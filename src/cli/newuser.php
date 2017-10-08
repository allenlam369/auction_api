<?php
/**
For creating a new user
*/
include_once('config.php');
$url = $baseUrl . '/api/login/new';

print 'NEW USER REGISTRATION' . "\n";

$name = readline("user name: ");
$email = readline("email address: ");
$pwd = readline("password: ");

// The data to send to the API
$data = 'name='.$name.'&email='.$email.'&pwd='.$pwd;

$ch = curl_init(); 
curl_setopt($ch, CURLOPT_URL, $url); 
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_HEADER, TRUE); 
curl_setopt($ch, CURLOPT_HTTPHEADER, Array("Content-Type: application/x-www-form-urlencoded"));
curl_setopt($ch, CURLOPT_POSTFIELDS, $data);

// receive server response ...
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$server_output = curl_exec ($ch);

echo $server_output;

?>