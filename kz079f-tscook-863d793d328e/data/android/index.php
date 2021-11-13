<?php
	require_once 'include/DB_Functions.php';
	$db=new DB_Functions();

	if(isset($_POST['tag'])&& $_POST['tag']!='')
	{
		$tag=$_POST['tag'];
	
		$json=array("tag"=>$tag,"thanhcong"=>0,"loi"=>0);																			
	
		if($tag=='login')
		{
			xulydangnhap($json,$db);
		}
		else if($tag=='register')
		{
			xulydangki($json,$db);
		}
		else 
		{
			echo "yeu cau khong hop le";
		}
	}
	else
	{
		echo "truy cap khong duoc";
	}
	
	
	function xulydangnhap($json,$db)
{
	$email=$_POST['email'];
	$password=$_POST['password'];
	$user=$db->getUser($email,$password);
	
	if($user!=false) // tim thay user
	{
		$json["thanhcong"]=1;
		$json["user"]["id"]=$user["id"];
		$json["user"]["picture_user"]=$user["picture_user"];
		$json["user"]["name"]=$user["user_name"];
		$json["user"]["email"]=$user["email"];
		$json["user"]["password"]=$user["password"];
	
		
		echo json_encode($json);
	}
	else //tim khong thay user
	{
		$json["loi"]=1;
		$json["thongbaoloi"]="sai email hoac password";
		$json["$password"]="$email";
		echo json_encode($json);
	}
}

function xulydangki($json,$db)
{	$picture=$_POST['picture_user'];
	$name=$_POST['name'];
	$email=$_POST['email'];
	$password=$_POST['password'];
	$imglocation="img";

	if($db->checkUser($email))//true : da ton tai
	{
		$json["loi"]=2;
		$json["thongbaoloi"]="email da ton tai roi";
		
		echo json_encode($json);
	}
	else//user chua ton tai, luu du lieu
	{
		if (!file_exists($imglocation)) {
		mkdir($imglocation,0777,true);
	}
	
	$imglocation=$imglocation ."/". rand() . "_". time() .".jpg";

		if($picture!=null){
			$user=$db->storeUser($imglocation,$name,$email,$password);
		}
		else{$user==$db->storeUser("img/avatar.png",$name,$email,$password);}
		
		
		file_put_contents($imglocation, base64_decode($picture));
		
		if($user!=false )//neu luu thanh cong
		{
			$json["thanhcong"]="1";
			$json["user"]["picture_user"]=$user["picture_user"];
			$json["user"]["name"]=$user["name"];
			$json["user"]["email"]=$user["email"];
			echo json_encode($json);
		}
		else //neu luu that bai
		{
			$json["loi"]=1;
			$json["thongbaoloi"]="dang ki that bai";
			$json["thongbaoloi1"]=$imglocation;
			$json["thongbaoloi2"]=$name;
			$json["thongbaoloi3"]=$email;
			
			echo json_encode($json);
		}
	}
}

	
?>
