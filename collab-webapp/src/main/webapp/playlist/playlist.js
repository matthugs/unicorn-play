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

    var app = angular.module("coPlaylist.playlist",
                             ["coPlaylist.collabInterface"]);

    //library, SHOULD NOT BE HERE FINALLY
    //ngRoute init
    app.config( function ( $routeProvider ) {
      $routeProvider
      .when( '/playlist', { templateUrl: 'playlist/playlist.tmp.html', controller: 'playlistCtr' });
    });


    app.service('playlistService', [
      "collabInterface",
      function(collab){


       var playlist = [];

    this.getPlaylist = function() {
      return playlist;
    };

    this.updatePlaylist = function (newPlaylist) {
      playlist = newPlaylist;
      console.log(playlist);
    }


    this.add = function(song, singer) {

      playlist.push({song:song, singer:singer});
      collab.sendSync("listChange", playlist);
      console.log("send sysn" + playlist);
      
    }
    }]);

    //ngController init
    app.controller("playlistCtr", [
      "collabInterface",
      "$scope",
      "$http",
      "playlistService",
      function(collab, $scope, $http, playlistService) {


        //loading playlist
        //	$http.get('playlist.json').
        //	success(function(data, status, headers, config) {
        console.log("find playlist");
        $scope.playlist = playlistService.getPlaylist();
        //	}).
        //	error(function(data, status, headers, config) {
        //		console.log("loading data error, playlist.js");
        //	});


        collab.subscribeSync("listChange", this, function(args){
          console.log("detect list Change!!");
          console.log(args.value);
          if (args.value != null) {
            playlistService.updatePlaylist(args.value);
            $scope.playlist = args.value;
            console.log("update playlist playlist page");
            $scope.$apply();
          };
          
        });

        $scope.$watchCollection( "playlist",
                                function(newValue, oldValue){
                                  console.log("locally detect change");
                                  if (typeof newValue !== 'undefined') {
                                    $scope.playlist = newValue;
                                    console.log('updating playlist');
                                    console.log(playlistService.getPlaylist());

                                  }
                                }
                               );


      }]);

  });
