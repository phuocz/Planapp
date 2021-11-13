
<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();
	$id_nd=$_POST['id'];

		$result=$db->getbinhluan($id_nd);
	if(mysqli_num_rows($result)>0)//co san pham
	{
		$json["thanhcong"]=1;
		$json["binhluan"]=array(); //mang con
		
		//duyet tat ca san pham dua vao json
		while($row=mysqli_fetch_array($result))
		{
			$binhluan=array();
			$binhluan["user_name"]=$row["user_name"];
			$binhluan["picture_user"]=$row["picture_user"];
			$binhluan["id"]=$row["id"];
			$binhluan["id_user"]=$row["id_user"];
			$binhluan["noidung"]=$row["noidung"];
			//dua san pham vao mang
			array_push($json["binhluan"],$binhluan);
		}
	}
	else //khong co san pham
	{
		$json["thanhcong"]=0;
		$json["thongbao"]="khong co san pham";
	}
	
	echo json_encode($json);

?>
