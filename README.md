Auction_API
================================================================================== 
a skeleton RESTful API server for an auction system, in PHP and SLIM3

- Auction_api is a PHP auction system having Restful APIs to connect to various clients.
- At this stage it is a skeleton system with limited features and functionalities. Its purpose is to demostrate how an auction system can be designed and implemented with Restful APIs. It can also be used as a base of further development.

## Project Dependencies
- PHP 5.6+
- SLIM 3 framework
- MySQL
- illuminate/database as ORM
- a httpd server (here we are using php's built-in http service for testing)
- Composer (http://getcomposer.org) for downloading vendor libraries


## Installation and configurations
- Download the zip of this app. Unpack it to your web root directory
- Best to define your DocumentRoot to point to the src/public directory of this project. The primary file for running the server is src/public/index.php
- Use src/models/auction.sql (a phpMyAdmin SQL Dump) to create a MySql DB and tables
- Edit src/dbconfig.php for DB connection
- In command line, cd to src/, run 'composer update' to download missing library files. The downloaded files will be stored under src/vendor


## Command Line Interface
- Under src/cli there are several php files for command line interaction with the server.
- Before using the cli programs, the http server has to be started
- A quick way to start a web server is to use PHP's built-in web server. In command line, cd to src/public, then start a http service by command 'php -S localhost:8080'
- The several cli php files assume the domain is localhost, the http port is 8080, the DocumentRoot is src/public. If you have other settings you need to edit the file src/cli/config.php
- After starting up the web server, cd to src/cli, run commands like "php listitems.php", "php getitem.php", and other php programs
- The returned info to the command line are in JSON pretty-print formats for easy reading


## API Functions and its corresponding cli programs
| Function                                     | cli program   | HTTP Verb | URL
|----------------------------------------------|---------------|-----------|---------------
| Browsing items on auction                    | listitems.php | GET       | /api/item
| Get details of an item by ID                 | getitem.php   | GET       | /api/item/{id}
| Browsing users                               | listusers.php | GET       | /api/user
| Get details of an user by ID                 | getuser.php   | GET       | /api/user/{id}
| New user registration                        | newuser.php   | POST      | /api/login/new
| User Login                                   | login.php     | POST      | /api/login
| Placing a bid on an item by a logged-in user | bid.php       | POST      | /api/bid



## Create a few new users
- The DB restored from the dump contains two pre-loaded auction items (id is 1 and 2). You can view the items by listitems.php and getitem.php
- At the beginning there is no user in the DB. You can create some user accounts by providing 3 informations: username, email, and password
- create a new user by "php newuser.php"
- For example, username=aa, email=aa@gmail.com, password=aa (or anything you like)
- Users login by email and password (by login.php). Email is used as a unique identification for users. User ID is for system uses
- After a successful login, the system will response with a token to the client. The client should attach the token to further http requests with the system. bid.php has combined these two-step procedures into one
- For simplicity, all bidding prices are defined as integers. No cent-value is handled.


## Further developments
- user email verification after registration
- define user-type to classify different users, e.g. admin, public, etc
- different authorization to acess different APIs based on user-types
- define item status (e.g. active, closed) to allow/disallow bidding
- allow different currencies for listing and bidding
- many more...

