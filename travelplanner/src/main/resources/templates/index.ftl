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
    <!--<script src="//code.angularjs.org/1.2.2/angular-sanitize.js"></script>-->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.min.js"></script>

    <!-- bootstrap select -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-select.min.js"></script>

    <!-- bootstrap datetimepicker -->
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
    <script src="js/moment-with-locales.min.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>

    <!-- my css -->
    <link href="css/my-styles.css" rel="stylesheet">
    <link href="css/sign-in-style.css" rel="stylesheet">

    <!-- my scripts -->
    <script src="js/my-styles.js"></script>

    <!-- fix to -->
    <script src="js/jquery-scrolltofixed.js"></script>

    <!-- map -->

    <!--<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBpOm2tBurzyefOG_hBFEXQIkLbkZpSvws&callback=initMap"-->
    <!--async defer></script>-->
    <script src="js/google-map.js"></script>
    <script src="js/autoComplite.js"></script>
    <!--<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBpOm2tBurzyefOG_hBFEXQIkLbkZpSvws&language=en&libraries=places"></script>-->

    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBpOm2tBurzyefOG_hBFEXQIkLbkZpSvws&callback=initMap&language=en&libraries=geometry,places"></script>
</head>

<body ng-controller="appController" onload="initAutocompleteFields()">
<div>

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
                <div class="navbar-collapse collapse"  id="navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/users"><i class="fa fa-user" aria-hidden="true"></i> ${firstname} ${lastname} </a></li>
                        <li><a href="/logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </#if>

    <!-- <div ng-controller="myParameterController"> -->
    <div  class="scrollbar" id="container1">
        <img src="img/travel-top.jpg" class="background-main-pic"/>
        <div class="container">
            <div class="row">
                <div id="travel-header-row" class="col-sm-12">
                    <h1>Start your journey right now</h1>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="container scrollbar">
                <div class="formBox">
                    <form id="inputForm" autocomplete="off">

                        <div class="row">
                            <div class="col-sm-6">
                                <div class="inputBox ">
                                    <input type="text" name="" id="inputFrom" required class="input" placeholder="From" onfocus="geolocate()">
                                </div>
                            </div>

                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <input type="text" name="" id="inputTo" required class="input" placeholder="To" onfocus="geolocate()">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="input">
                                        <div class="dropdown show">
                                            <!--<div class="dropdown">-->
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Passengers<span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
                                            <ul class="dropdown-menu" id="passengers-qunatity">
                                                <div class="drop-passengers">
                                                    <li>
                                                        <a href="#" ><label for="spin-adult">Adults</label><input id="spin-adult" type="text" value="1" name="demo_vertical"></a>
                                                    </li>
                                                    <!--<li>-->
                                                    <!--<a href="#"><label for="spin-senior">Seniors</label><input id="spin-senior" type="text" value="" name="demo_vertical"></a>-->
                                                    <!--</li>-->
                                                    <li>
                                                        <a href="#"><label for="spin-children">Children</label><input id="spin-children" type="text" value="0" name="demo_vertical"></a>
                                                    </li>
                                                    <li class="divider"></li>
                                                    <li class="form-check">
                                                        <label class="checkbox">
                                                            <input type="checkbox"><span>Business class</span>
                                                        </label>
                                                    </li>
                                                </div>
                                            </ul>
                                            <!--</div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="input">
                                        <div class="form-group">
                                            <div class="input-group date" id='datetimepicker1'>
                                                <input type="text" id="inputDate" class="date-input-style" placeholder="Departure date" />
                                                <span class="input-group-addon datepickerbutton">
                                                                <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

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

                        <div class="row" ng-controller="myParameterController">
                            <div class="col-sm-12">
                                <input type="button" name="" ng-click="sendRequestParameters()" required class="button" value="Find route" onclick="this.parentNode.submit();">
                                <div ng-show="loading" class="preloader"></div>
                                <div ng-show="loaded">

                                    <!-- test collapse -->
                                    <div ng-controller="mapController">
                                        <div class="output-collapse">
                                            <div class="container-fluid">
                                                <form>
                                                    <div class="row">
                                                        <div class="col-sm-5">
                                                            <div class="fancy-collapse-panel" >
                                                                <!-- костыль -->
                                                                <div id="json-for-map" ng-show="false">{{records}}</div>
                                                                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
                                                                    <div class="panel panel-default"  ng-repeat="record in records">
                                                                            <#--<label class="checkbox" id="checkbox-{{record.idRouteForView}}">-->
                                                                                <#--<input type="checkbox"><span> Save</span>-->
                                                                            <#--</label>-->
                                                                            <div class="panel-heading" role="tab" id="headingOne" >
                                                                                <h4 class="panel-title">
                                                                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href=#id-for-view-{{record.idRouteForView}} ng-click="setMap(record.idRouteForView)" aria-expanded="false" aria-controls=id-for-view-{{record.idRouteForView}}>
                                                                                        <div class="route-header" ng-repeat="item in record.edges"> {{item.startPoint}}    <span ng-bind-html=' item.transportType | transportTypeToIcon'></span>    {{item.destinationPoint}} </div>
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
                                                                                <ul>
                                                                                    <p><b>Transit route {{item.edgeOrder}}</b>: {{item.startPoint}} - {{item.destinationPoint}}</p>
                                                                                    <p><b>Cost:</b> {{item.cost}} (RUB)</p>
                                                                                    <p><b>Departure <date></date>:</b> {{item.startDate.year}}-{{item.startDate.monthValue}}-{{item.startDate.dayOfMonth}} at {{item.startDate.hour}}:{{item.startDate.minute}}</p>
                                                                                </ul>
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
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <#--<input type="button" name="" required class="button" value="Save all routes" onclick="this.parentNode.submit();">-->
                                </div>
                            </div>
                        </div>



                    </form>

                    <!-- костыли -->
                    <form>
                        <div class="row">
                            <div class="container" id="test-container"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- </div> -->

</div>

</body>
</html>
