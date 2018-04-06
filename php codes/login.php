<?php
require "init.php";
$user_email= $_POST["user_email"];
$user_pass= $_POST["user_pass"];
$sql_query = "select name from user_info where user_email like '$user_email' and user_pass like '$user_pass';"; 

$result=mysqli_query($con,$sql_query);

if(mysqli_num_rows($result) > 0)
{
$row= mysqli_fetch_assoc($result);
$name= $row["name"];
echo " Login Success...Hello Welcome ".$name ;
}

else
{
echo "Wrong UserName/Password...Try Again";
}

?>