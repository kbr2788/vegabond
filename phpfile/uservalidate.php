<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];

    $statement = mysqli_prepare($con, "SELECT id FROM user WHERE id = ?");

    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id);

    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){
      $response["success"] = false;
      $response["id"] = $id;
    }

    echo json_encode($response);
?>