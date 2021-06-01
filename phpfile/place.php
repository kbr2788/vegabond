<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');

    $autocamp_name = $_POST["autocamp_name"];

    $statement = mysqli_prepare($con, "SELECT * FROM autocamp WHERE autocamp_name = ?");

    mysqli_stmt_bind_param($statement, "s", $autocamp_name);
    mysqli_stmt_execute($statement);
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $autocamp_id, $autocamp_name, $autocamp_location, $autocamp_address, $autocamp_img, $autocamp_view, $autocamp_add_info, $tag, $subname);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
      $response["success"] = true;
        $response["autocamp_id"]=$autocamp_id;
      $response["autocamp_name"] = $autocamp_name;
        $response["autocamp_location"] =$autocamp_location;
        $response["autocamp_address"] =$autocamp_address;
        $response["autocamp_img"] =$autocamp_img;
        $response["autocamp_view"] =$autocamp_view;
        $response["autocamp_add_info"] = $autocamp_add_info;
        $response["tag"] =$tag;
        $response["subname"] =$subname;
    }

    echo json_encode($response);
?>