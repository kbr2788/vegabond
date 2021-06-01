<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');

    $result = mysqli_query($con, "SELECT autocamp_id, autocamp_name, autocamp_location, tag, subname,autocamp_add_info FROM autocamp");

    $data = array();
    
    if ($result) {
        while ($row=mysqli_fetch_array($result)){
            array_push($data,array('autocamp_id'=>$row[0],'autocamp_name'=>$row[1],'autocamp_location'=>$row[2], 'tag'=>$row[3], 'subname'=>$row[4],'autocamp_add_info'=>$row[5]));
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
