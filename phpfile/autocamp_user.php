<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');

    $result = mysqli_query($con, "SELECT autocamp_id, autocamp_name,tag,autocamp_view,autocamp_img, subname FROM autocamp order by autocamp_view DESC");
    $result2 = mysqli_query($con,"SELECT birthday,gender,user_detail.taste FROM user NATURAL JOIN user_detail");
    
        $data = array();
    
    if ($result) {
        while ($row=mysqli_fetch_array($result)){
            array_push($data,array('no'=>0,'autocamp_id'=>$row[0],'autocamp_name'=>$row[1],'tag'=>$row[2], 'autocamp_view'=>$row[3],'autocamp_img'=>$row[4], 'subname'=>$row[5]));
        }
    }else{
        echo "SQLerror : ";
        echo mysqli_error($con);
    }
if ($result2) {
        while ($row=mysqli_fetch_array($result2)){
            array_push($data,array('no'=>1,'birthday'=>$row[0],'gender'=>$row[1],'taste'=>$row[2]));
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
