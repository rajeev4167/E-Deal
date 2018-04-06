<?php
	
	require "init.php";

	$upload_path = 'profiles/';
	
	$server_ip = gethostbyname(gethostname());
	
	$upload_url = 'http://'.$server_ip.'/webapp/'.$upload_path;
	
	$username = $_POST['username'];
			
	$sql = "SELECT profile_pic_no FROM user_info WHERE user_email LIKE '$username'";
	$result = mysqli_fetch_array(mysqli_query($con,$sql));
	$abc = ++$result['profile_pic_no'];
	
	
	$flieinfo = pathinfo($_FILES['image']['name']);
	
	$extension = 'png';
	
	$file_url = $upload_url . $username . $abc . '.' . $extension;
	
	$file_path = $upload_path . $username . $abc . '.' . $extension;
	
	try{
		
		move_uploaded_file($_FILES['image']['tmp_name'], $file_path);
		
		$sql = "UPDATE user_info SET profile_pic = '$file_url', profile_pic_no = $abc WHERE user_email LIKE '$username'";
		
		if(mysqli_query($con,$sql)){
			echo "profile_pic updated";
		}
		
	}catch(Exception $e){
	}
	
?>
	 