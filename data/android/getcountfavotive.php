<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();


		$id_ct=$_POST['id_ct'];
	
			$result=$db->countfavorited($id_ct);
if(mysqli_num_rows($result)>0)//co san pham
	{
		$json["thanhcong"]=1;
		$json["count"]=array(); //mang con
		
		//duyet tat ca san pham dua vao json
		while($row=mysqli_fetch_array($result))
		{
			$count=array();
			
			$count["count"]=$row["COUNT(`id_user`)"];
			
			//dua san pham vao mang
		array_push($json["count"],$count);
		
		}
	}
	else //khong co san pham
	{
		$json["thanhcong"]=0;
		$json["thongbao"]="khong co san pham";
	}
	
	echo json_encode($json);

?>