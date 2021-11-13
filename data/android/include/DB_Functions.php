<?php
class DB_Functions
{
	public  $db;
	// ham tao
	function __construct()
	{
		require_once("DB_Connect.php");
		$this->db=new DB_Connect();
		$this->db->connect();
	}
	
	function __destruct()
	{
	}
	
	//luu user va database
	public function storeUser($imglocation,$name,$email,$password)
	{
		$sql="INSERT INTO `user`(`picture_user`,`user_name`, `email`, `password`) VALUES ('$imglocation','$name','$email','$password')";
		$con=$this->db->connect();
		// thuc hien lenh sql
		$result=mysqli_query($con,$sql);
		

		if($result==true)
		{
			//chu y: phai dung chung connect thi moi lay duoc id
			$id=mysqli_insert_id($con);//id cuoi cung cung la dulieu vua them

			$result=mysqli_query($this->db->connect(),"select * from `user` where email='$email'");
			
			return mysqli_fetch_array($result);
		}
		else
			return false;
	}
	
	//lay thong tin user dua vao email va password
	public function getUser($email,$password)
	{
		$sql="select * from `user` where email='$email' and password='$password'";
		$result=mysqli_query($this->db->connect(),$sql) ;
		
		$rows=mysqli_num_rows($result);//lay so hang
		
		if($rows>0) //neu co hang tuc la co user
		{
			$result=mysqli_fetch_array($result);
			//echo "co user ne";
			return $result;
		}
		else //khong co user
		{
			//echo "khong co user ne ba";
			return false;
		}
	}
	public function getallUser()
	{
		$sql="select * from `user` ";
		$con=$this->db->connect();
		$result=mysqli_query($con,$sql);
		return $result;
	}
	
	//kiem tra email da co nguoi dung chua
	public function checkUser($email)
	{
		$sql="SELECT * FROM `user` where email='$email'";
		$this->db->close();
		$con=$this->db->connect();
		//$result=mysqli_query($this->db->connect(),$sql);
		
		$result=mysqli_query($con,$sql);
		
		$rows=mysqli_num_rows($result);//lay so hang
	
		if($rows>0) 
		
			return true; //user da ton tai
		else
			return false; //chua co user nay
	}

			//ham ghi product vao database
			public function taocongthuc($id_user,$name,$mota,$imglocation,$loai)
			{
				$sql="INSERT INTO
				 `congthuc`(`id_user`,`name`, `mota`, `picture`, `loai`, `creat_date`) values
				('$id_user','$name','$mota','$imglocation','$loai',CURRENT_TIMESTAMP)";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			
			public function taonoidung($idcongthuc,$nguyenlieu,$noidung,$imglocation)
			{
				$sql="INSERT INTO
				 `noidung`(`id_conthuc`,`nguyenlieu`, `noidung`, `picture_nd`) values
				('$idcongthuc','$nguyenlieu','$noidung','$imglocation')";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			
			//ham lay chi tiet san pham dua vao id
			public function getProductDetail($id)
			{
				$sql="select * from ''
						where id='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
		
		//ham lay tat ca cac san pham
			public function getAllProducts()
			{
				$sql="SELECT * FROM `congthuc` ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			
			public function updateProduct($id,$name,$price,$description)
			{
				$sql="UPDATE `product` SET  `name`='$name',`price`='$price',`description`='$description'where id='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			
			//ham xoa mot san pham
				public function deletecongthuc($id)
			{
				$sql="DELETE FROM `congthuc` where id='$id' ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				
				//ham affected_rows tra ve so record bi
				//anh huong boi cau lenh insert, update,delete
				return mysqli_affected_rows($con);
			}

			public function getidmoitao()
			{
				$sql="SELECT MAX(`id`) FROM `congthuc`";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

				public function getbuatoi()
			{
				$sql="SELECT`user`.`user_name`,`user`.`picture_user`,`congthuc`.`id`, `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture` FROM `congthuc` INNER JOIN `user` ON `congthuc`.`id_user`=`user`.`id` INNER JOIN `noidung` ON `congthuc`.`id`=`noidung`.`id_conthuc`WHERE loai='tối'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function getbuanuoc()
			{
				$sql="SELECT`user`.`user_name`,`user`.`picture_user`,`congthuc`.`id`, `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture` FROM `congthuc` INNER JOIN `user` ON `congthuc`.`id_user`=`user`.`id` INNER JOIN `noidung` ON `congthuc`.`id`=`noidung`.`id_conthuc`WHERE loai='nước'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
				public function getbuasang()
			{
				$sql="SELECT`user`.`user_name`,`user`.`picture_user`,`congthuc`.`id`, `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture` FROM `congthuc` INNER JOIN `user` ON `congthuc`.`id_user`=`user`.`id` INNER JOIN `noidung` ON `congthuc`.`id`=`noidung`.`id_conthuc` WHERE loai='sáng'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
				public function getbuatrua()
			{
				$sql="SELECT`user`.`user_name`,`user`.`picture_user`,`congthuc`.`id`, `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture` FROM `congthuc` INNER JOIN `user` ON `congthuc`.`id_user`=`user`.`id` INNER JOIN `noidung` ON `congthuc`.`id`=`noidung`.`id_conthuc` WHERE loai='trưa' ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
				public function getthucuong()
			{
				$sql="SELECT * FROM `congthuc` WHERE loai='nước' ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
				public function getcontent($id)
			{
				$sql="SELECT `congthuc`.`name`,`noidung`.`id`,`noidung`.`noidung`,`noidung`.`picture_nd`,`noidung`.`nguyenlieu` FROM `congthuc` INNER JOIN `noidung` ON `congthuc`.`id`=`noidung`.`id_conthuc`where `congthuc`.`id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

				public function getcontentid($id)
			{
				$sql="SELECT`congthuc`.`id`, `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture` FROM `congthuc` INNER JOIN `user` ON `congthuc`.`id_user`=`user`.`id`where `congthuc`.`id_user`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function geteditcontentid($id)
			{
				$sql="SELECT `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture`,`noidung`.`nguyenlieu`,`noidung`.`noidung`,`noidung`.`picture_nd` FROM `congthuc` INNER JOIN `noidung` ON `congthuc`.`id`=`noidung`.`id_conthuc`where `congthuc`.`id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function editcontentid($id,$name,$mota,$loai,$picturect,$nguyenlieu,$noidung,$picturend)
			{
				$sql="UPDATE `congthuc`,`noidung` SET `congthuc`.`name`='$name',`congthuc`.`mota`='$mota',`congthuc`.`picture`='$picturect',`congthuc`.`loai`='$loai',`noidung`.`nguyenlieu`='$nguyenlieu',`noidung`.`noidung`='$noidung',`noidung`.`picture_nd`='$picturend'  WHERE  `congthuc`.`id`=`noidung`.`id_conthuc` and  `congthuc`.`id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function editcontentidbnopic($id,$name,$mota,$loai,$nguyenlieu,$noidung)
			{
				$sql="UPDATE `congthuc`,`noidung` SET `congthuc`.`name`='$name',`congthuc`.`mota`='$mota',`congthuc`.`loai`='$loai',`noidung`.`nguyenlieu`='$nguyenlieu',`noidung`.`noidung`='$noidung' WHERE  `congthuc`.`id`=`noidung`.`id_conthuc` and  `congthuc`.`id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function editcontentidctpic($id,$name,$mota,$loai,$picturect,$nguyenlieu,$noidung)
			{
				$sql="UPDATE `congthuc`,`noidung` SET `congthuc`.`name`='$name',`congthuc`.`mota`='$mota',`congthuc`.`picture`='$picturect',`congthuc`.`loai`='$loai',`noidung`.`nguyenlieu`='$nguyenlieu',`noidung`.`noidung`='$noidung' WHERE  `congthuc`.`id`=`noidung`.`id_conthuc` and  `congthuc`.`id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function editcontentidndpic($id,$name,$mota,$loai,$nguyenlieu,$noidung,$picturend)
			{
				$sql="UPDATE `congthuc`,`noidung` SET `congthuc`.`name`='$name',`congthuc`.`mota`='$mota',`congthuc`.`loai`='$loai',`noidung`.`nguyenlieu`='$nguyenlieu',`noidung`.`noidung`='$noidung',`noidung`.`picture_nd`='$picturend' WHERE  `congthuc`.`id`=`noidung`.`id_conthuc` and  `congthuc`.`id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function getstatus($id_user,$id_congthuc)
			{
				$sql="SELECT `favorited`.`id`,`id_user`,`status` FROM `favorited` INNER JOIN `user` ON `favorited`.`id_user`=`user`.`id`where `favorited`.`id_user`='$id_user' AND `favorited`.`id_congthuc`='$id_congthuc'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function getstatusid($id)
			{
				$sql="SELECT `favorited`.`id_user`, `id_congthuc`,`status` FROM `favorited` INNER JOIN `congthuc` ON `favorited`.`id_congthuc`=`congthuc`.`id`where `favorited`.`id_user`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

			public function editstatus($status,$id_user,$id)
			{
				$sql="UPDATE `favorited` SET `status`='$status' where `favorited`.`id_user`='$id_user' AND `favorited`.`id`='$id' ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function insertstatus($id_ct,$status,$id_user)
			{
				$sql="INSERT INTO `favorited`(`id_user`, `id_congthuc`, `status`) VALUES ('$id_user','$id_ct','$status')";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

			public function countfavorited($id_ct)
			{
				$sql="SELECT COUNT(`id_user`) FROM `favorited` WHERE `id_congthuc`='$id_ct' AND `status`=1";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function deletefavorited($id_congthuc)
			{
				$sql="DELETE FROM `favorited` WHERE `id_congthuc`=$id_congthuc ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
		public function getallfavorite()
			{
				$sql="SELECT * FROM `favorited` ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

			public function getcongthuchot($id_congthuc)
			{
				$sql="SELECT COUNT(`favorited`.`id_user`),`congthuc`.`name`,`congthuc`.`id`,`congthuc`.`mota`,`congthuc`.`picture` FROM `favorited` INNER JOIN `congthuc` ON `favorited`.`id_congthuc`=`congthuc`.`id` WHERE `id_congthuc`='$id_congthuc' AND `status`=1";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function getallidcongthuc()
			{
				$sql="SELECT `id`FROM `congthuc`";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

				public function getallcongthuc()
			{
				$sql="SELECT`user`.`user_name`,`user`.`picture_user`,`congthuc`.`id`, `congthuc`.`name`,`congthuc`.`mota`,`congthuc`.`picture`,`congthuc`.`creat_date` FROM `congthuc` INNER JOIN `user` ON `congthuc`.`id_user`=`user`.`id`";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

			public function editaccount($id,$name,$pass,$email,$picturend)
			{
				$sql="UPDATE `user` SET `picture_user`='$picturend',`user_name`='$name',`email`='$email',`password`='$pass' WHERE `id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function editaccount2($id,$name,$pass,$email)
			{
				$sql="UPDATE `user` SET `user_name`='$name',`email`='$email',`password`='$pass' WHERE `id`='$id'";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

			public function getidfavorite($id_user)
			{
				$sql="SELECT `id_user`, `id_congthuc`, `status` FROM `favorited` WHERE `id_user`='$id_user' AND `status`=1";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function getidfavorite2($id_congthuc)
			{
				$sql="SELECT `id`, `id_user`, `name`, `mota`, `picture`, `loai`, `creat_date` FROM `congthuc` WHERE `id`=$id_congthuc";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}

			public function insertbinhluan($id_user,$id_noidung,$binhluan)
			{
				$sql="INSERT INTO `binhluan`( `id_user`, `id_noidung`, `noidung`) VALUES ('$id_user','$id_noidung','$binhluan')";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function getbinhluan($id_nd)
			{
				$sql="SELECT`user`.`user_name`,`user`.`picture_user`,`binhluan`.`id_user`,`binhluan`.`id`,`binhluan`.`noidung` FROM `binhluan` INNER JOIN `user` ON `binhluan`.`id_user`=`user`.`id` WHERE `binhluan`.`id_noidung`='$id_nd' ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function eidtbinhluan($comment,$id,$id_user)
			{
				$sql="UPDATE `binhluan` SET `noidung`='$comment' WHERE `id`='$id' AND `id_user`= '$id_user'  ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
			public function deletebinhluan($id)
			{
				$sql="DELETE FROM `binhluan` WHERE `id`='$id' ";
				$con=$this->db->connect();
				$result=mysqli_query($con,$sql);
				return $result;
			}
}
?>