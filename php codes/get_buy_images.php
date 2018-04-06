<?php 
 
 require 'init.php';
 
 $username = $_POST['username'];
 
 $sql = "SELECT * FROM images WHERE username NOT LIKE '$username' ORDER BY id DESC";
 
 $result = mysqli_query($con,$sql);
 
 $response = array(); 
 $response['error'] = false; 
 $response['images'] = array(); 
 
 while($row = mysqli_fetch_array($result)){
 
 $temp = array(); 
 $temp['id']=$row['id'];
 $temp['name']=$row['name'];
 $temp['url']=$row['url'];
 $temp['username']=$row['username'];
 $temp['type']=$row['type'];
 $temp['price']=$row['price'];
 $temp['description']=$row['description'];
 array_push($response['images'],$temp);
 }
 
 echo json_encode($response);