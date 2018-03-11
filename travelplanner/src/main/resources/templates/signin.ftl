<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Sign in to account</title>

    <!-- Bootstrap, fonts -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-select.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>

    <!-- my css -->
    <link href="css/my-styles.css" rel="stylesheet">

    <!-- my scripts -->
    <!--<script src="js/my-styles.js"></script>-->

    <!--angular-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>

    <!-- bootstrap select -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-select.min.js"></script>

    <!-- bootstrap datetimepicker -->
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>

</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><i class="fa fa-ravelry" aria-hidden="true"></i>Travel planner</a>
        </div>
        <div class="navbar-collapse collapse"  id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="/signIn">Sign in</a></li>
                <li><a href="/signUp">Sign up</a></li>
                <li><a href="#">About us</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> Profile</a></li>
            </ul>
        </div>
    </div>
</div>
<div>
    <img src="img/imgMain.jpg"  id="sign-pic" class="background-sign-pic">
    <div class="container" id="sign-in-from">
        <div class="col-sm-12">
            <div id="logbox">
                <form method="post" id="signIn">
                <#-- <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">-->
                    <h1>Enter your e-mail and password</h1>
                    <#if logout>
                    <div class="alert alert-success" role="alert">You've been logged out successfully.</div>
                    </#if>
                    <#if error>
                    <div class="alert alert-danger" role="alert">Invalid E-mail or Password!</div>
                    </#if>
                    <input type="text" class="input pass" id="username" placeholder="Email"
                           name="username">
                    <input type="password" class="input pass" id="password" placeholder="Password"
                           name="password">
                    <div class="checkbox">
                        <label>
                            <input id="my-check" type="checkbox"> Keep me logged-in
                        </label>
                    </div>
                    <button type="submit" class="inputButton">Log in</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>