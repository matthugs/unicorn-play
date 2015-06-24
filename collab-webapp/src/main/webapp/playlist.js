var app = angular.module("coPlaylist", []);
app.controller("playlistCtr", function($scope, $http) {
    $http.get('playlist.json').
    success(function(data, status, headers, config) {
    	console.log("find playlist json");
      $scope.playlist = data.playlist;
    }).
    error(function(data, status, headers, config) {
      console.log("loading data error, playlist.js");
    });
});