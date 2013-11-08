<?php
    
    
    /*Connect to database
      This function just connects to database and fail if error occurs
     */
    include('./data/config.inc.php');
    //include('./resource/phpass/PasswordHash.php');

    $body;
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
    
    $db = new mysqli($db_host, $db_user, $db_pass, $db_name);
    
    if($db->connect_errno > 0){
        die('Unable to connect to database [' . $db->connect_error . ']');
    }   
     
    /**
    	Incoming arguments:	$body['uid'], $body['post_content']
    	Return value(key:variable):		result:$success
    */
    
    
    if ($_SERVER['HTTP_METHOD'] === 'post_content'){
    
    	$stmt = $db->prepare('INSERT INTO  `content` (`pid` ,`post_content` ,`uid`,`share_count`,
    										`like_count`, `comment_count`, `creation_time`, `last_update_time`)
    										 VALUES (NULL , ?, ?, 0, 0, 0, now(), NULL) ');
        $stmt->bind_param('ss', $body['post_content'],$body['uid']);
        if($stmt->execute()){
        	$stmt = $db->prepare('SELECT `pid`,`post_content` FROM  `content` WHERE `uid` = ? ORDER BY `creation_time` LIMIT 1');
        	$stmt->bind_param('ss', $body['uid']);
        	$stmt->execute();
        	$stmt -> bind_result($pid,$post_content);
        	$stmt->fetch();
            if($body['post_content'] === $post_content){
            	$stmt = $db->prepare('INSERT INTO  `user_content_list` (`pid`, `uid`)
    										 VALUES (?, ?) ');
        		$stmt->bind_param('ss', $pid, $body['uid']);
        		if($stmt->execute()){
        			$success = "Success";
        		}
        		else{
        			$success = "Failed";
        		}
            }
        }
        else{
        	$success = "Failed";
        }
        $results[] = array('result'=>$success);
        $stmt -> close();
        $db->close();
        
        echo json_encode($results);
    }
    /**
        Incoming arguments: $body['uid'], $body['comment'], $body['rid'],$body['pid']
        Return value(key:variable):     result:$success
    */
    else if ($_SERVER['HTTP_METHOD'] === 'post_comment'){
    
        $stmt = $db->prepare('INSERT INTO  `comment` (`cid`, `pid`, `rid` ,`comment` ,`uid`,`reply_count`,
                                            `comment_count`, `creation_time`, `last_update_time`)
                                             VALUES (NULL , ?, ?, ?, 0, 0, now(), NULL) ');
        $stmt->bind_param('sss', $body['rid'], $body['pid'], $body['comment'],$body['uid']);
        if($stmt->execute()){
            $stmt = $db->prepare('SELECT `cid`,`comment`,`rid` FROM  `comment` WHERE `uid` = ? ORDER BY `creation_time` LIMIT 1');
            $stmt->bind_param('s', $body['uid']);
            $stmt->execute();
            $stmt -> bind_result($cid,$post_comment,$rid);
            $stmt->fetch();
            if($rid != NULL){
                $stmt = $db->prepare('SELECT `uid` FROM  `comment` WHERE `cid` = ? LIMIT 1');
                $stmt->bind_param('s', $cid);
                $stmt->execute();
                $stmt -> bind_result($r_uid);
                $stmt->fetch();
            }
            if($body['comment'] === $post_comment){
                $stmt = $db->prepare('INSERT INTO  `user_comment_list` (`uid`, `r_uid`, `rid`, `cid`)
                                             VALUES (?, ?, ?, ?) ');
                $stmt->bind_param('ss', $body['uid'], $r_uid,$body['rid'],$cid);
                if($stmt->execute()){
                    $success = "Success";
                }
                else{
                    $success = "Failed";
                }
            }
        }
        else{
            $success = "Failed";
        }
        $results[] = array('result'=>$success);
        $stmt -> close();
        $db->close();
        
        echo json_encode($results);
    }
    /**
        Incoming arguments: $body['uid'], $body['pid']
        Return value(key:variable):     result:$success post_content:$post_content share_count:$share_count like_count:$like_count comment_count:$comment_count creation_time:$creation_time
    */
    else if ($_SERVER['HTTP_METHOD'] === 'fetch_content'){
    
        //$stmt = $db->prepare('INSERT INTO  `content` (`pid` ,`post_content` ,`uid`,`share_count`,
                                            //`like_count`, `comment_count`, `creation_time`, `last_update_time`)
                                            // VALUES (NULL , ?, ?, 0, 0, 0, now(), NULL) ');
        //$stmt->bind_param('ss', $body['post_content'],$body['uid']);
        //if($stmt->execute()){
        $stmt = $db->prepare('SELECT `post_content`,`share_count`,`like_count`,`comment_count`,`creation_time` FROM  `content` WHERE `pid` = ? AND `uid`= ? LIMIT 1');
        $stmt->bind_param('ss', $body['pid'], $body['uid']);
        $stmt->execute();
        $stmt -> bind_result($post_content,$share_count,$like_count,$comment_count,$creation_time);
        $stmt->fetch();
        if($post_content !== NULL){
            $success = "Success";
        }
        //}
        else{
            $success = "Failed";
        }
        $results[] = array('result'=>$success,'post_content'=>$post_content,'share_count'=>$share_count,'like_count'=>$like_count,'comment_count'=>$comment_count,'creation_time'=>$creation_time);
        $stmt -> close();
        $db->close();
        
        echo json_encode($results);
    }
    /**
        Incoming arguments: $body['uid'], $body['cid'], $body['pid']
        Return value(key:variable):     result:$success comment:$comment rid:$rid reply_count:reply_count comment_count:comment_count creation_time:creation_time
    */
    else if ($_SERVER['HTTP_METHOD'] === 'fetch_comment'){
    
        //$stmt = $db->prepare('INSERT INTO  `comment` (`cid`, `rid` ,`comment` ,`uid`,`reply_count`,
                                            //`comment_count`, `creation_time`, `last_update_time`)
                                            // VALUES (NULL , ?, ?, ?, 0, 0, now(), NULL) ');
        //$stmt->bind_param('sss', $body['rid'], $body['comment'],$body['uid']);
        //if($stmt->execute()){
            $stmt = $db->prepare('SELECT `cid`,`pid`, `comment`,`rid`,`reply_count`,`comment_count`,`creation_time` FROM  `comment` WHERE `cid` = ? AND `uid`= ? ORDER BY `creation_time` LIMIT 1');
            $stmt->bind_param('ss', $body['cid'],$body['uid']);
            $stmt->execute();
            $stmt -> bind_result($cid,$post_comment,$rid,$reply_count,$comment_count,$creation_time);
            $stmt->fetch();
            if($cid !== NULL){
                $stmt = $db->prepare('SELECT `uid` FROM  `comment` WHERE `cid` = ? LIMIT 1');
                $stmt->bind_param('ss', $cid);
                $stmt->execute();
                $stmt -> bind_result($r_uid);
                $stmt->fetch();
                $success = "Success";
            }
            else{
                $success = "Failed";
            }
        //}
        //else{
            
        //}
        $results[] = array('result'=>$success,'comment'=>$comment,'rid'=>$rid,'reply_count'=>reply_count,'comment_count'=>comment_count,'creation_time'=>creation_time);
        $stmt -> close();
        $db->close();
        
        echo json_encode($results);
    }
    else {
        $data['error'] = 'The Service you asked for was not recognized';
        echo json_encode($data);
    }
    
    
    function get_string_between($string, $start, $end){
        $string = " ".$string;
        $ini = strpos($string,$start);
        if ($ini == 0) return "";
        $ini += strlen($start);
        $len = strpos($string,$end,$ini) - $ini;
        return substr($string,$ini,$len);
    }
    ?>

