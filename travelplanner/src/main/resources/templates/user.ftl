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
                <div class="user-info-block">
                    <div class="row">
                    <div class="col-sm-3">
                        <div class="user-image">
                            <img src="img/man-user.png" alt="user" title="${firstname} ${lastname}" class="img-circle">
                        </div>
                    </div>
                    <div class="col-sm-9">
                        <div class="user-heading">
                            <h3> ${firstname} ${lastname} </h3>
                            <span class="help-block">${email}</span>
                        </div>
                    </div>
                </div>

                    <div class="container navigation">
                        <div class="row" id="row-user-panel">
                            <ul >
                                <li class="col-xs-4 active">
                                    <a data-toggle="tab" href="#information">
                                        <span class="glyphicon glyphicon-user"></span>
                                    </a>
                                </li>
                                <li class="col-xs-4">
                                    <a data-toggle="tab" href="#routes">
                                        <span class="glyphicon glyphicon-globe"></span>
                                    </a>
                                </li>
                                <li class="col-xs-4">
                                    <a data-toggle="tab" href="#events">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="user-body">
                        <div class="tab-content">
                            <div id="information" class="tab-pane active">
                                <div class="container-fluid" id="container-user">
                                    <div class="custom-form">
                                        <div class="col-sm-12">
                                            <h4>First name</h4>
                                            <input type="text" class="form-input" value="${firstname}" placeholder="Name" disabled id="first-name">
                                        </div>
                                        <div class="col-sm-12">
                                            <h4>Last name</h4>
                                            <input type="text" class="form-input" value="${lastname}" placeholder="LastName" disabled id="last-name">
                                        </div>
                                        <div class="col-sm-12">
                                            <h4>E-mail</h4>
                                            <input type="text" class="form-input" value="${email}" placeholder="Email ID" disabled id="user-email">
                                        </div>
                                        <div class="col-sm-12">
                                            <h4>Date of birth</h4>
                                            <input type="text" class="form-input" value="${birthdate}" placeholder="Phone Number" disabled id="birth-date">
                                        </div>
                                        <div class="col-sm-12 text-right">
                                            <input type="file" id="base-input" style="display: none" onchange="readURL(this);" accept="image/*" class=" form-style-base">
                                            <h4 id="fake-input" style="display: none" class="upload-btn form-style-fake"><i class="fa fa-camera"></i> Upload photo</h4>
                                        </div>

                                        <div class="col-sm-12 text-center" align="center">
                                            <button class="btn custom-btn"  onclick="saveEdit()" id="submit-edit" style="display: none" disabled> Save changes</button>
                                        </div>
                                        <div class="col-sm-12">
                                            <div class="edit-section">
                                                <h4 id="edit-header" class="text-right"><span class="glyphicon glyphicon-edit"></span> Edit Profile</h4>
                                                <input type="checkbox" onclick="enableEdit()" class="form-control" id="checker22" value="0">
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        <div class="box box-info">
                                            <div class="box-body">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="routes" class="tab-pane">
                                <h4>Send Message</h4>
                            </div>
                            <div id="events" class="tab-pane">
                                <h4>Events</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- не трогать, не переносить в head!!!!!!! -->
    <script src="js/user-info.js"></script>


    <#--<div>-->
    <#--<button type="button" onclick="location.href='/logout'">Logout</button>-->
    <#--</div>-->
</body>
</html>