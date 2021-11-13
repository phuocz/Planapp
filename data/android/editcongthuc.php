<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

	
		$id=$_POST['id'];
		$name=$_POST['name'];
		$mota=$_POST['mota'];
		$loai=$_POST['loai'];
		$nguyenlieu=$_POST['nguyenlieu'];
		$noidung=$_POST['noidung'];
		$imgct=$_POST['imgct'];
		$imgnd=$_POST['imgnd'];
		$imglocation1="img";
		$imglocation2="img";
		if (!file_exists($imglocation2)) {
		mkdir($imglocation2,0777,true);
	}
	if (!file_exists($imglocation1)) {
		mkdir($imglocation1,0777,true);
	}
		$imglocation1=$imglocation1 ."/". rand() . "_". time() .".jpg";
		$imglocation2=$imglocation2 ."/". rand() . "_". time() .".jpg";
		if ($imgct =="null" && $imgnd =="null") {
			$result=$db->editcontentidbnopic($id,$name,$mota,$loai,$nguyenlieu,$noidung);
		}
		if ($imgct !="null" && $imgnd =="null") {
			$result=$db->editcontentidctpic($id,$name,$mota,$loai,$imglocation1,$nguyenlieu,$noidung);
		}
		if ($imgct =="null" && $imgnd !="null") {
			$result=$db->editcontentidndpic($id,$name,$mota,$loai,$nguyenlieu,$noidung,$imglocation2);
		}
		if ($imgct !="null" && $imgnd !="null") {
			$result=$db->editcontentid($id,$name,$mota,$loai,$imglocation1,$nguyenlieu,$noidung,$imglocation2);
		}
		
		file_put_contents($imglocation1, base64_decode($imgct));
		file_put_contents($imglocation2, base64_decode($imgnd));
	if($result)
		{
			
		}
		else
		{
			$json["thanhcong"]=0;
			$json["thongbao"]="tao khong thanh cong";
		}
	
	
	echo json_encode($json);
?>
