<?php
if(isset($_POST['id'])){
	require_once "conn.php";
			$id = $_POST['id'];
			$sql = "DELETE FROM `guest` WHERE `guest`.`id` = $id";

			$res=mysqli_query($conn,$sql);
			
			if($res==true)
			 echo "success";
			else
			 echo "failure";
}
?>