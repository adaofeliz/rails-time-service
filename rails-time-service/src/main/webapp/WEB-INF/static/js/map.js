   
(function () {
    var module = angular.module("rails-time-map-example", ["google-maps"]);
}());

function ExampleController ($scope, $http, $filter) {
  $http.get('service/train').success(function(data) {
    $scope.trains = data;
    $scope.markersProperty = data;
    $scope.filteredMarkersProperty = $scope.markersProperty;

    var cats = [];
        for (var i = 0; i < data.length; i++){
            cats[i] = data[i].trainType;
        }
    
    var sorted_cats = cats.sort();
    
    $scope.categories = [];
        for (var i = 0; i < cats.length; i++){
            if (sorted_cats[i+1] != sorted_cats[i]){
                $scope.categories.push(sorted_cats[i]);
            }
        }

  }); //End Data Grab

 //Start Extra Functions for Controller 
    $scope.$watch( 'orderProp', function ( val ) {
        $scope.filteredMarkersProperty = $filter('filter')($scope.markersProperty, val);
        $scope.zoomProperty = 8;
        calcFocus();
    });

    $scope.showAll = function($event){
        $scope.orderProp ="0";
        $scope.filteredMarkersProperty = $scope.trains;
        $scope.zoomProperty = 8;
        calcFocus();

    }

     $scope.select = function($event){
        var theName = $event.trainId;
        var lat = $event.trainPosition.currentLatitude;
        var lng = $event.trainPosition.currentLongitude;
        $scope.filteredMarkersProperty = [$event];
        $scope.centerProperty.lat = lat;
        $scope.centerProperty.lng = lng;
        $scope.zoomProperty = 11;
        calcFocus();
     }

    function calcFocus(){
        var lats = [], longs = [], counter = [];

        for(i=0; i<$scope.filteredMarkersProperty.length; i++)
        {
            lats[i] = $scope.filteredMarkersProperty[i].trainPosition.currentLatitude;
            longs[i] = $scope.filteredMarkersProperty[i].trainPosition.currentLongitude;
        }

        var latCount = 0;
        var longCount = 0;

        for (i=0; i<lats.length; i++){
            latCount += lats[i];
            longCount += longs[i];
        }

        latCount = latCount / lats.length;
        longCount = longCount / longs.length;
        $scope.centerProperty.lat = latCount;
        $scope.centerProperty.lng = longCount;
    };


    angular.extend($scope, {

        /** the initial center of the map */
        centerProperty: {
            lat:40.715041946365865,
            lng:-8.636390865998628
        },

        /** the initial zoom level of the map */
        zoomProperty: 8,

        /** list of markers to put in the map */

        markersProperty : [],

        // These 2 properties will be set when clicking on the map - click event
        clickedLatitudeProperty: null,  
        clickedLongitudeProperty: null
    });
    

}


