<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

	if(isset($_POST['id']) && $_POST['id']!=''){
		$id=$_POST['id'];
		$result=$db->getstatusid($id);
	if(mysqli_num_rows($result)>0)//co san pham
	{
		$json["thanhcong"]=1;
		$json["status"]=array(); //mang con
		
		//duyet tat ca san pham dua vao json
		while($row=mysqli_fetch_array($result))
		{
			$status=array();
			$status["id_user"]=$row["id_user"];
		$status["id_congthuc"]=$row["id_congthuc"];
			$status["status"]=$row["status"];

			
			//dua san pham vao mang
			array_push($json["status"],$status);
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
