<?php
$username = "root";
$password = "";
$ten = $_POST['ten'];
$ns = $_POST['ns'];
$dc = $_POST['dc'];
$sdt = $_POST['sdt'];
$avatar = $_POST['avatar'];


$conn = new PDO('mysql:host=localhost;dbname=qlkh', $username, $password);
//Tạo Prepared Statement
$stmt = $conn->prepare('INSERT INTO `guest` (`ten`, `ns`, `dc`, `sdt`, `avatar`) VALUES (:ten, :ns, :dc, :sdt, :avatar)');

$stmt->bindParam(':ten',$ten);
$stmt->bindParam(':ns',$ns);
$stmt->bindParam(':dc',$dc);
$stmt->bindParam(':sdt',$sdt);
$stmt->bindParam(':avatar',$avatar);
//Thiết lập kiểu dữ liệu trả về

//Gán giá trị và thực thi
$result = $stmt->execute();

//Hiển thị kết quả, vòng lặp sau đây sẽ dừng lại khi đã duyệt qua toàn bộ kết quả
if ($result == true) echo "success";
?>