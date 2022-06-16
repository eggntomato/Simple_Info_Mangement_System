<?php
$conn = new mysqli("localhost","root","","qlkh");
$stmt = $conn->prepare("SELECT * FROM `guest`");
$stmt ->execute();
$result = $stmt->get_result();
$outp = $result->fetch_all(MYSQLI_ASSOC);
echo json_encode($outp);
?>