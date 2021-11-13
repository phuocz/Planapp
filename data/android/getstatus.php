<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();


		$id_ct=$_POST['id_ct'];
		$id_user=$_POST['id_user'];
		$result=$db->getstatus($id_user,$id_ct);
		
	if(mysqli_num_rows($result)>0)//co san pham
	{
		$json["thanhcong"]=1;
		$json["status"]=array(); //mang con

		//duyet tat ca san pham dua vao json
		while($row=mysqli_fetch_array($result))
		{
			$status=array();
			$status["id"]=$row["id"];	
			$status["id_user"]=$row["id_user"];		
			$status["status"]=$row["status"];

			
			//dua san pham vao mang
			array_push($json["status"],$status);
		}
		}
	else //khong co san pham
	{
		$result=$db->insertstatus($id_ct,1,$id_user);
		$json["thanhcong"]="0";
		
	}
		
	
	
	echo json_encode($json);

?>
