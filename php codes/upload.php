<?php
	
	require "init.php";

	$upload_path = 'uploads/';
	
	$server_ip = gethostbyname(gethostname());
	
	$upload_url = 'http://'.$server_ip.'/webapp/'.$upload_path;
	
	$response = array();
	
	$name = $_POST['name'];
	
	$abc = getFileName();
	
	$flieinfo = pathinfo($_FILES['image']['name']);
	
	$extension = 'png';
	
	$file_url = $upload_url . getFileName() . $name . '.' . $extension;
	
	$file_path = $upload_path . getFileName() . $name . '.' . $extension;
	
	try{
		
		move_uploaded_file($_FILES['image']['tmp_name'], $file_path);
		
		$sql = "insert into images (id, url, name) VALUES ($abc,'$file_url','$name')";
		
		if(mysqli_query($con,$sql)){
			$response['error'] = false;
			$response['url'] = $file_url;
			$response['name'] = $name;
		}
		
	}catch(Exception $e){
		$response['error'] = false;
		$response['message'] = $e->getMessage();
	}
	
	echo json_encode($response);
	
	function getFileName(){
		
		require "init.php";
		
		$sql = "select max(id) as id from images";
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		if($result['id']==null){
			return 1;
		}else{
			return ++$result['id'];
		}
	}
	
?>
	 