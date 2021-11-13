<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();
	
		$id=$_POST['id'];
		$result=$db->deletebinhluan($id);
		if($result)//co san pham
		{
			$json["thanhcong"]=1;
			$json["thongbao"]="xoa thanh cong";
		}
		else //khong co san pham
		{
			$json["thanhcong"]=0;
			$json["thongbao"]="khong co san pham";
		}
	
	echo json_encode($id);

?>
