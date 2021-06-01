<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
    
    $autocamp_id= $_POST["autocamp_id"];
    $sql = "SELECT user.user_name, review.contents,review.user_id,review.rating,review.review_maketime FROM review natural join user WHERE autocamp_id =" .$autocamp_id."";
    $sql2 = "SELECT sentence,review_p,positive FROM sns_review WHERE autocamp_id =" .$autocamp_id."";
    $result = mysqli_query($con, $sql);//.$autocamp_id.);
    $result2 = mysqli_query($con, $sql2);
    
    $data = array();
    
    if ($result) {
        while ($row=mysqli_fetch_array($result)){
            array_push($data,array('no'=>0,'user_name'=>$row[0],'contents'=>$row[1],'user_id'=>$row[2],'rating'=>$row[3],'review_maketime'=>$row[4]));
        }
    }else{
        echo "SQLerror : ";
        echo mysqli_error($con);
    }

    if ($result2) {
        while ($row=mysqli_fetch_array($result2)){
            array_push($data,array('no'=>1,'sentence'=>$row[0],'review_p'=>$row[1],'positive'=>$row[2]));
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