<#include "/spring.ftl" />

<!DOCTYPE html>
<html ng-app="rails-time-map-example" lang="en">

<head>
  <meta charset="utf-8">
  <title>Rails Time</title>

  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

	<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
	<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
	<!--script src="js/less-1.3.3.min.js"></script-->
	<!--append ‘#!watch’ to the browser URL, then refresh the page. -->
	
	<link href="static/css/bootstrap.min.css" rel="stylesheet">
	<link href="static/css/style.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="static/img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="static/img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="static/img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="static/img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="static/img/favicon.png">
  
	<script type="text/javascript" src="static/js/jquery.min.js"></script>
	<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/js/scripts.js"></script>

</head>

<body ng-controller="RailsTimeController">
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
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
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">

            <select id="dropdown" ng-model="orderProp" >
                <option ng-repeat="cats in categories" value="{{cats}}">{{cats}}</option>
            </select>

            <button type="button" class="btn btn-default" ng-click="showAll($event)" >Show all results</button>

			<table class="table">
				<thead>
					<tr>
						<th>

						</th>
						<th>
							#
						</th>
						<th>
						    Train Name
						</th>
						<th>
                            Train Origin
						</th>
						<th>
                            Train Destination
						</th>
						<th>
                            Train JSON LINK
						</th>
					</tr>
				</thead>

				<tbody>
					<tr ng-repeat="train in trains | filter:orderProp" ng-init="liVisible" ng-hide="liVisible == false">

						<td class="name" ng-click="select(train)">
                            <span class="glyphicon glyphicon-search"></span>
						</td>
                        <td>
                            {{train.trainId}}
						</td>
						<td>
                            {{train.trainName}}
						</td>
						<td>
                            {{train.trainOrigin.stationName}}
						</td>
						<td>
                            {{train.trainDestination.stationName}}
						</td>
						<td>
                            <a href="/service/train/{{train.trainId}}" target="_blank">{{train.trainId}}</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script src="//maps.googleapis.com/maps/api/js?sensor=false&language=en"></script>
<script src="static/js/angular.min.js"></script>
<script src="static/js/google-maps.js"></script>
<script src="static/js/map.js"></script>

</body>
</html>
