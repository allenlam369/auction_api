<?php
/*
List users
*/
include_once('config.php');
$url = $baseUrl + '/api/user';

$ch = curl_init(); 
curl_setopt($ch, CURLOPT_URL, $url); 

//return the transfer as a string 
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 

// $output contains the output string 
$output = curl_exec($ch); 

// close curl resource to free up system resources 
curl_close($ch);      

echo $output

?>