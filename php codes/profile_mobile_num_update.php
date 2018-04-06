<?php
	
	require "init.php";

	$upload_path = 'profiles/';
	
	$server_ip = gethostbyname(gethostname());
	
	$upload_url = 'http://'.$server_ip.'/webapp/'.$upload_path;
	
	$username = $_POST['username'];
	
	$mobile_num = $_POST['mobile_num'];
	
	try{
		
		$sql = "UPDATE user_info SET mobile_num = '$mobile_num' WHERE user_email LIKE '$username'";
		
		if(mysqli_query($con,$sql)){
			echo "mobile_num updated";
		}
		
	}catch(Exception $e){
	}
	
?>
	 