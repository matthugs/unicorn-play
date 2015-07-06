define(
  [
  "angular",
  "angular-route",
  "collab/collabInterfaceService"
  ],
  function(angular, ngRoute) {
    var app = angular.module("coPlaylist.library",
     ["coPlaylist.collabInterface"]);
    app.config(["$routeProvider",
     function($routeProvider) {

       $routeProvider
       .when( '/library', { templateUrl: 'library/library.tmp.html', controller: 'libraryCtr' } )
       .when( '/libBySong', { templateUrl: 'library/song.tmp.html', controller: 'libraryCtr' } )
       .when( '/libByAlbum', { templateUrl: 'library/album.tmp.html', controller: 'libraryCtr' } )
       .when( '/libByArtist', { templateUrl: 'library/artist.tmp.html' , controller: 'libraryCtr' } )
     }]);
    app.controller("libraryCtr", [
      "collabInterface",
      "$scope",
      "$http",
      function(collab, $scope, $http) {

        var lib=[
        {"song":"Sugar", "singer":"Maroon5"},
        {"song":"My Bloody Valentine", "singer":"Tata Young"},
        {"song":"Animal", "singer":"Maron5"},
        {"song":"One More Night11", "singer":"Maroon5"}
        ];


        $scope.lib = lib;
        collab.postService("query",{query: $scope.searchArtistText},
         function(args) {
          console.log("get lib from service");
           console.log(args.value.list);
           $scope.lib = args.value.list;
         });

        $scope.searchArtistText = "search artist";
        $scope.searchArtist = function() {
          console.log("about to search artist " + $scope.searchArtistText);
          $scope.lib = [];
          collab.postService("query",{query: $scope.searchArtistText},
           function(args) {
             console.log(args.value.list);
             $scope.lib = args.value.list;
           });

        }


        $scope.$watchCollection( function() {return $scope.lib},
          function(newValue, oldValue){
            console.log("detect change in scope lib");
            if (typeof newValue !== 'undefined') {
              $scope.lib = newValue;
              console.log('updating lib list');
            }
          }
          );
        $scope.addSong = function(song, singer){

          $scope.playlist.push({song : song, singer : singer});

         $scope.playlist = playlist;
         console.log("add new song locally : " + song +
           " " + singer);
         collab.sendSync("listChange", $scope.playlist);
       };
     }]);
}
);
