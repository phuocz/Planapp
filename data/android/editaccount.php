<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();
	$json=array();

	
		$id=$_POST['id'];
		$name=$_POST['name'];
		$pass=$_POST['pass'];
		$email=$_POST['email'];
		$img=$_POST['imgct'];
		$imglocation="img";
		
	if (!file_exists($imglocation)) {
		mkdir($imglocation1,0777,true);
	}
		$imglocation=$imglocation ."/". rand() . "_". time() .".jpg";
		
		if ( $img =="null") {
			$result1=$db->editaccount2($id,$name,$pass,$email);
			file_put_contents($imglocation, base64_decode($img));
			$result=$db->getallUser();
			if(mysqli_num_rows($result)>0 ){//co san pham
	
			$json["user"]=array(); //mang con
		while($row=mysqli_fetch_array($result))
		{
			$user=array();
		
           
			$user["id"]=$row["id"];
			$user["picture_user"]=null;
			$user["name"]=$row["user_name"];
			$user["email"]=$row["email"];
			$user["password"]=$row["password"];
			//dua san pham vao mang
			array_push($json["user"],$user);
		}
		}	
			
		}
		if ($img!="null") {
			$result1=$db->editaccount($id,$name,$pass,$email,$imglocation);
			$result=$db->getallUser();
			file_put_contents($imglocation, base64_decode($img));

			if(mysqli_num_rows($result)>0 ){
			$json["user"]=array(); //mang con
		
		//duyet tat ca san pham dua vao json
			while($row=mysqli_fetch_array($result))
		{
			$user=array();
		
           
			$user["id"]=$row["id"];
			$user["picture_user"]=$row["picture_user"];
			$user["name"]=$row["user_name"];
			$user["email"]=$row["email"];
			$user["password"]=$row["password"];
			//dua san pham vao mang
			array_push($json["user"],$user);
		}
		}
		
		}
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
