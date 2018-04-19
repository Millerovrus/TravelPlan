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
<#--<link rel="stylesheet" href="css/jquery.bootstrap-touchspin.css" />-->
<#--<script src="js/jquery.bootstrap-touchspin.js"></script>-->
    <!--angular-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>
    <script src="js/my-angular-modules.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-sanitize.min.js"></script>
    <script src="js/angular-scroll.js"></script>
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
    <link href="css/loader.css" rel="stylesheet">
    <!--<link href="css/sign-in-style.css" rel="stylesheet">-->

    <!-- my scripts -->
    <script src="js/my-styles.js"></script>

    <!--spinner + - -->
    <script src="js/jquery.dpNumberPicker.js"></script>
    <link href="css/jquery.dpNumberPicker-holoLight.css" rel="stylesheet">

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
            <form name="myForm" id="inputForm" novalidate autocomplete="off"  ng-controller="myParameterController" ng-submit="myForm.$valid && sendRequestParameters()">
                <div class="container">
                    <div class="formBox">
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="inputBox ">
                                    <div class="inputText">Departure city</div>
                                    <input type="text" name="cityFrom" ng-model="cityFrom"  id="inputFrom" class="input" onfocus="geolocate()" placeholder="" required>
                                    <div class="help-block pull-right" ng-if="myForm.$submitted">
                                        <div ng-show="showMessage(myForm.cityFrom)">Please enter departure city.</div>
                                        <div ng-show="showMessageFromHidden(myForm.inpFrom)">Please enter correct name of departure city</div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="inputText">Arrival city</div>
                                    <input type="text" id="inputTo" name="cityTo" ng-model="cityTo" class="input" onfocus="geolocate()" placeholder="" required>
                                    <div class="help-block pull-right" ng-if="myForm.$submitted">
                                        <div ng-show="showMessage(myForm.cityTo)">Please enter arrival city.</div>
                                        <div ng-show="showMessageToHidden(myForm.inpTo)">Please enter correct name of arrival city.</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="inputText">Departure date</div>
                                    <input type="text" class="input" name="dateFrom" ng-model="dateFrom" id="inputDate" ng-bind="bindingCalendar()" placeholder="" required>
                                    <div class="help-block pull-right" ng-if="myForm.$submitted">
                                        <div ng-show="showCalendarMessage(myForm.dateFrom)">Please enter departure date.</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="inputBox">
                                    <div class="input dropdown-toggle " id="test">
                                        <a href="#" class=" inputText" data-toggle="dropdown">Passengers<span class="pull-right"><i class="fa fa-angle-down"></i></span></a>
                                        <ul class="dropdown-menu mega-dropdown-menu row">
                                            <li class="col-sm-12">
                                                <div class="row">
                                                    <label for="num-picker-adults" class="text-center margin-left">
                                                        <span class="dropdown-header">Adults</span>
                                                    </label>
                                                    <div id="num-picker-adults" class="dp-numberPicker pull-right margin-set">
                                                        <div class="dp-numberPicker-sub"><i class="fa fa-minus"></i></div>
                                                        <input type="text" id="adults-number" class="dp-numberPicker-input" value="1">
                                                        <div class="dp-numberPicker-add"><i class="fa fa-plus"></i></div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <label for="num-picker-children" class="text-center margin-left">
                                                        <span class="dropdown-header">Children</span>
                                                    </label>
                                                    <div id="num-picker-children" class="dp-numberPicker pull-right margin-set">
                                                        <div class="dp-numberPicker-sub"><i class="fa fa-minus"></i></div>
                                                        <input type="text" id="children-number" class="dp-numberPicker-input" value="0">
                                                        <div class="dp-numberPicker-add"><i class="fa fa-plus"></i></div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <label for="num-picker-infants" class="text-center margin-left">
                                                        <span class="dropdown-header">Infants</span>
                                                    </label>
                                                    <div id="num-picker-infants" class="dp-numberPicker pull-right margin-set">
                                                        <div class="dp-numberPicker-sub"><i class="fa fa-minus"></i></div>
                                                        <input type="text" id="infants-number" class="dp-numberPicker-input" value="0">
                                                        <div class="dp-numberPicker-add"><i class="fa fa-plus"></i></div>
                                                    </div>
                                                </div>
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
                                    <input type="text" ng-model="inpFrom" name="inpFrom" class="input" id="inputFromHidden" required>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="inputBox ">
                                    <input type="text" class="input" ng-model="inpTo" name="inpTo" id="inputToHidden" required>
                                </div>
                            </div>
                        </div>
                        <!-- angular controller for getting routes -->
                        <div class="row" > <!--<div class="row" ng-controller="myParameterController">-->
                            <div class="col-sm-12">
                                <input type="submit" name="" class="button"  ng-keypress="checkIfEnterKeyWasPressed($event)" value="Find route">
                            </div>
                        </div>
                        <div class="row" ng-show="loading">
                            <div class="col-sm-12 loader-margin">
                                <div class="banter-loader">
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                    <div class="banter-loader__box"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="container-fluid" id="scroll-to">
                    <div ng-show="loaded" id="loaded" ng-init="optimalFilter = optimalRoutes; orderByAttribute = 'cost'">
                        <div class="routes-box">
                            <div ng-controller="mapController">
                                <div class="output-collapse" >
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <!-- filters -->
                                                <div class="accordion-panel">
                                                    <dl class="accordion">
                                                        <dt class="label-header">Routes filter <i class="plus-icon"></i></dt>
                                                        <dd>
                                                            <div class="content">
                                                                <div class="col-sm-4">
                                                                    <div class="label-header text-left">
                                                                        <h4>Show routes</h4>
                                                                    </div>
                                                                    <div class="funkyradio">
                                                                        <div class="funkyradio-default">
                                                                            <input type="radio" name="radio1" id="radio1" ng-click="optimalFilter = optimalRoutes" checked/>
                                                                            <label for="radio1" >Optimal routes</label>
                                                                        </div>
                                                                        <div class="funkyradio-default">
                                                                            <input type="radio" name="radio1" id="radio2" ng-click="optimalFilter = allRoutes"/>
                                                                            <label for="radio2">All routes</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-4">
                                                                    <div class="label-header text-left">
                                                                        <h4>Transport type</h4>
                                                                    </div>
                                                                    <div class="funkyradio">
                                                                        <div class="funkyradio-default">
                                                                            <input type="checkbox" name="checkbox" id="checkboxPlane" ng-model="checkboxPlane" ng-change="changePlaneCheckbox()"/>
                                                                            <label for="checkboxPlane">Plane</label>
                                                                        </div>
                                                                        <div class="funkyradio-default">
                                                                            <input type="checkbox" name="checkbox" id="checkboxBus" ng-model="checkboxBus" ng-change="changeBusCheckbox()"/>
                                                                            <label for="checkboxBus">Bus</label>
                                                                        </div>
                                                                        <div class="funkyradio-default">
                                                                            <input type="checkbox" name="checkbox" id="checkboxTrain" ng-model="checkboxTrain" ng-change="changeTrainCheckbox()"/>
                                                                            <label for="checkboxTrain">Train</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-4">
                                                                    <div class="laabel-header text-left">
                                                                        <h4>Order by</h4>
                                                                    </div>
                                                                    <div class="funkyradio" >
                                                                        <div class="funkyradio-default" >
                                                                            <input type="radio" name="radio3" id="radio5" ng-click="orderByAttribute = 'cost'" checked/>
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
                                                            </div>
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <div class="fancy-collapse-panel" >
                                                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
                                                        <div ng-repeat="record in records | filter:optimalFilter | filter:transportTypeFilter | orderObjectBy:orderByAttribute">
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
                                                                            <p><b>Departure:</b> {{transits.departure}}</p>
                                                                            <p><b>Arrival:</b> {{transits.arrival}}</p>
                                                                        </div>
                                                                        <p><b>Cost:</b> {{item.cost}} (RUB)</p>
                                                                        <p ng-if="item.trainTicketsInfoList != null"><b>Train tickets info:</b></p>
                                                                        <div ng-repeat="TrainTicketsInfo in item.trainTicketsInfoList">
                                                                            <p>{{TrainTicketsInfo.wagonType}} - {{TrainTicketsInfo.cost}} (RUB) - {{TrainTicketsInfo.availableSeats}} seats</p>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col-sm-offset-2 col-sm-8 col-sm-offset-2 centered">
                                                                            <#--<section>-->
                                                                                <div class="row">
                                                                                    <form >
                                                                                    <#if isAuthorized>
                                                                                        <div ng-hide="saved(record.idRouteForView)" ng-keypress="checkIfEnterKeyWasPressed($event)">
                                                                                        <#--<button class='dotted thin' ng-click="saveRoute(record)">Save route</button>-->
                                                                                            <input type="button" ng-click="saveRoute(record)" class="button" value="Save route">
                                                                                        </div>
                                                                                    </#if>
                                                                                    </form>
                                                                                </div>
                                                                                <br>
                                                                                <div class="row">
                                                                                    <form ng-click="openLink(item.purchaseLink)" ng-keypress="checkIfEnterKeyWasPressed($event)">
                                                                                    <#--<button class='dotted thin' ng-click="openLink(item.purchaseLink)">Buy ticket</button>-->
                                                                                        <input type="button" class="button" value="Buy ticket">
                                                                                    </form>
                                                                                </div>
                                                                            <#--</section>-->
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-6" id="map-margin">
                                                <div class="container-map" id="map"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>


<script src="https://cdn.jsdelivr.net/rangeslider.js/2.3.0/rangeslider.min.js"></script>
</body>
</html>