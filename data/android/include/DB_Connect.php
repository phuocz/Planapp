<?php
class DB_Connect
{
	//ham tao
	function __construct()
	{
		
	}
	
	//ham huy
	function __destruct()
	{
		
	}
	
	//ket noi toi database
	function connect()
	{
		require_once "config.php";
		$con=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
		//mysqli_select_db($con,DB_DATABASE);
		return $con;
		
	}
	
	//dong ket noi
	function close()
	{
		//mysqli_close();
	}
}
?>
