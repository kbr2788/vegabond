<?php
    $con = mysqli_connect("localhost", "holycrab", "holyaloe1!", "holycrab");
    mysqli_query($con,'SET NAMES utf8');
 
    $id = $_POST["id"];
    $password =$_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT user_id, user_name, birthday, gender, camped,taste FROM user natural join user_detail WHERE id = ? AND password = ?");

    mysqli_stmt_bind_param($statement, "ss", $id, $password);
    mysqli_stmt_execute($statement);

 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $user_name, $birthday, $gender, $camped,$taste);
 
    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["user_id"] = $user_id;
        $response["user_name"] = $user_name;      
        $response["birthday"] = $birthday;
        $response["gender"] = $gender;
        $response["camped"] = $camped;
        $response["taste"] = $taste;
    }    echo json_encode($response);
        echo "user end";
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