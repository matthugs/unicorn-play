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
     function($routeProvider) {

       $routeProvider
       .when( '/library', { templateUrl: 'library/library.tmp.html'} )
       .when( '/libBySong', { templateUrl: 'library/song.tmp.html', controller: 'songCtr' } )
       .when( '/libByAlbum', { templateUrl: 'library/album.tmp.html', controller: 'albumCtr' } )
       .when( '/libByArtist', { templateUrl: 'library/artist.tmp.html' , controller: 'artistCtr' } )
     }]);
    app.service("queryService", function(){
      this.searchAlbumsByArtist = "";
      this.updatesearchAlbumsByArtist = function(artist) {
        this.searchAlbumsByArtist = artist;
      };

    });

    app.controller("songCtr", [
      "collabInterface",
      "playlistService",
      "$scope",
      "$http",
      function(collab, playlistService, $scope, $http) {

        collab.postService("query",{query: {"listing-type":"Track"}},
         function(args) {
          console.log("get lib from service");
          console.log(args);
           $scope.lib = args.value.list;
           $scope.$apply();
         });

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

        $scope.addSong = function(song, singer){
         playlistService.add(song, singer);
         console.log("add new song locally from lib page: " + song +
           " " + singer);

       };

     }]);

    app.controller("albumCtr", [
      "collabInterface",
      "$scope",
      "$http",
      "queryService",
      function(collab, $scope, $http,queryService) {

        collab.postService("query",{query: {"listing-type": "Album"}},
         function(args) {
          console.log("get lib from service");
          console.log(args);
           $scope.lib = args.value.list;
           $scope.$apply();
         });

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


    app.controller("artistCtr", [
      "collabInterface",
      "$scope",
      "$http",
      "queryService",
      function(collab, $scope, $http,queryService) {

        collab.postService("query",{query: {"listing-type": "Artist"}},
         function(args) {
          console.log("get lib from service");
          console.log(args);
           $scope.lib = args.value.list;
           $scope.$apply();
         });

        // $scope.searchArtistText = "search artist";
        // $scope.searchArtist = function() {
        //   console.log("about to search artist " + $scope.searchArtistText);
        //  // $scope.lib = [];
        //   collab.postService("query",{query: $scope.searchArtistText},
        //    function(args) {
        //     console.log("get artist list from server");
        //      console.log(args.value.list);
        //      $scope.lib = args.value.list;
        //    });

        //}


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

        $scope.goToAlbums = function(singer) {
         queryService.updatesearchAlbumsByArtist(singer);
         console.log("get albums for"+ singer);
       };
     }]);
}
);
