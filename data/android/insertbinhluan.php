<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

		$id_user=$_POST['id_user'];
		$id_noidung=$_POST['id_noidung'];
		
		$binhluan=$_POST['binhluan'];
		
			$result=$db->insertbinhluan($id_user,$id_noidung,$binhluan);

		
	
	echo json_encode("thanh cong");

?>