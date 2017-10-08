<?php  

/*
db config for Eloquent
*/

global $conn;
$conn = new mysqli("localhost", "allen", "allenpwd", "auction");

return [  
  'determineRouteBeforeAppMiddleware' => false,
  'outputBuffering' => false,
  'displayErrorDetails' => true,
  'db' => [
    'driver' => 'mysql',
    'host' => 'localhost',
    'port' => '3306',
    'database' => 'auction',
    'username' => 'allen',
    'password' => 'allenpwd',
    'charset' => 'utf8',
    'collation' => 'utf8_unicode_ci',
  ]
];
