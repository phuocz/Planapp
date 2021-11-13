<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

	if(isset($_POST['id']) && $_POST['id']!=''){
		$id=$_POST['id'];
		$result=$db->getcontent($id);
	if(mysqli_num_rows($result)>0)//co san pham
	{
		$json["thanhcong"]=1;
		$json["congthuc"]=array(); //mang con
		
		//duyet tat ca san pham dua vao json
		while($row=mysqli_fetch_array($result))
		{
			$congthuc=array();
			$congthuc["id"]=$row["id"];
			$congthuc["name"]=$row["name"];
			$congthuc["noidung"]=$row["noidung"];
			$congthuc["picture"]=$row["picture_nd"];
			$congthuc["nguyenlieu"]=$row["nguyenlieu"];


			
			//dua san pham vao mang
			array_push($json["congthuc"],$congthuc);
		}
	}
	else //khong co san pham
	{
		$json["thanhcong"]=0;
		$json["thongbao"]="khong co san pham";
	}
	}
	echo json_encode($json);

?>
