<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
    
    $do_name = $_POST["do_name"];

    $result = mysqli_query($con, "SELECT autocamp_id FROM autocamp WHERE autocamp_address like '".$do_name."%'");

    $data = array();

    if ($result) {
        while ($row=mysqli_fetch_array($result)){
            array_push($data,array('autocamp_id'=>$row[0]));
        }
        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("autocamp"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }else{
        echo "SQLerror : ";
        echo mysqli_error($con);
    }

    mysqli_close($con);

?>