<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require __DIR__ . '/../vendor/autoload.php';
require __DIR__ . '/../models/item.php';
require __DIR__ . '/../models/user.php';
require __DIR__ . '/../models/bid.php';
require __DIR__ . '/../classes/jwt.php';

$config['displayErrorDetails'] = true;
$config['addContentLengthHeader'] = false;
$config = include(__DIR__ . '/../dbconfig.php');
$app = new \Slim\App(["settings" => $config]);

$container = $app->getContainer();
$capsule = new \Illuminate\Database\Capsule\Manager;
$capsule->addConnection($container['settings']['db']);
$capsule->setAsGlobal();
$capsule->bootEloquent();

/*
$capsule->getContainer()->singleton(
  Illuminate\Contracts\Debug\ExceptionHandler::class,
  App\Exceptions\Handler::class
);
*/

// testing
$app->get('/', function ($request, $response) {
  return 'hello world';
});

// testing
$app->get('/hello/{name}', function (Request $request, Response $response) {
    $name = $request->getAttribute('name');
    $response->getBody()->write("Hello, $name");
    return $response;
});

// get all items
$app->get('/api/item', function($request, $response) {
  return $response->getBody()->write(App\Model\Item::all()->toJson(JSON_PRETTY_PRINT));
});

// get one item details
$app->get('/api/item/{id}', function($request, $response, $args) {
  $id = $args['id'];
  $item = App\Model\Item::find($id);
  $response->getBody()->write($item->toJson(JSON_PRETTY_PRINT));
  return $response;
});

// list all users
$app->get('/api/user', function($request, $response) {
  return $response->getBody()->write(App\Model\User::all()->toJson(JSON_PRETTY_PRINT));
});

// get one user details
$app->get('/api/user/{id}', function($request, $response, $args) {
  $id = $args['id'];
  $user = App\Model\User::find($id);
  $response->getBody()->write($user->toJson(JSON_PRETTY_PRINT));
  return $response;
});

// user login by email + pwd, to get a token
$app->post('/api/login', function($request, $response, $args) {
		$data = $request->getParsedBody();
    $email = $data['email'];
    $pwd = $data['pwd'];
		if ($email == NULL || $pwd == NULL) {
    		$feedback = json_encode(array(
        		"error" => 3,
        		"message" => "Incorrect http request",
        ), JSON_PRETTY_PRINT);
        $response->getBody()->write($feedback);
				return $response;
		}

    global $conn;
    $sql = 'SELECT * from users where email="'.$email.'"';
    $rs = $conn->query($sql);
    $arr = $rs->fetch_array();

		// no such email
    if($arr == null){
    		$feedback = json_encode(array(
        		"error" => 2,
        		"message" => "Cannot find user email",
        ), JSON_PRETTY_PRINT);
        $response->getBody()->write($feedback);
				return $response;
    }
		// a matching email was found, now check the password
    else {
    	$hash = $arr['pwd'];
    	if (password_verify($pwd, $hash)) {
    		$token = array();
				$token['id'] = $arr['id'];
    		$feedback = json_encode(array(
        		"error" => 0,
        		"message" => "User logged in successfully",
        		"token" => JWT::encode($token, 'secret_server_key')
        ), JSON_PRETTY_PRINT);
        $response->getBody()->write($feedback);
  			return $response;
			}
			// incorrect password
			else {
    		$feedback = json_encode(array(
        		"error" => 1,
        		"message" => "Incorrect password",
        ), JSON_PRETTY_PRINT);
        $response->getBody()->write($feedback);
				return $response;
			}
		}
});

// new user register
$app->post('/api/login/new', function($request, $response, $args) {
		$data = $request->getParsedBody();

		$name = $data['name'];
		$email = $data['email'];
		$pwd = $data['pwd'];
		if ($name == NULL || $email == NULL || $pwd == NULL) {
			$feedback = json_encode(array(
        "error" => 1,
        "message" => "input error"
        ), JSON_PRETTY_PRINT);
	    $response->withStatus(100)->getBody()->write($feedback);
			return $response;
		}

		// check that there is no duplicate email
    global $conn;
    $sql = 'SELECT * from users where email="'.$email.'"';
    $rs = $conn->query($sql);
    $arr = $rs->fetch_array();

		// no such email
    if($arr != null){
    	$feedback = json_encode(array(
       		"error" => 1,
       		"message" => $email . ": This email is already registered",
      ), JSON_PRETTY_PRINT);
      $response->getBody()->write($feedback);
			return $response;
		}

		$hashedPwd = password_hash($pwd, PASSWORD_DEFAULT);

		$newUser = new App\Model\User;
		$newUser->name = $name;
		$newUser->email = $email;
		$newUser->pwd = $hashedPwd;
		$newUser->save();
		return $response->withStatus(200)->getBody()->write($newUser->toJson(JSON_PRETTY_PRINT));
});

// place bid
// POST Headers define Content-Type = application/x-www-form-urlencoded
$app->post('/api/bid', function($request, $response, $args) {
		$data = $request->getParsedBody();
		$token = $data['token']; // containing the userId $token->id
		$itemId = $data['itemId'];
		$bidPrice = $data['bidPrice'];

		if ($token == NULL || $itemId == NULL || $bidPrice == NULL) {
			$feedback = json_encode(array(
				"error" => 3,
				"message" => "Input Error",
			), JSON_PRETTY_PRINT);
			$response->getBody()->write($feedback);
			return $response;
		}

		try {
			$token = JWT::decode($token, 'secret_server_key');
			if ($token->id == NULL) {
				return $response;
			}
		} catch (Exception $e) {
   		$feedback = json_encode(array(
       		"error" => 2,
       		"message" => "Login Error",
        ), JSON_PRETTY_PRINT);
        $response->getBody()->write($feedback);
			return $response;
		}

		// ensure the userId ($token->id) is a valid user
	  $user = App\Model\User::find($token->id);
  	if ($user == NULL) {
   		$feedback = json_encode(array(
       		"error" => 2,
       		"message" => "Login Error",
        ), JSON_PRETTY_PRINT);
        $response->getBody()->write($feedback);
			return $response;
  	}

		// ensure the item is for bidding
		$item = App\Model\Item::find($itemId);
		if ($item == NULL) {
			$feedback = json_encode(array(
        "error" => 1,
        "message" => "Item not found"
        ), JSON_PRETTY_PRINT);
	    $response->withStatus(200)->getBody()->write($feedback);
			return $response;
		}

		// bidding price must be >= current price
		if ($bidPrice < $item->currentPrice) {
			$feedback = json_encode(array(
        "error" => 1,
        "currentPrice" => $item->currentPrice,
        "message" => "Bid price is not high enough"
        ), JSON_PRETTY_PRINT);
	    $response->withStatus(200)->getBody()->write($feedback);
			return $response;
		}

		// update item
		$item->placedBid += 1;
		$item->currentPrice = $bidPrice + $item->stepPrice;
		$item->lastBidder = $token->id;
		$item->save();

		// create new bid record
		$bid = new App\Model\Bid;
		$bid->itemId = $itemId;
		$bid->userId = $token->id;
		$bid->price = $bidPrice;
		$bid->save();
		return $response->withStatus(200)->getBody()->write($bid->toJson(JSON_PRETTY_PRINT));
});

$app->run();
