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
<#--<link href="css/sign-in-style.css" rel="stylesheet">-->
    <!-- my scripts -->
    <script src="js/my-styles.js"></script>

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
                                        <a href="#" class=" inputText" data-toggle="dropdown">More parameters  <span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
                                        <ul class="dropdown-menu mega-dropdown-menu row">
                                            <li class="col-sm-12">
                                                <ul>
                                                    <li class="dropdown-header centered">Passengers</li>
                                                    <div class="drop-passengers">
                                                        <div class="spinner-inline">
                                                            <div class="col-sm-6">
                                                                <label for="spin-adult">Adults  </label>
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <input id="spin-adult" class="spinner text-center" type="text" value="1" name="spinner-adult">
                                                            </div>
                                                        </div>
                                                        <div class="spinner-inline">
                                                            <div class="col-sm-6">
                                                                <label for="spin-children">Children</label>
                                                            </div>
                                                            <div class="col-sm-6">
                                                                <input id="spin-children" class="spinner text-center" type="text" value="0" name="spinner-children">
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
                                                        <div class="col-sm-6">
                                                            <label class="checkbox">
                                                                <input id="only-plane" type="checkbox"><span>Only plane</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="row form-check">
                                                        <div class="col-sm-6">
                                                            <label class="checkbox">
                                                                <input id="use-bus" type="checkbox"><span>Find routes with bus</span>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </ul>
                                                <div class="divider-inverse"></div>
                                            </li>
                                            <li class="col-sm-12">
                                                <ul>
                                                    <li class="dropdown-header centered">Max time for transferring</li>
                                                    <div class="col-sm-12 margin-offset">
                                                        <input id="range-slider" type="range" min="0" max="24" step="1" value="0">
                                                    </div>
                                                </ul>
                                            </li>
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
                                <div ng-show="loading" class="preloader"></div>
                                <div ng-show="loaded" ng-init="filterOptimalRoutes = optimalRoutes">
                                    <div ng-controller="mapController">
                                            <div class="output-collapse">
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-sm-5">
                                                            <div class="fancy-collapse-panel" >
                                                                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
                                                                    <button ng-click="orderByAttribute = 'cost'">Order by Cost</button>
                                                                    <button ng-click="orderByAttribute = 'duration'">Order by Duration</button>
                                                                    <button ng-click="orderByAttribute = 'startDate'">Order by Start Date</button>
                                                                    <button ng-click="orderByAttribute = ''">Reset order</button>
                                                                    <button ng-click="filterOptimalRoutes = optimalRoutes">Only Optimal Routes</button>
                                                                    <button ng-click="filterOptimalRoutes = allRoutes">All Routes</button>

                                                                    <div class="panel panel-default" ng-repeat="record in records | filter:filterOptimalRoutes | orderObjectBy:orderByAttribute">
                                                                        <div class="panel-heading" role="tab" id="headingOne" >
                                                                            <h4 class="panel-title">
                                                                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href=#id-for-view-{{record.idRouteForView}} ng-click="setMap(record.edges)" aria-expanded="false" aria-controls=id-for-view-{{record.idRouteForView}}>
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
                                                                                    <p><b>Arrival:</b> {{transits.arrival.year}}-{{transits.arrival.monthValue}}-{{transits.arrival.dayOfMonth}} at {{transits.arrival.hour}}:{{transits.arrival.minute}}</p>
                                                                                    <p><b>Departure:</b> {{transits.departure.year}}-{{transits.departure.monthValue}}-{{transits.departure.dayOfMonth}} at {{transits.departure.hour}}:{{transits.departure.minute}}</p>
                                                                                </div>
                                                                                <p><b>Cost:</b> {{item.cost}} (RUB)</p>

                                                                                    <div class="row">
                                                                                        <div class="col-sm-offset-2 col-sm-8 col-sm-offset-2 centered">
                                                                                            <section>
                                                                                            <#if isAuthorized>
                                                                                                <div ng-hide="saved(record.idRouteForView)">
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