<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Profile</title>
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
    <script src="js/my-styles.js"></script>

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
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/"><i class="fa fa-ravelry" aria-hidden="true"></i>Travel planner</a>
            </div>
            <div class="navbar-collapse collapse"  id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/routes">Routes</a></li>
                    <li><a href="/about">About</a></li>
                        <#if !isAuthorized>
                            <li class="active"><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> Profile </a></li>
                        <#else>
                            <li class="active"><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> ${firstname} ${lastname} </a></li>
                        </#if>
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </div>


    <div class="container" id="container-user-general-panel">
        <div class="row">
            <div class="col-sm-12 user-details">
                <div class="user-image">
                    <img src="https://ru.gravatar.com/userimage/136663844/2e0ece2353baa9f598d4cede1dd9eb1b?size=200" alt="user name" title="User Name Here" class="img-circle">
                </div>
                <div class="user-info-block">
                    <div class="user-heading">
                        <h3>User Name Here</h3>
                        <span class="help-block">Voronezh, RU</span>
                    </div>
                    <div class="container navigation">
                        <div class="row" id="row-user-panel">
                            <ul >
                                <li class="col-xs-3 active">
                                    <a data-toggle="tab" href="#information">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </a>
                                </li>
                                <li class="col-xs-3">
                                    <a data-toggle="tab" href="#routes">
                                        <span class="glyphicon glyphicon-globe"></span>
                                    </a>
                                </li>
                                <li class="col-xs-3">
                                    <a data-toggle="tab" href="#events">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </a>
                                </li>
                                <li class="col-xs-3">
                                    <a data-toggle="tab" href="#settings">
                                        <span class="glyphicon glyphicon-cog"></span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="user-body">
                        <div class="tab-content">
                            <div id="information" class="tab-pane active">
                                <h4>Account Information</h4>

                                <h1> Welcome ${firstname} ${lastname}! </h1>

                                <div style="color: #b92c28">Your data:</div>
                                <style type="text/css">
                                    .tftable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #bcaf91;border-collapse: collapse;}
                                    .tftable th {font-size:12px;background-color:#ded0b0;border-width: 1px;padding: 8px;border-style: solid;border-color: #bcaf91;text-align:left;}
                                    .tftable tr {background-color:#e9dbbb;}
                                    .tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #bcaf91;}
                                    .tftable tr:hover {background-color:#ffffff;}
                                </style>

                                <table class="tftable" border="1">
                                    <tr><th></th><th>Data</th></tr>
                                    <tr><td>Firstname</td><td> ${firstname} </td></tr>
                                    <tr><td>Lastname</td><td> ${lastname} </td></tr>
                                    <tr><td>E-mail</td><td> ${email} </td></tr>
                                    <tr><td>Birthday</td><td> ${birthdate} </td></tr>
                                </table>
                            <#--<div class="container" id="container-user">-->
                            <#--<div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-xs-12">-->
                            <#--<div class="full-width">-->
                            <#--<input type="file" id="base-input" class="form-control form-input form-style-base">-->
                            <#--<input type="text" id="fake-input" class="form-control form-input form-style-fake" placeholder="Choose your File" readonly>-->
                            <#--<span class="glyphicon glyphicon-open input-place"></span>-->
                            <#--</div>-->

                            <#--<div class="full-width">-->
                            <#--<h1 class="text-center color">Edit Profile Snippet</h1>-->
                            <#--<div class="col-sm-12">-->
                            <#--<div class="custom-form">-->
                            <#--<div class="text-center bg-form">-->
                            <#--<div class="img-section">-->
                            <#--<img src="http://lorempixel.com/200/200/nature/" class="imgCircle" alt="Profile picture">-->
                            <#--<span class="fake-icon-edit" id="PicUpload" style="color: #ffffff;"><span class="glyphicon glyphicon-camera camera"></span></span>-->
                            <#--<div class="col-lg-12">-->
                            <#--<h4 class="text-right col-lg-12"><span class="glyphicon glyphicon-edit"></span> Edit Profile</h4>-->
                            <#--<input type="checkbox" class="form-control" id="checker">-->
                            <#--</div>-->
                            <#--</div>-->
                            <#--<input type="file" id="image-input" onchange="readURL(this);" accept="image/*" disabled class="form-control form-input Profile-input-file" >-->
                            <#--</div>-->
                            <#--<div class="col-lg-12 col-md-12">-->
                            <#--<input type="text" class="form-control form-input" value="Abhinit Roy" placeholder="Name" disabled id="name">-->
                            <#--<span class="glyphicon glyphicon-user input-place"></span>-->
                            <#--</div>-->
                            <#--<div class="col-lg-12 col-md-12">-->
                            <#--<input type="text" class="form-control form-input" value="rgba@gmail.com" placeholder="Email ID" disabled id="email">-->
                            <#--<span class="glyphicon glyphicon-envelope input-place"></span>-->
                            <#--</div>-->
                            <#--<div class="col-lg-12 col-md-12">-->
                            <#--<input type="text" class="form-control form-input" value="+91-00000000" placeholder="Phone Number" disabled id="phone">-->
                            <#--<span class="glyphicon glyphicon-earphone input-place"></span>-->
                            <#--</div>-->
                            <#--<div class="col-lg-12 col-md-12">-->
                            <#--<input type="text" class="form-control form-input" value="Chandigarh, India" placeholder="Address" disabled id="place">-->
                            <#--<span class="glyphicon glyphicon-map-marker input-place"></span>-->
                            <#--</div>-->
                            <#--<div class="col-lg-12 col-md-12 text-center">-->
                            <#--<button class="btn btn-info btn-lg custom-btn" id="submit" disabled><span class="glyphicon glyphicon-save"></span> Save</button>-->
                            <#--</div>-->
                            <#--</div>-->
                            <#--</div>-->
                            <#--</div>-->

                            <#--</div>-->
                            <#--</div>-->

                            </div>

                            <div id="routes" class="tab-pane">
                                <h4>Send Message</h4>
                            </div>
                            <div id="events" class="tab-pane">
                                <h4>Events</h4>
                            </div>
                            <div id="settings" class="tab-pane">
                                <h4>Settings</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>




    <#--<div>-->
    <#--<button type="button" onclick="location.href='/logout'">Logout</button>-->
    <#--</div>-->
</body>
</html>