<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Create account</title>
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

    <!-- my scripts -->
    <script src="js/sign-user-styles.js"></script>

   <#--recaptcha V2-->
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <script type="text/javascript">
        function callback(){
            if($('#g-recaptcha-response').val().length !== 0){
                console.log("The user has already solved the captcha, now you can submit your form.");
                return true;}
            else {
                window.alert("I'm recaptcha. Touch me, please!")
                return false;}
        }
    </script>
    <script>
        element.addEventListener('input',function(){this.value=this.value.toLowerCase()});​
    </script>
</head>

<body>
    <!-- menu -->
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
                    <li><a href="/signIn">Sign in</a></li>
                    <li class="active"><a href="/signUp">Sign up</a></li>
                    <li><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> Profile</a></li>
                </ul>
            </div>
        </div>
    </div>

    <#--<img src="img/imgMain.jpg"  id="sign-pic" class="background-sign-pic">-->
    <div class="container">
        <div class="col-sm-12">
            <div id="logbox">
                <form id="signUp" action="" method="post" onsubmit="return callback();">
                    <h1>Create an Account</h1>
                    <#if email??>
                        <div class="alert alert-danger" role="alert">Email already exists!</div>
                    </#if>

                    <input id="inputFirstName" name="firstname" type="text" placeholder="First name" maxlength="50" class="input pass" required/>
                    <input id="inputLastName" name="lastname" type="text" placeholder="Last name" maxlength="50" class="input pass" required/>
                    <input id="inputBirthDate" name="birthdate" type="text" class="input pass" placeholder="Date of birth" required/>
                    <input id="inputEmail" name="email" type="email" oninput="this.value=this.value.toLowerCase()" placeholder="Email address" maxlength="255" class="input pass" required/>
                    <input id="inputPassword" name="password" type="password" placeholder="Choose a password" maxlength="255" required class="input pass"/>
                <#--amazon key captcha-->
                <#--<div align="center" class="g-recaptcha add-margin" data-sitekey="6Ldyj1IUAAAAAKFVX7twbmYLIArlJWsqQll0IShp"></div>-->
                    <div align="center" class="g-recaptcha add-margin" data-sitekey="6Lc9p1IUAAAAAOBwW-LE1IPih6AcW_CpDY3bsxaQ"></div>
                    <div class="add-margin">
                        <input type="submit" value="Sign up" class="button"/>
                    </div>
                </form>

            </div>
        </div>
    </div>


</body>
</html>