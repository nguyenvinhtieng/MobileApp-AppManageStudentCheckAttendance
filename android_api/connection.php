<?php

    $dbhost = "localhost";
    $dbuser= "root";
    $dbpass ="";
    $dbname = "project_android";

    $conn = mysqli_connect($dbhost,$dbuser,$dbpass,$dbname);
    if($conn){
        mysqli_query($conn,"SET NAME 'utf8'");
    }
    else {
        echo "Bạn đã kêt nối thất bại".mysqli_connect_error();
    }
?>
