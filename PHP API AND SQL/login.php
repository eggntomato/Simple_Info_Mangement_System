<?php

if(isset($_POST['username']) && isset($_POST['password'])){

    require_once "conn.php";

    $username = $_POST['username'];
    $password = $_POST['password'];
    // Create the SQL query string
    $sql = "select * from login where username='$username' and password= '$password'";

    $result = $conn->query($sql);
  
    if($result->num_rows > 0){
        echo "success";
    } else{

        echo "failure";
    }
}
else echo "NO DATA INPUT";
?>