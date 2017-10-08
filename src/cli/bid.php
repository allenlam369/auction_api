<?php
/**
User place a bid. Need login to get a token first.
*/
include_once('config.php');
$url = $baseUrl . '/api/bid';

$json = include 'login.php';
$error = parseError($json);

if ($error != '0') {
	return;
}

$token = parseToken($json);
echo "Login successful " . $token . "\n";

$itemId = readline("Item ID: ");
$price = readline("Bidding price: ");
// The data to send to the API
$data = 'token='.$token.'&itemId='.$itemId.'&bidPrice='.$price;

print $data;


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


function parseToken($json) {
	$json = preg_replace('/\s+/', '', $json);
	$pos = strpos($json, 'token":"');
	$token = substr($json, $pos+8, -2);
  return $token;
}

function parseError($json) {
	$json = preg_replace('/\s+/', '', $json);
	$pos = strpos($json, 'error": ');
	$err = substr($json, $pos+8, 1);
  return $err;
}
?>


