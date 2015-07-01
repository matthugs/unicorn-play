//handle loading data and changing data
define(
	[
	"coweb/main",
	"jQuery",
	"angular",
	"angular-route",
	"bootstrap",
  "collab/collabInterfaceService",
  "library/library.js"
	],
	function(coweb,$,angular,ngRoute) {

		//coweb init
//		var session = coweb.initSession();
//		session.onStatusChange = function(stat) {
//			console.log(stat);
//		};
//		var argumentations = {key: "the-onlu-session"};
//		session.prepare(argumentations);

		//angular module init
    var app = angular.module('coPlaylist',
                             ['ngRoute',
                               "coPlaylist.collabInterface",
                               "coPlaylist.library"
    ]);

    //playlist, SHOULD NOT BE HERE FINALLY
    var playlist = [
      {"song":"Not Enough", "singer":"Avril Lavigne"},
      {"song":"Criminal", "singer":"Britney Spear"},
      {"song":"Broke hearted", "singer":"Karmin"}
    ];

    //library, SHOULD NOT BE HERE FINALLY
    //ngRoute init
    app.config( function ( $routeProvider ) {
      $routeProvider
      .when( '/playlist', { templateUrl: 'playlist.html', controller: 'playlistCtr' })
      //.when( '/library', { templateUrl: 'library.html', controller: 'libraryCtr' } )
      //.when( '/libBySong', { templateUrl: 'song.html', controller: 'libraryCtr' } )
      //.when( '/libByAlbum', { templateUrl: 'album.html', controller: 'libraryCtr' } )
      //.when( '/libByArtist', { templateUrl: 'artist.html' , controller: 'libraryCtr' } )
      .otherwise( { redirectTo: '/playlist' } );
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
