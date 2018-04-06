<?php
require "init.php";
$name= $_POST["name"];
$user_email= $_POST["user_email"]; 
$user_pass= $_POST["user_pass"];

$sql = "Select * from user_info WHERE user_email LIKE '$user_email'";

$result = mysqli_query($con,$sql);

if ($row = mysqli_fetch_array($result))
{
	echo "Username already taken";
}
else {
	$sql_query = "insert into user_info (name, user_email, user_pass) values('$name','$user_email','$user_pass');";
	

	if (mysqli_query($con,$sql_query))
	{
		echo"Registration Success...";
	}

	else
	{
	//echo "Data insertion error.." .mysqli_error($con);
	}
}

?>