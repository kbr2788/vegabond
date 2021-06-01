<?php 
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
 
    $user_id = $_POST["user_id"];
    $detail = $_POST["detail"];

 
    $statement = mysqli_prepare($con, "INSERT INTO user_detail (user_id, taste) VALUES (?,?)");
    mysqli_stmt_bind_param($statement, "is", $user_id, $detail);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
   

    echo json_encode($response);
?>