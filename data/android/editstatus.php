<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

		$id_user=$_POST['id_user'];
		$id=$_POST['id'];
		$status=$_POST['status'];
		$result=$db->editstatus($status,$id_user,$id);
		
		
		$json["thanhcong1"]=$id_user;
		$json["thanhcong2"]=$id;
		$json["thanhcong3"]=$status;
		
		
	
	echo json_encode($json);

?>