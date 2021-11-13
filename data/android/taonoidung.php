<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();
		
		$idcongthuc=$_POST['idcongthuc'];
		$nguyenlieu=$_POST['nguyenlieu'];
		$noidung=$_POST['noidung'];
		$img=$_POST['img'];
		$imglocation="img";
		if (!file_exists($imglocation)) {
		mkdir($imglocation,0777,true);
	}
		
		$imglocation=$imglocation ."/". rand() . "_". time() .".jpg";
		$result=$db->taonoidung($idcongthuc,$nguyenlieu,$noidung,$imglocation);
		
		file_put_contents($imglocation, base64_decode($img));
		if($result)
		{
			
			$json["thanhcong tao"]=$noidung;
			$json["thongbao"]=$imglocation;
		}
		else
		{
			$json["thanhcong"]=0;
			$json["thongbao"]="tao khong thanh cong";
			$json["thanhconga"]=$idcongthuc;
			$json["thongbaoc"]=$nguyenlieu;
			$json["thanhcongv"]=$noidung;
			$json["thongbaob"]=$imglocation;
		}
	
	
	echo json_encode($json);

?>
