<!DOCTYPE html>
<html lang="en" ng-app="myApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Travel Planner</title>
    <!-- Bootstrap, fonts -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-select.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

    <!-- bootstrap touchspin -->
    <link rel="stylesheet" href="css/jquery.bootstrap-touchspin.css" />
    <script src="js/jquery.bootstrap-touchspin.js"></script>
    <!--angular-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>
    <script src="js/my-angular-modules.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.min.js"></script>
    <!-- bootstrap select -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-select.min.js"></script>
    <!-- bootstrap datetimepicker -->
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <!-- bootstrap fix to -->
    <script src="js/jquery-scrolltofixed.js"></script>

    <!-- my css -->
    <link href="css/styles.css" rel="stylesheet">
    <!--<link href="css/sign-in-style.css" rel="stylesheet">-->
    <!-- my scripts -->
    <script src="js/my-styles.js"></script>

    <!-- js styles for accordion with filters-->
<#--<script src='//production-assets.codepen.io/assets/editor/live/console_runner-079c09a0e3b9ff743e39ee2d5637b9216b3545af0de366d4b9aad9dc87e26bfd.js'></script>-->
<#--<script src='//production-assets.codepen.io/assets/editor/live/events_runner-73716630c22bbc8cff4bd0f07b135f00a0bdc5d14629260c3ec49e5606f98fdd.js'></script>-->
<#--<script src='//production-assets.codepen.io/assets/editor/live/css_live_reload_init-2c0dc5167d60a5af3ee189d570b1835129687ea2a61bee3513dee3a50c115a77.js'></script>-->
<#--<link rel="shortcut icon" type="image/x-icon" href="//production-assets.codepen.io/assets/favicon/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico" />-->
<#--<link rel="mask-icon" type="" href="//production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg" color="#111" />-->
<#--<link rel="canonical" href="https://codepen.io/carlosdelreal/pen/XXQZVQ?limit=all&page=28&q=Accordion" />-->

    <!-- map -->
    <script src="js/google-map.js"></script>
    <script src="js/autoComplite.js"></script>
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBpOm2tBurzyefOG_hBFEXQIkLbkZpSvws&callback=initMap&language=en&libraries=geometry,places"></script>
</head>

<body ng-controller="appController" onload="initAutocompleteFields()">

<!-- navbar -->
<#if !isAuthorized>
<my-navbar></my-navbar>
<#else>
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
        <div class="navbar-collapse collapse" id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> ${firstname} ${lastname} </a></li>
                <li><a href="/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</div>
</#if>

<div>
    <!-- header -->
    <div class="container">
        <div class="row">
            <div id="travel-header-row" class="col-sm-12">
                <h1>Start your journey right now</h1>
            </div>
        </div>
    </div>

    <!-- parameter menu-->
    <div class="col-sm-12">
        <div class="container-fluid">
            <div class="container">
                <div class="formBox" id=>
                    <form id="inputForm" autocomplete="off">
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="inputBox ">
                                    <div class="inputText">Departure city</div>
                                    <input type="text" id="inputFrom" class="input" onfocus="geolocate()" placeholder="">
                                </div>
                            </div>

                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="inputText">Arrival city</div>
                                    <input type="text" id="inputTo" class="input" onfocus="geolocate()" placeholder="">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="inputText">Departure date</div>
                                    <input type='text' class="input" id="inputDate" placeholder="">
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="input dropdown-toggle " id="test">
                                        <a href="#" class=" inputText" data-toggle="dropdown">More parameters<span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
                                        <ul class="dropdown-menu mega-dropdown-menu row">
                                            <li class="col-sm-12">
                                                <ul>
                                                    <li class="dropdown-header centered">Passengers</li>
                                                    <div class="drop-passengers">
                                                        <div class="spinner-inline">
                                                            <div class="col-sm-6">
                                                                <input id="spin-passengers" class="spinner text-center" type="text" value="1" name="spinner-passengers">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </ul>
                                                <div class="divider-inverse"></div>
                                            </li>
                                            <li class="col-sm-12">
                                                <ul>
                                                    <li class="dropdown-header centered">Type of transport</li>
                                                    <div class="row form-check">
                                                        <div class="col-sm-4">
                                                            <label class="checkbox">
                                                                <input id="need-plane" type="checkbox" checked><span>Plane</span>
                                                            </label>
                                                        </div>
                                                        <div class="col-sm-4">
                                                            <label class="checkbox">
                                                                <input id="need-bus" type="checkbox" checked><span>Bus</span>
                                                            </label>
                                                        </div>
                                                        <div class="col-sm-4">
                                                            <label class="checkbox">
                                                                <input id="need-train" type="checkbox" checked><span>Train</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </ul>
                                                <div class="divider-inverse"></div>
                                            </li>
                                            <li class="col-sm-12">
                                                <ul>
                                                    <li class="dropdown-header centered">Transfers</li>
                                                    <div class="row form-check">
                                                        <div class="col-sm-12">
                                                            <label class="checkbox">
                                                                <input id="fewer-transfers" type="checkbox"><span>Find routes with fewer transfers</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </ul>
                                            </li>
                                            <#--<li class="col-sm-12">-->
                                                <#--<ul>-->
                                                    <#--<li class="dropdown-header centered">Max time for transferring</li>-->
                                                    <#--<div class="col-sm-12 margin-offset">-->
                                                        <#--<input id="range-slider" type="range" min="0" max="24" step="1" value="0">-->
                                                    <#--</div>-->
                                                <#--</ul>-->
                                            <#--</li>-->
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- autocomplete -->
                        <div class="row" ng-show="false">
                            <div class="col-sm-4">
                                <div class="inputBox ">
                                    <input type="text" class="input" id="latit_longit_from">
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="inputBox ">
                                    <input type="text" class="input" id="latit_longit_to">
                                </div>
                            </div>
                        </div>
                        <div class="row" ng-show="false">
                            <div class="col-sm-4">
                                <div class="inputBox ">
                                    <input type="text" class="input" id="inputFromHidden">
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="inputBox ">
                                    <input type="text" class="input" id="inputToHidden">
                                </div>
                            </div>
                        </div>
                        <!-- angular controller for getting routes -->
                        <div class="row" ng-controller="myParameterController">
                            <div class="col-sm-12">
                                <input type="button" name="" ng-click="sendRequestParameters()" required class="button" value="Find route" onclick="this.parentNode.submit();">

                                <#--<div ng-show="loading" class="preloader"></div>-->
                                <div class="row" ng-show="loading">
                                    <div  class="col-sm-12 page-loader" style="">
                                        <div class="loader">
                                            <span class="dot dot_1"></span>
                                            <span class="dot dot_2"></span>
                                            <span class="dot dot_3"></span>
                                            <span class="dot dot_4"></span>
                                        </div>
                                    </div>
                                </div>
                                <div ng-show="loaded" ng-init="optimalFilter = optimalRoutes; busFilter = allRoutes; orderByAttribute = 'cost'">
                                    <div ng-controller="mapController">
                                            <div class="output-collapse">
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-sm-12 nomargin">
                                                            <div class="accordion-panel">
                                                                <div class="buttons-wrapper">
                                                                    <i class="plus-icon"></i>
                                                                    <div class="open-btn">
                                                                        Open all
                                                                    </div>
                                                                    <div class="close-btn hidden">
                                                                        Close all
                                                                    </div>
                                                                </div>

                                                            <div class="container-fluid">
                                                                <dl class="row accordion">
                                                                    <div class="col-sm-4 nomargin accordion">
                                                                        <dt>Show filter<i class="plus-icon"></i></dt>
                                                                        <dd>
                                                                            <div class="content">
                                                                                <div class="funkyradio">
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio1" id="radio1" ng-click="optimalFilter = optimalRoutes"/>
                                                                                        <label for="radio1">Optimal routes</label>
                                                                                    </div>
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio1" id="radio2" ng-click="optimalFilter = allRoutes"/>
                                                                                        <label for="radio2">All routes</label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </dd>
                                                                    </div>
                                                                    <div class="col-sm-4 nomargin accordion">
                                                                        <dt>Transport type filter<i class="plus-icon"></i></dt>
                                                                        <dd>
                                                                            <div class="content">
                                                                                <div class="funkyradio">
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio2" id="radio3" ng-click="busFilter = routesWithoutBus"/>
                                                                                        <label for="radio3">Without bus</label>
                                                                                    </div>
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio2" id="radio4" ng-click="busFilter = allRoutes"/>
                                                                                        <label for="radio4">With bus</label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </dd>
                                                                    </div>
                                                                    <div class="col-sm-4 nomargin accordion">
                                                                        <dt>Order routes by<i class="plus-icon"></i></dt>
                                                                        <dd>
                                                                            <div class="content">
                                                                                <div class="funkyradio" >
                                                                                    <div class="funkyradio-default" >
                                                                                        <input type="radio" name="radio3" id="radio5" ng-click="orderByAttribute = 'cost'"/>
                                                                                        <label for="radio5">Cost</label>
                                                                                    </div>
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio3" id="radio6" ng-click="orderByAttribute = 'duration'"/>
                                                                                        <label for="radio6">Duration</label>
                                                                                    </div>
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio3" id="radio7" ng-click="orderByAttribute = 'startDate'"/>
                                                                                        <label for="radio7">Departure time</label>
                                                                                    </div>
                                                                                    <div class="funkyradio-default">
                                                                                        <input type="radio" name="radio3" id="radio8" ng-click="orderByAttribute = 'transfers'"/>
                                                                                        <label for="radio8">Transfers quantity</label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </dd>
                                                                    </div>
                                                                </dl>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-sm-5">
                                                            <div class="fancy-collapse-panel" >
                                                                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
                                                                    <div ng-repeat="record in records | filter:optimalFilter | filter:busFilter | filter:costFilter | orderObjectBy:orderByAttribute">
                                                                        <#--<br>-->

                                                                        <div class="panel panel-default" >
                                                                            <div class="panel-heading" role="tab" id="headingOne" >
                                                                                <h4 class="panel-title">
                                                                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href=#id-for-view-{{record.idRouteForView}} ng-click="setMap(record.edges)" aria-expanded="false" aria-controls=id-for-view-{{record.idRouteForView}}>
                                                                                        <div class="route-description"><b>{{record.description}}</b></div>
                                                                                        <div ng-repeat="item in record.edges">
                                                                                            <div class="route-header" ng-repeat="transits in item.transitEdgeList"> {{transits.startPoint.name}} <span ng-bind-html=' item.transportType | transportTypeToIcon'></span> {{transits.endPoint.name}} </div>
                                                                                        </div>
                                                                                        <div class="divider-inverse"></div>
                                                                                        <div class="json-data">
                                                                                            <p><b>Total cost:</b> {{record.cost}} (RUB)</p>
                                                                                            <p><b>Travel time:</b> {{record.duration | secondsToTime | date: 'HH:mm'}}</p>
                                                                                        </div>
                                                                                    </a>
                                                                                </h4>
                                                                            </div>
                                                                            <div id=id-for-view-{{record.idRouteForView}} class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                                                                                <div class="panel-body" ng-repeat="item in record.edges">
                                                                                    <div ng-repeat="transits in item.transitEdgeList">
                                                                                        <p><b>Transit:</b> {{transits.startPoint.name}} - {{transits.endPoint.name}}</p>
                                                                                        <p><b>Departure:</b> {{transits.departure | localDateTimeToString}}</p>
                                                                                        <p><b>Arrival:</b> {{transits.arrival | localDateTimeToString}}</p>
                                                                                    </div>
                                                                                    <p><b>Cost:</b> {{item.cost}} (RUB)</p>
                                                                                    <div class="row">
                                                                                        <div class="col-sm-offset-2 col-sm-8 col-sm-offset-2 centered">
                                                                                            <section>
                                                                                            <#if isAuthorized>
                                                                                                <div ng-disabled="saved(record.idRouteForView)">
                                                                                                    <button class='dotted thin' ng-click="saveRoute(record)">Save route</button>
                                                                                                </div>
                                                                                            </#if>
                                                                                                <button class='dotted thin' ng-click="openLink(item.purchaseLink)">Buy ticket</button>
                                                                                            </section>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-7" id="map-margin">
                                                            <div class="container-map" id="map"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <script src="https://cdn.jsdelivr.net/rangeslider.js/2.3.0/rangeslider.min.js"></script>
</body>
</html>