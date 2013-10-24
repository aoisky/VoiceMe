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
    
    
    if ($_SERVER['HTTP_METHOD'] === 'checkLogin'){
        
        
        
        $stmt = $db->prepare('SELECT `user_login`,`uid`,`user_email`,`user_pass` FROM `user` WHERE `user_login` = ?');
        $stmt->bind_param('s', $body['username']);
        
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
            if ($hasher->CheckPassword($body['password'], $user_pass)) { //$hash is the hash retrieved from the DB
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
                $avatar=get_avatar( $body['username'], 96 );
                preg_match("/src='(.*?)'/i", $avatar, $avatar_url1);
                $avatar_url=$avatar_url1[1];
            }
            $results[] = array('auth'=>$success,'avatar_url'=>$avatar_url,'user_email'=>$user_email);
        }
        else {
        	$results[] = array('auth'=>$success);
        }
        $stmt -> close();
        $db->close();

        echo json_encode($results);
    }
    else if($_SERVER['HTTP_METHOD'] === 'registerUser'){
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
        $result='NO';
        file_put_contents("test.txt",$body['user_username'].$body['user_email']);
        if($user_exist==='OK'){
        	$result='User exists';
        	if($email_exist==='OK'){
        		$result='User and Email exist';
        	}
        }
        else if($email_exist==='OK'){
            $result='Email exists';
        }
        else{
            // Base-2 logarithm of the iteration count used for password stretching
            $hash_cost_log2 = 8;
            // Do we require the hashes to be portable to older systems (less secure)?
            $hash_portable = TRUE;
            $hasher = new PasswordHash($hash_cost_log2, $hash_portable);
            $password = $hasher->HashPassword($body['user_password']);
            $stmt = $db->prepare('INSERT INTO  `user` (`uid` ,`user_login` ,`user_pass`,`user_email`,`user_nickname`, `user_avatar`) VALUES (NULL ,  ?,  ?,  ?,  ?, NULL) ');
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
                		$result='OK';
                	}
                	else{
                		$result='Failed';
                	}
                }	
                else{
                	$result='Failed';
                }	
            }
            else{
            	$result='Failed';
            }
        }
        
        $stmt -> close();
        $db->close();
        $results[] = array('result'=>$result);
        echo json_encode($results);
        
    }
    else if($_SERVER['HTTP_METHOD']==='resetPasswd'){
        
        
        $success='NO';
        $user_data;
        
        /**
        if(strpos( $body['user_login'], '@' )){
            $user_data = get_user_by( 'email', trim( $body['user_login'] ) );
            if (!$user_data)
                $success='No such Email';
        }
        else{
            $user_data = get_user_by( 'login', trim( $body['user_login'] ) );
            if(!$user_data){
                $success='No such user';
            }
        }
        
        if($user_data){
            
            $success = 'OK';
            
            $user_login = $user_data->user_login;
            $user_email = $user_data->user_email;
            
            
            do_action('retrieve_password', $user_login);
            
            $allow = apply_filters('allow_password_reset', true, $user_data->ID);
            
            if ( ! $allow )
                return new WP_Error('no_password_reset', __('Password reset is not allowed for this user'));
            else if ( is_wp_error($allow) )
                return $allow;
            
            $key = $wpdb->get_var($wpdb->prepare('SELECT user_activation_key FROM $wpdb->users WHERE user_login = %s', $user_login));
            if ( empty($key) ) {
                // Generate something random for a key...
                $key = wp_generate_password(20, false);
                do_action('retrieve_password_key', $user_login, $key);
                // Now insert the new md5 key into the db
                $wpdb->update($wpdb->users, array('user_activation_key' => $key), array('user_login' => $user_login));
            }
            $message = __('Someone requested that the password be reset for the following account:') . "\r\n\r\n";
            $message .= network_home_url( '/' ) . "\r\n\r\n";
            $message .= sprintf(__('Username: %s'), $user_login) . "\r\n\r\n";
            $message .= __('If this was a mistake, just ignore this email and nothing will happen.') . "\r\n\r\n";
            $message .= __('To reset your password, visit the following address:') . "\r\n\r\n";
            $message .= '<' . network_site_url("wp-login.php?action=rp&key=$key&login=" . rawurlencode($user_login), 'login') . ">\r\n";
            
            
            /**
             if ( is_multisite() )
             $blogname = $GLOBALS['current_site']->site_name;
             else
             // The blogname option is escaped with esc_html on the way into the database in sanitize_option
             // we want to reverse this for the plain text arena of emails.
             $blogname = wp_specialchars_decode(get_option('blogname'), ENT_QUOTES);
             */
            $title = sprintf( '[%s] Password Reset', "VoiceMe" );
            
            $title = apply_filters('retrieve_password_title', $title);
            $message = apply_filters('retrieve_password_message', $message, $key);
            $headers = 'From: VoiceMe <support@imvoice.me>' . "\r\n";
            
            if ( $message && !wp_mail($user_email, $title, $message) ){
                //wp_die( __('The e-mail could not be sent.') . "<br />\n" . __('Possible reason: your host may have disabled the mail() function.') );
                $success='Fail';
            }
            
            
            
        }
        */
        $results[] = array('result'=>$success);
        echo json_encode($results);
    }
    else {
        $data['error'] = 'The Service you asked for was not recognized';
        echo json_encode($data);
    }
    
?>