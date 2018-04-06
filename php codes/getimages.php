<?php 
 
 require 'init.php';
 
 $sql = "SELECT * FROM images";
 
 $result = mysqli_query($con,$sql);
 
 $response = array(); 
 $response['error'] = false; 
 $response['images'] = array(); 
 
 while($row = mysqli_fetch_array($result)){
 
 $temp = array(); 
 $temp['id']=$row['id'];
 $temp['name']=$row['name'];
 $temp['url']=$row['url'];
 array_push($response['images'],$temp);
 }
 
 echo json_encode($response);