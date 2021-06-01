<?php 
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
 
    $id = $_POST["id"];
    $password = $_POST["password"];
    $user_name = $_POST["user_name"];
    $birthday = $_POST["birthday"];
    $gender = $_POST["gender"];
 

    $statement = mysqli_prepare($con, "INSERT INTO user (id, password, user_name, birthday, gender) VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssss", $id, $password, $user_name, $birthday, $gender);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
   
    $statement2 = mysqli_prepare($con, "SELECT user_id FROM user WHERE id = ?");

    mysqli_stmt_bind_param($statement2, "s", $id);
    mysqli_stmt_execute($statement2);
 
    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $user_id);

    while(mysqli_stmt_fetch($statement2)){
      $response["user_id"] = $user_id;
    }

    echo json_encode($response);
?>