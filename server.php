<?php
	$dir = '/web';
	$files = scandir($dir);
    array_splice($files, 0, 2);
    $array = array();
    for($i = 0; $i < count($files); $i++){
        $objectName = (object)array('imageName' => $files[$i], 'imageSaveData' => date("Y-m-d_H-i-s", filemtime($dir."/".$file[$i])));
        array_push($array, $objectName);
    }
    
    $obj = (object) array('ImagesList' => $array);
	echo json_encode($obj);
?>