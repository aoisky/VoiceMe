<?php
    /*Simple Service
     This is just a simple php script that will return values ,the
     method is selected using the value of HTTP_METHOD
     */
    
    /*Connect to database
     This function just connects to database and fail if error occurs
     */
    include('./data/config.inc.php');
    include('./resource/phpass/PasswordHash.php');

    
    $db = new mysqli($db_host, $db_user, $db_pass, $db_name);
    
    if($db->connect_errno > 0){
        die('Unable to connect to database [' . $db->connect_error . ']');
    }
    
    /*Sometimes the body data is attached in raw form and is not attached
     to $_POST, this needs to be handled*/
    if($_POST == null){
        $handle  = fopen('php://input', 'r');
        $rawData = fgets($handle);
        $body = json_decode($rawData,true);
    }
    else{
        $body == $_POST;
    }
    
    /**
    	Incoming arguments:	$body['user_username'], $body['user_password']
    	Return value(key:variable):		result:$success, uid:$uid, user_login:$user_login,
    									avatar_url:$avatar_url, user_email:$user_email
    */
    if ($_SERVER['HTTP_METHOD'] === 'checkLogin'){
        
        
        
        $stmt = $db->prepare('SELECT `user_login`,`uid`,`user_email`,`user_pass` FROM `user` WHERE `user_login` = ?');
        $stmt->bind_param('s', $body['user_username']);
        
        $stmt->execute();
        //$stmt->store_result();
        $stmt -> bind_result($user_login,$uid,$user_email,$user_pass);
        // Base-2 logarithm of the iteration count used for password stretching
        $hash_cost_log2 = 8;
        // Do we require the hashes to be portable to older systems (less secure)?
        $hash_portable = FALSE;
        $hasher = new PasswordHash($hash_cost_log2, $hash_portable);
        $success='No such user';

        while($stmt -> fetch()){
            if ($hasher->CheckPassword($body['user_password'], $user_pass)) { //$hash is the hash retrieved from the DB
                $success = 'Authentication succeeded';
                break;
            } else {
                $success = 'Authentication failed';
            }
        }
        if($success==='Authentication succeeded'){
            $stmt1 = $db->prepare('SELECT `avatar_url` FROM  `user_avatar` WHERE `uid`=?');
            $stmt1->bind_param('s', $uid);
            $stmt1->execute();
            $stmt1->store_result();
            $stmt1 -> bind_result($avatar_url);
            $stmt1->fetch();
            $stmt1 -> close();
            if(!$avatar_url){
                $avatar=get_avatar( $body['user_username'], 96 );
                preg_match("/src='(.*?)'/i", $avatar, $avatar_url1);
                $avatar_url=$avatar_url1[1];
            }
            $results[] = array('auth'=>$success,'user_login'=>$user_login, 'uid'=>$uid, 'avatar_url'=>$avatar_url,'user_email'=>$user_email);
        }
        else {
        	$results[] = array('auth'=>$success);
        }
        $stmt -> close();
        $db->close();

        echo json_encode($results);
    }
    /**
    	Incoming arguments:	$body['user_username'], $body['user_email'], $body['user_password'],
    						 $body['user_firstname'], $body['user_lastname'], $body['user_mobile'],
            				$body['user_age'], $body['user_des'],$body['user_gender']
    	Return value(key:variable):		result:$success
    */
    else if($_SERVER['HTTP_METHOD'] === 'registerUser'){
    
    	$success='Failed';
    
        $stmt = $db->prepare('SELECT "OK" FROM `user` WHERE `user_login` = ? ');
        $stmt->bind_param('s', $body['user_username']);
        $stmt->execute();
        $stmt->store_result();
        $stmt -> bind_result($user_exist);
        $stmt->fetch();
        $stmt = $db->prepare('SELECT "OK" FROM `user` WHERE `user_email` = ? ');
        $stmt->bind_param('s', $body['user_email']);
        $stmt->execute();
        $stmt->store_result();
        $stmt -> bind_result($email_exist);
        $stmt->fetch();
        
        //file_put_contents("test.txt",$body['user_username'].$body['user_email']);
        if($user_exist==='OK'){
        	$success='User exists';
        	if($email_exist==='OK'){
        		$success='User and Email exist';
        	}
        }
        else if($email_exist==='OK'){
            $success='Email exists';
        }
        else{
            // Base-2 logarithm of the iteration count used for password stretching
            $hash_cost_log2 = 8;
            // Do we require the hashes to be portable to older systems (less secure)?
            $hash_portable = FALSE;
            $hasher = new PasswordHash($hash_cost_log2, $hash_portable);
            $password = $hasher->HashPassword($body['user_password']);
            $stmt = $db->prepare('INSERT INTO  `user` (`uid` ,`user_login` ,`user_pass`,
            									`user_email`,`user_nickname`, `user_avatar`) 
            									VALUES (NULL ,  ?,  ?,  ?,  ?, NULL) ');
            $stmt->bind_param('ssss', $body['user_username'],$password,$body['user_email'],$body['user_username']);
            if($stmt->execute()){
            	$stmt = $db->prepare('SELECT `uid` FROM `user` WHERE `user_email` = ? ');
            	$stmt->bind_param('ssss', $body['user_email']);
				$stmt->execute();
				$stmt -> bind_result($uid);
				$stmt->fetch();
				
            	$stmt = $db->prepare('INSERT INTO  `user_profile` (`uid` ,`firstname` ,`lastname` ,`mobile` ,`age`,
            									`description` ,`gender` ,`reg_time` ,`last_login_time` ,`user_level`,
            									`user_status`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, now(), NULL, ?, ?) ');
            	$stmt->bind_param('dssssssss', $uid, $body['user_firstname'], $body['user_lastname'], $body['user_mobile'],
            									$body['user_age'], $body['user_des'],$body['user_gender'],
            									,$user_default_level,$user_default_status);

                if($stmt->execute()){
                	$stmt = $db->prepare('INSERT INTO  `user_avatar` (`uid` ,`avatar_url`) VALUES ( ?, NULL) ');
            		$stmt->bind_param('d', $uid);
            		if($stmt->execute()){
                		$success='OK';
                	}
                	else{
                		$success='Failed';
                	}
                }	
                else{
                	$success='Failed';
                }	
            }
            else{
            	$success='Failed';
            }
        }
        
        $stmt -> close();
        $db->close();
        $results[] = array('result'=>$success);
        echo json_encode($results);
        
    }
    /**
    	Incoming arguments:	$body['user_searchVal']
    	Return value(key:variable):		result:$success
    */
    else if($_SERVER['HTTP_METHOD']==='resetPasswd'){
        
        $success='Failed';
        
        
        $stmt = $db->prepare('SELECT `user_email`,`user_login` FROM `user` WHERE `user_login` = ? OR `user_email` = ?');
        $stmt->bind_param('ss', $body['user_searchVal'],$body['user_searchVal']);
        $stmt->execute();
        $stmt->store_result();
        $stmt -> bind_result($user_email,$user_login);
        $stmt->fetch();
        if($user_email){
        	$mail = new PHPMailer;

			$mail->isSMTP();                                      // Set mailer to use SMTP
			$mail->Host = 'smtp1.example.com;smtp2.example.com';  // Specify main and backup server
			$mail->SMTPAuth = true;                               // Enable SMTP authentication
			$mail->Username = 'jswan';                            // SMTP username
			$mail->Password = 'secret';                           // SMTP password
			$mail->SMTPSecure = 'tls';                            // Enable encryption, 'ssl' also accepted

			$mail->From = 'no-reply@imvoice.me';
			$mail->FromName = 'VoiceMe';
			$mail->addAddress('josh@example.net', 'Josh Adams');  // Add a recipient
			//$mail->addAddress('ellen@example.com');               // Name is optional
			$mail->addReplyTo('support@imvoice.me', 'Support');
			//$mail->addCC('cc@example.com');
			//$mail->addBCC('bcc@example.com');

			$mail->WordWrap = 50;                                 // Set word wrap to 50 characters
			//$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
			//$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
			$mail->isHTML(true);                                  // Set email format to HTML
			
			$lifeTime = 24 * 3600;
            $sessionName = session_name();
            $sessionID = $_GET[$sessionName];
            session_id($sessionID); 
            session_set_cookie_params($lifeTime);
            session_start();
			
			$message = __('Someone requested that the password be reset for the following account:') . "\r\n\r\n";
            $message .= sprintf(__('Username: %s'), $user_login) . "\r\n\r\n";
            $message .= __('If this was a mistake, just ignore this email and nothing will happen.') . "\r\n\r\n";
            $message .= __('To reset your password, visit the following address within 24 
            										hours after receiving this email:') . "\r\n\r\n";
            $message .= '<' . dirname(__FILE__) . "/login.php?action=rp&key=".session_id()."&login=" . $user_login . ">\r\n";
            
			$mail->Subject = 'Reset password request';
			$mail->Body    = $message;
			$mail->AltBody = $message;

			if(!$mail->send()) {
			   $success =  'Message could not be sent.';
			   $success .= 'Mailer Error: ' . $mail->ErrorInfo;
			}

			$success = 'Message has been sent';
        }
        else{
        	$success = "No such user";
        }
        $results[] = array('result'=>$success);
        echo json_encode($results);
    }
    else {
        $data['error'] = 'The Service you asked for was not recognized';
        echo json_encode($data);
    }
    
?>