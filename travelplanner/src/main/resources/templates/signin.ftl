<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Sign in to account</title>
    <link rel="shortcut icon" type="image/png" href="../img/darth-vader-fav.png"/>

    <!-- Bootstrap, fonts -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-select.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" crossorigin="anonymous">
    <script src="js/html5shiv.min.js"></script> <!-- ver 3.7.3 -->
    <script src="js/respond.min.js"></script> <!-- ver 1.4.2 -->
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/jquery.validate.min.js"></script> <!-- version 1.13.1-->
    <script src="js/jquery-ui.min.js"></script> <!-- ver 1.10.3 -->

    <!--angular-->
    <script src="js/angular.min.js"></script> <!-- version 1.4.3 -->
    <script type="text/javascript" src="js/angular-sanitize.min.js"></script> <!-- version 1.4.8 -->

    <!-- bootstrap select -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-select.min.js"></script>

    <!-- bootstrap datetimepicker -->
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>

    <!-- my css -->
    <link href="css/sign-styles.css" rel="stylesheet">

    <script>
        element.addEventListener('input',function(){this.value=this.value.toLowerCase()});​
    </script>
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
            <div style="margin-top: 5px;">
                <img src="../img/vader-icon.png" class="pull-left" style="margin-right: 5px; margin-top: 5px;" aria-hidden="true">
                <a class="navbar-brand" href="/">SMART WAYDER</a>
            </div>
        </div>
        <div class="navbar-collapse collapse"  id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="/signIn">Sign in</a></li>
                <li><a href="/signUp">Sign up</a></li>
                <li><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> Profile</a></li>
            </ul>
        </div>
    </div>
</div>
<div>
    <#--<img src="img/imgMain.jpg"  id="sign-pic" class="background-sign-pic">-->
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
                    <input type="text" class="input pass" id="username" oninput="this.value=this.value.toLowerCase()" placeholder="E-mail"
                           name="username">
                    <input type="password" class="input pass" id="password" placeholder="Password"
                           name="password">
                    <div class="add-margin">
                        <button type="submit" class="button">Log in</button>
                    </div>

                    <div class="bottom text-center add-margin">
                        New here? <a href="/signUp"><b>Join Us</b></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>