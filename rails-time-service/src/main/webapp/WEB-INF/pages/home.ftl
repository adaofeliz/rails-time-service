<#include "/spring.ftl" />

<!DOCTYPE html>

<html ng-app="rails-time-map-example">

<head>
    <meta charset="UTF-8">
    <title>Rails Time</title>
    <link rel="stylesheet" href="static/css/app.css">
    <link rel="stylesheet" href="static/css/bootstrap.css">

    <style>
        .name{
            color:rgb(106,182,200);
            font-weight:bold;
            cursor:pointer;
        }
        .trains li{
            list-style:none;
            display: inline;
        }

        #showHide {
            display: inline;
        }

    </style>
</head>
<body ng-controller="ExampleController">
<div class="trains" >


    <div class="google-map"
         center="centerProperty"
         zoom="zoomProperty"
         markers="filteredMarkersProperty"
         latitude="clickedLatitudeProperty"
         longitude="clickedLongitudeProperty"
         mark-click="false"
         draggable="true"
         style="height: 500px; width: 100%; float:right">
    </div>

    <div>
        <select id="dropdown" ng-model="orderProp" >
            <option ng-repeat="cats in categories" value="{{cats}}">{{cats}}</option>
        </select>

        <button id="showHide" ng-click="showAll($event)" >Show all results</button>

        <ul>
            <li ng-repeat="train in trains | filter:orderProp" ng-init="liVisible" ng-hide="liVisible == false">
                <span class="name" ng-click="select(train)">{{train.trainName}}: {{train.trainId}}; </span>
            </li>
        </ul>
    </div>
</div>

<script src="//maps.googleapis.com/maps/api/js?sensor=false&language=en"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.5/angular.js"></script>
<script src="static/lib/google-maps.js"></script>
<script src="static/js/map.js"></script>

</body>
</html>