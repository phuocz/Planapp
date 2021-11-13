<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

		$id_user=$_POST['id_user'];
		$id_ct=$_POST['id_ct'];
		$status=$_POST['status'];
		
			$result=$db->insertstatus($id_ct,$status,$id_user);

		
	
	echo json_encode($status);

?>