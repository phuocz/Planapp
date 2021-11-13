<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

		$id_user=$_POST['id_user'];
		$id_cm=$_POST['id'];
		$comment=$_POST['comment'];
		$result=$db->eidtbinhluan($comment,$id_cm,$id_user);
		
		
	
		$json["thanhcong1"]=$id_cm;
	
		
		
	
	echo json_encode($json);

?>