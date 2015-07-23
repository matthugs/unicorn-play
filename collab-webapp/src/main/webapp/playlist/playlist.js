//handle loading data and changing data
define(
	[
	"jQuery",
	"angular",
	"angular-route",
  "coweb/main",
	"bootstrap",
  "collab/collabInterfaceService"],
	function($,angular,ngRoute, coweb) {

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
      "$rootScope",
      function(collab, $root){


       var playlist = [];

       this.onSetFullState = function(state) {
         playlist = state.list;
         console.log("state received from server");
         console.log(state);
         $root.$apply();
       }


       var session = coweb.initSession();
       session.onStatusChange = function(stat) {
         console.log(stat);
       };
       var argumentations = {key: "the-onlu-session"};

       collab.subscribeStateResponse(this, "onSetFullState");

       session.prepare(argumentations);



       this.getPlaylist = function() {
         return playlist;
       };

       this.updatePlaylistAdd = function (song) {
         playlist.push(song);
         console.log(playlist);
       }

       collab.subscribeSync("listChange", this, function(args){
         console.log("detect remote add song!");
         console.log(args.value);
         if (args.value != null) {
           this.updatePlaylistAdd(args.value);
           console.log("update playlist playlist page");
           $root.$apply();
         };
       });

       this.add = function(song) {

         playlist.push(song);
         collab.sendSync("listChange", song, 'insert', playlist.length - 1);
         console.log("send sync");
         console.log(song);
         console.log("added at " + (playlist.length - 1));
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
