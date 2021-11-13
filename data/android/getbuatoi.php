<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();
	
	$result=$db->getbuatoi();
	
	if(mysqli_num_rows($result)>0 )//co san pham
	{
		$json["thanhcong"]=1;
		$json["congthuc"]=array();
		$json["favorite"]=array(); //mang con
		
		//duyet tat ca san pham dua vao json
		while($row=mysqli_fetch_array($result)  )
		{
			$congthuc=array();
			$congthuc["user_name"]=$row["user_name"];
			$congthuc["picture_user"]=$row["picture_user"];
			$congthuc["id"]=$row["id"];
			$congthuc["name"]=$row["name"];
			$congthuc["mota"]=$row["mota"];
			$congthuc["picture"]=$row["picture"];
           
			
			//dua san pham vao mang
			array_push($json["congthuc"],$congthuc);
		}
		
	}
	else //khong co san pham
	{
		$json["thanhcong"]=0;
		$json["thongbao"]="khong co san pham";
	}
	echo json_encode($json);

?>
