/**
*
*  Playlist controller
*/
define(
	[
	"jQuery",
	"angular",
	"angular-route",
  "coweb/main",
	"bootstrap",
  "collab/collabInterfaceService"
  ],
  function($,angular,ngRoute, coweb) {
    //playlist module
    var app = angular.module("coPlaylist.playlist",
     ["coPlaylist.collabInterface"]);


    // ngRoute init
    app.config( function ( $routeProvider ) {
      $routeProvider
      .when( '/playlist', { templateUrl: 'playlist/playlist.tmp.html', controller: 'playlistCtr' });
    });

    //playlist service handle changes in playlist
    app.service('playlistService', [
      "collabInterface",
      "$rootScope",
      function(collab, $root){

       var playlist = [];

       this.onSetFullState = function(state) {
          $root.$apply(function() {
            playlist = state.list;
            console.log("state received from server");
            console.log(state);
          });
       };


       var session = coweb.initSession();  //init collab session
       session.onStatusChange = function(stat) {
         console.log(stat); // log the status of collab session
       };
       var argumentations = {key: "the-onlu-session"}; //ensures there is only one session

       collab.subscribeStateResponse(this, "onSetFullState");

       session.prepare(argumentations);



       this.getPlaylist = function() {
        return playlist;
      };

      this.updatePlaylistAdd = function (song) {
        playlist.push(song);
        console.log(playlist);
      };

      //subscrib to remote changes
      collab.subscribeSync("listChange", this, function(args){
        console.log("detect remote add song!");
        console.log(args.value);
        if (args.value != null) {
          $root.$apply(function() {
            this.updatePlaylistAdd(args.value);
            console.log("update playlist playlist page");
          });
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
      "playlistService",
      function(collab, $scope, playlistService) {
        console.log("find playlist");
        $scope.playlist = playlistService.getPlaylist();

        //watch change of playlist
        $scope.$watchCollection( playlistService.getPlaylist,
          function(newValue, oldValue){
            console.log("locally detect change");
            if (typeof newValue !== 'undefined') {
              $scope.playlist = newValue;
              console.log('updating playlist');
              console.log(playlistService.getPlaylist());
            }
          });


      }]);

  });
