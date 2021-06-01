<?php 
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
 
    $autocamp_id = $_POST["autocamp_id"];

 
    $statement = mysqli_prepare($con, "update autocamp set autocamp_view = autocamp_view + 1 where autocamp_id = ? ");
    mysqli_stmt_bind_param($statement, "i", $autocamp_id);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
   
    echo json_encode($response);
?>