<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

		$id_congthuc=$_POST['id_congthuc'];
		$id_user=$_POST['id_user'];
		
			$result=$db->deletefavorited($id_congthuc,$id_user);

	
	
	echo json_encode($status);

?>