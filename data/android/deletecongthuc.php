<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();
	
	if(isset($_POST['id']) && $_POST['id']!='')
	{
		$id=$_POST['id'];
		$result=$db->deletecongthuc($id);
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
	}
	else
	{
		$json["thanhcong"]=0;
		$json["thongbao"]="khong co id";
	}
	
	echo json_encode($json);

?>
