/**
*
*  library controller
*
*/

define(
  [
  "angular",
  "angular-route",
  "collab/collabInterfaceService",
  "playlist/playlist"
  ],
  function(angular, ngRoute) {
    var app = angular.module("coPlaylist.library",
     ["coPlaylist.collabInterface","coPlaylist.playlist"]);
    app.config(["$routeProvider",

      //init ngRoute
      function($routeProvider) {
       $routeProvider
       .when( '/library', { templateUrl: 'library/library.tmp.html'} )
       .when( '/libBySong', { templateUrl: 'library/song.tmp.html', controller: 'songCtr' } )
       .when( '/libByAlbum', { templateUrl: 'library/album.tmp.html', controller: 'albumCtr' } )
       .when( '/libByArtist', { templateUrl: 'library/artist.tmp.html' , controller: 'artistCtr' } )
     }]);

    //queryService to record user's searching
    app.service("queryService", function(){
      this.searchAlbumsByArtist = "";
      this.updatesearchAlbumsByArtist = function(artist) {
        this.searchAlbumsByArtist = artist;
      };
    });

    //song Controller, query for songs 
    app.controller("songCtr", [
      "collabInterface",
      "playlistService",
      "$scope",
      "$http",
      function(collab, playlistService, $scope, $http) {

        //query songs from backend
        collab.postService("query",{query: {"listing-type":"Track"}},
         function(args) {
          console.log("get lib from service");
          console.log(args);
          $scope.lib = args.value.list;
          $scope.$apply();
        });

        //watch song list and update UI correspondingly
        $scope.$watchCollection( "lib",
          function(newValue, oldValue){
            console.log("detect change in scope lib");
            if (typeof newValue !== 'undefined') {
              $scope.lib = newValue;
              console.log('updating lib list');
              console.log(newValue);
            }
          }
          );

        //function of add a song to playlist
        $scope.addSong = function(song){
         playlistService.add(song);
         console.log("add new song locally from lib page: " + song);
       };

     }]);

    //album Controller, query for album
    app.controller("albumCtr", [
      "collabInterface",
      "$scope",
      "$http",
      "queryService",
      function(collab, $scope, $http,queryService) {

        //query for album from backend
        collab.postService("query",{query: {"listing-type": "Album"}},
         function(args) {
          console.log("get lib from service");
          console.log(args);
          $scope.lib = args.value.list;
          $scope.$apply();
        });

        //watch song list and update UI correspondingly
        $scope.$watchCollection( "lib",
          function(newValue, oldValue){
            console.log("detect change in scope lib");
            if (typeof newValue !== 'undefined') {
              $scope.lib = newValue;
              console.log('updating lib list');
              console.log(newValue);
            }
          }
          );
      }]);


    //artist Controller, query for artist
    app.controller("artistCtr", [
      "collabInterface",
      "$scope",
      "$http",
      "queryService",
      function(collab, $scope, $http,queryService) {

        //query for artist from backend
        collab.postService("query",{query: {"listing-type": "Artist"}},
         function(args) {
          console.log("get lib from service");
          console.log(args);
          $scope.lib = args.value.list;
          $scope.$apply();
        });

        //watch song list and update UI correspondingly
        $scope.$watchCollection( "lib",
          function(newValue, oldValue){
            console.log("detect change in scope lib");
            if (typeof newValue !== 'undefined') {
              $scope.lib = newValue;
              console.log('updating lib list');
              console.log(newValue);
            }
          }
          );

        //function of go to album list of an artist user chosen
        $scope.goToAlbums = function(singer) {
         queryService.updatesearchAlbumsByArtist(singer);
         console.log("get albums for"+ singer);
       };
     }]);
  }
  );
