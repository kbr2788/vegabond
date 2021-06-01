<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
    
    $autocamp_id= $_POST["autocamp_id"];
    $sql = "SELECT sentence,review_p,positive FROM sns_review WHERE autocamp_id =" .$autocamp_id."";
    $result = mysqli_query($con, $sql);//.$autocamp_id.);
    
    $data = array();

    if ($result) {
        while ($row=mysqli_fetch_array($result)){
            array_push($data,array('sentence'=>$row[0],'review_p'=>$row[1],'positive'=>$row[2]));
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