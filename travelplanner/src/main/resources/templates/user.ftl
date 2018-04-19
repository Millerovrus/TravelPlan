<!DOCTYPE html>
<html lang="en" ng-app="appChangeUserData">
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
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>

    <!-- bootstrap select -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-select.min.js"></script>

    <!-- bootstrap touchspin -->
    <link rel="stylesheet" href="css/jquery.bootstrap-touchspin.css" />
    <script src="js/jquery.bootstrap-touchspin.js"></script>

    <!-- bootstrap datetimepicker -->
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>

    <!--angular-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.min.js"></script>

    <!-- my scripts -->
    <#--<script src="js/my-styles.js"></script>-->
    <script src="js/user-change-info.js"></script>

    <!-- my css -->
    <link href="css/user-styles.css" rel="stylesheet">
    <#--<link href="css/styles.css" rel="stylesheet">-->

</head>
<body>

    <div ng-controller="controllerChangeData">
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
                <div class="col-sm-offset-1 col-sm-10 col-sm-offset-1 user-details">
                    <div class="user-info-block">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="user-image">
                                    <img src=${avatar} alt="user" title="${firstname} ${lastname}" class="img-circle">
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
                                    <li class="col-xs-6 active">
                                        <a data-toggle="tab" href="#information">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </a>
                                    </li>
                                    <li class="col-xs-6">
                                        <a data-toggle="tab" href="#routes" ng-click="printUserRoutes()">
                                            <span class="glyphicon glyphicon-globe"></span>
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
                                                <input type="text" class="form-input" value="${firstname}" placeholder="Name" maxlength="50" disabled id="first-name">
                                            </div>
                                            <div class="col-sm-12">
                                                <h4>Last name</h4>
                                                <input type="text" class="form-input" value="${lastname}" placeholder="LastName" maxlength="50" disabled id="last-name">
                                            </div>
                                            <div class="col-sm-12">
                                                <h4>E-mail</h4>
                                                <input type="text" class="form-input" value="${email}" placeholder="Email ID" disabled id="user-email">
                                            </div>
                                            <div class="col-sm-12">
                                                <h4>Date of birth</h4>
                                                <input type="text" class="form-input" value="${birthdate}" placeholder="Birthdate" disabled id="birth-date">
                                            </div>
                                            <div class="col-sm-12" style="display: none" id="avatar-class">
                                                <h4>Avatar</h4>
                                                <input type="text" class="form-input" value="" placeholder="Please, insert here link to the picture..." disabled id="avatar">
                                            </div>
                                            <#--<div class="row">
                                                <div class="col-sm-11 file-input text-left">
                                                    <input type="file" id="base-input" style="display: none" onchange="readURL(this);" accept="image/*" class="form-style-base">
                                                    <h4 id="fake-input" style="display: none" class="upload-btn form-style-fake"><i class="fa fa-camera"></i> Upload photo</h4>
                                                    <div id="file-name"><i class="fa fa-check"></i></div>
                                                </div>
                                            </div>-->

                                            <div class="col-sm-12 text-center" align="center">
                                                <button class="btn custom-btn" ng-click="change()" onclick="saveEdit()" id="submit-edit" style="display: none" disabled> Save changes</button>
                                            </div>
                                            <div class="col-sm-12">
                                                <div class="edit-section">
                                                    <h4 id="edit-header" class="text-right"><span class="glyphicon glyphicon-edit"></span> Edit Profile</h4>
                                                    <input type="checkbox" onclick="enableEdit()" class="form-control" id="checker22" value="0">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="routes" class="tab-pane">
                                    <#--<h4>Saved routes for ${user_id}</h4>-->
                                    <!-- эту твою фигню сделала невидимой -->
                                    <input value="${user_id}" style="display: none" class="input" id="user_id"> <#--Из этой фигни берётся id текущего мужика-->

                                    <div ng-show="loaded">

                                        <div class="container-fluid" >
                                            <div class="row" ng-if="records.length===0">
                                                <h4 class="text-center">There are no saved routes yet.</h4>
                                            </div>
                                            <div class="row-striped" ng-show="records.length!==0">
                                                <div class="row">
                                                    <h4 style="margin-left: 20px; margin-top: 10px;">Order routes by</h4>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="content">
                                                            <div class="col-sm-6">
                                                                <div class="funkyradio">
                                                                    <div class="funkyradio-default">
                                                                        <input type="radio" name="radio3" id="radio9" ng-click="orderByAttribute = 'creationDate'" checked/>
                                                                        <label for="radio9">Route creation date</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <div class="funkyradio">
                                                                    <div class="funkyradio-default">
                                                                        <input type="radio" name="radio3" id="radio10" ng-click="orderByAttribute = 'startDate'"/>
                                                                        <label for="radio10">Travel start date</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <br>
                                        <!-- better calendar version-->
                                        <div class="container-fluid">
                                            <div ng-repeat="record in records | orderObjectBy:orderByAttribute" data-ng-class-even="'row-striped-even'" data-ng-class-odd="'row-striped-odd'">
                                                <div>
                                                    <div class="row">
                                                        <div class="col-sm-2 text-right pull-left">
                                                            <h1 class="display-4"><span class="badge badge-secondary">{{record.edges[0].startDate | getDayNumber}}</span></h1>
                                                            <h2 class="header-text">{{record.edges[0].startDate | getMonthNumber | getMonthValue}}</h2>
                                                        </div>
                                                        <div class="col-sm-8">
                                                            <h3 class="text-uppercase header-text"><b> {{record.startPoint}} - {{record.destinationPoint}} </b></h3>
                                                            <ul class="list-inline">
                                                            <#--<li class="list-inline-item"><span class="glyphicons glyphicons-coins" aria-hidden="true"></span> {{record.cost}} RUB</li>-->
                                                                <li class="list-inline-item"><i class="fa fa-tag" aria-hidden="true"></i> {{record.cost}} RUB</li>
                                                                <li class="list-inline-item"><i class="fa fa-clock-o" aria-hidden="true"></i> {{record.edges[0].startDate | getTimeFromDate}}</li>
                                                                <li class="list-inline-item"><i class="fa fa-location-arrow" aria-hidden="true"></i> {{record.startPoint}} </li>
                                                            </ul>
                                                        <#--<br>-->
                                                            <div ng-repeat="item in record.edges">
                                                                <div ng-repeat="transit in item.transitEdgeList">
                                                                    <p>
                                                                        <b class="font-weight">Transit:</b> {{transit.startPoint.name}} - {{transit.endPoint.name}} <br>
                                                                        <b class="font-weight">Transport type:</b> {{item.transportType}} <br>
                                                                        <b class="font-weight">Departure date:</b> {{transit.departure | getDayNumber}}  {{transit.departure | getMonthNumber | getMonthValue}} at {{transit.departure | getTimeFromDate}}<br>
                                                                        <b class="font-weight">Arrival date:</b> {{transit.arrival | getDayNumber}}  {{transit.arrival | getMonthNumber | getMonthValue}} at {{transit.arrival | getTimeFromDate}} <br>
                                                                        <b class="font-weight">Cost:</b> {{item.cost}} RUB
                                                                        <#--<b class="font-weight">ID:</b> {{record.id}}-->
                                                                    <div class="add-margin user-but">
                                                                        <button type="submit" class="button" ng-click="openLink(item.purchaseLink)">Tickets</button>
                                                                    </div>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-2">
                                                            <input class="input" id="route_id" style="display: none" value="{{record.id}}">
                                                            <div class="btn-group pull-right">
                                                                <a  href="#"  class="btn btn-inverse button-del" ng-click="deleteSavedRoute(record.id)"><i class="fa fa-trash"></i></a>
                                                                <#--style="background-color: rgba(45,45, 45, 0.5); color: #fff"-->
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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