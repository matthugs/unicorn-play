//handle loading data and changing data
define(
	[
	"jQuery",
	"angular",
	"angular-route",
	"bootstrap",
  "collab/collabInterfaceService",
	],
	function($,angular,ngRoute) {

    //playlist, SHOULD NOT BE HERE FINALLY
    var playlist = [
      {"song":"Not Enough", "singer":"Avril Lavigne"},
      {"song":"Criminal", "singer":"Britney Spear"},
      {"song":"Broke hearted", "singer":"Karmin"}
    ];
    var app = angular.module("coPlaylist.playlist",
                             ["coPlaylist.collabInterface"]);

    //library, SHOULD NOT BE HERE FINALLY
    //ngRoute init
    app.config( function ( $routeProvider ) {
      $routeProvider
      .when( '/playlist', { templateUrl: 'playlist/playlist.tmp.html', controller: 'playlistCtr' });
    });

    //ngController init
    app.controller("playlistCtr", [
      "collabInterface",
      "$scope",
      "$http",
      function(collab, $scope, $http) {


        //loading playlist
        //	$http.get('playlist.json').
        //	success(function(data, status, headers, config) {
        console.log("find playlist");
        $scope.playlist = playlist;
        //	}).
        //	error(function(data, status, headers, config) {
        //		console.log("loading data error, playlist.js");
        //	});


        collab.subscribeSync("listChange", this, function(args){
          console.log("detect list Change!!");
          console.log(args.value);
          if (playlist !== args.value) {
            playlist = args.value;
            $scope.playlist = playlist;
            $scope.$apply();
            console.log("update remotely");
            console.log($scope.playlist);
          }

        });

        $scope.$watchCollection( function() {return playlist},
                                function(newValue, oldValue){
                                  if (typeof newValue !== 'undefined') {
                                    $scope.playlist = playlist;
                                    console.log('updating playlist');
                                    console.log($scope.playlist);
                                  }
                                }
                               );


      }]);

  });
