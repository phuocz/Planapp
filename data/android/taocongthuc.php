<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();
	


		$id_user=$_POST['id_user'];
		$name=$_POST['name'];
		$loai=$_POST['loai'];
		$mota=$_POST['mota'];
		$img=$_POST['img'];
		$imglocation="img";
		if (!file_exists($imglocation)) {
		mkdir($imglocation,0777,true);
	}
		
		$imglocation=$imglocation ."/". rand() . "_". time() .".jpg";
		$result=$db->taocongthuc($id_user,$name,$mota,$imglocation,$loai);
		file_put_contents($imglocation, base64_decode($img));
		if($result)
		{
			
			$json["thanhcong"]=1;
			$json["thongbao"]="tao san pham thanh cong";
		}
		else
		{
			$json["thanhcong"]=0;
			$json["thongbao"]="tao khong thanh cong";
		}
	
	
	echo json_encode($json);

?>
