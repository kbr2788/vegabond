<?php 
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
 
    $autocamp_id = $_POST["autocamp_id"];
    $contents = $_POST["contents"];
    $user_id = $_POST["user_id"];
    $rating = $_POST["rating"];
 
    $statement = mysqli_prepare($con, "INSERT INTO review (autocamp_id,contents, user_id,rating) VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "isii", $autocamp_id,$contents, $user_id,$rating);
    mysqli_stmt_execute($statement);
 
    $response = array();
    $response["success"] = true;
   
    echo json_encode($response);
?>