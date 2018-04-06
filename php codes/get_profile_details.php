<?php 
 
 require 'init.php';
 
 $username = $_POST['username'];
 
 $sql = "SELECT * FROM user_info WHERE user_email LIKE '$username'";
 
 $result = mysqli_query($con,$sql);
 
 $response = array();
 $response['error'] = false; 
 $response['user_info'] = array();
 
 while($row = mysqli_fetch_array($result)){
 
 $temp = array();
 $temp['name']=$row['name'];
 $temp['user_email']=$row['user_email'];
 $temp['user_pass']=$row['user_pass'];
 $temp['mobile_num']=$row['mobile_num'];
 $temp['profile_pic']=$row['profile_pic'];
 array_push($response['user_info'],$temp);
 }
 
 echo json_encode($response);