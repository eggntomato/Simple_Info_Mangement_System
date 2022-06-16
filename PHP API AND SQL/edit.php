<?php
$username = "root";
$password = "";
$id = $_POST['id'];
$ten = $_POST['ten'];
$ns = $_POST['ns'];
$dc = $_POST['dc'];
$sdt = $_POST['sdt'];



$conn = new PDO('mysql:host=localhost;dbname=qlkh', $username, $password);
//Tạo Prepared Statement
$stmt = $conn->prepare('UPDATE `guest` SET `ten` = :ten, `ns` = :ns, `dc` = :dc, `sdt` = :sdt WHERE `guest`.`id` = :id');

$stmt->bindParam(':id',$id);
$stmt->bindParam(':ten',$ten);
$stmt->bindParam(':ns',$ns);
$stmt->bindParam(':dc',$dc);
$stmt->bindParam(':sdt',$sdt);

//Thiết lập kiểu dữ liệu trả về

//Gán giá trị và thực thi
$result = $stmt->execute();

//Hiển thị kết quả, vòng lặp sau đây sẽ dừng lại khi đã duyệt qua toàn bộ kết quả
if ($result == true) echo "success";
?>