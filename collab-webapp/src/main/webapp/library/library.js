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
			.when( '/library', { templateUrl: 'library.html', controller: 'libraryCtr' } )
			.when( '/libBySong', { templateUrl: 'song.html', controller: 'libraryCtr' } )
			.when( '/libByAlbum', { templateUrl: 'album.html', controller: 'libraryCtr' } )
			.when( '/libByArtist', { templateUrl: 'artist.html' , controller: 'libraryCtr' } )
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
          {"song":"One More Night", "singer":"Maroon5"}
        ];


        //loading library
        //$http.get('lib.json').
        //success(function(data, status, headers, config) {
        console.log("find libray");
        $scope.lib = lib;
        //	}).
        //	error(function(data, status, headers, config) {
        //		console.log("loading data error, lib.json");
        //	});

        $scope.searchArtistText = "search artist";
        $scope.searchArtist = function() {
          console.log("about to search artist " + $scope.searchArtistText);
          collab.postService("query",{query: $scope.searchArtistText},
                             function(args) {
                               console.log(args.value.list);
                               $scope.lib = args.value.list;
                             });

        }


        $scope.$watchCollection( function() {return $scope.lib},
                                function(newValue, oldValue){
                                  if (typeof newValue !== 'undefined') {
                                    $scope.lib = newValue;
                                    console.log('updating lib list');
                                  }
                                }
                               );
                               $scope.addSong = function(song, singer){
                                 //	var newSong = function() {
                                 //		this.song = song;
                                 //		this.singer = singer;
                                 //	};
                                 playlist.push({song : song, singer : singer});
                                 //placeholder for send notification to Bot
                                 //about the changes
                                 $scope.playlist = playlist;
                                 console.log("add new song locally : " + song +
                                             " " + singer);
                                 collab.sendSync("listChange", $scope.playlist);
                               };
      }]);
  }
);
