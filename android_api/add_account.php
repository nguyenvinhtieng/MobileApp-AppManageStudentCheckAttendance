<?php
    require_once ('connection.php');

    if (!isset($_POST['username']) || !isset($_POST['password']) || !isset($_POST['lever'])) {
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $username = $_POST['username'];
    $password = $_POST['password'];
    $lever = $_POST['lever'];

    $sql = 'INSERT INTO account(UserName,PassWord,Lever) VALUES(?,?,?)';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array($username,$password,$lever));
        
        echo json_encode(array('status' => true, 'data' => $dbCon->lastInsertId(), 'message' => 'Tạo tài khoản thành công'));
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }



?>
