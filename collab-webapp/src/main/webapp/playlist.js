//handle loading data and changing data
define(
	[
	"coweb/main",
	"jQuery",
	"angular",
	"angular-route",
	"bootstrap",
	],
	function(coweb,$,angular,ngRoute) {

		//coweb init
		var session = coweb.initSession();
		session.onStatusChange = function(stat) {
			console.log(stat);
		};
		var argumentations = {key: "the-onlu-session"};
		session.prepare(argumentations);

		//angular module init
		var app = angular.module('coPlaylist', ['ngRoute']);

		//playlist, SHOULD NOT BE HERE FINALLY
		var playlist = [
		{"song":"Not Enough", "singer":"Avril Lavigne"},
		{"song":"Criminal", "singer":"Britney Spear"},
		{"song":"Broke hearted", "singer":"Karmin"}
		];

		//library, SHOULD NOT BE HERE FINALLY
		var lib=[
		{"song":"Sugar", "singer":"Maroon5"},
		{"song":"My Bloody Valentine", "singer":"Tata Young"},
		{"song":"Animal", "singer":"Maron5"},
		{"song":"One More Night", "singer":"Maroon5"}
		];

		//ngRoute init
		app.config( function ( $routeProvider ) {
			$routeProvider
			.when( '/playlist', { templateUrl: 'playlist.html', controller: 'playlistCtr' })
			.when( '/library', { templateUrl: 'library.html', controller: 'libraryCtr' } )
			.when( '/libBySong', { templateUrl: 'song.html', controller: 'libraryCtr' } )
			.when( '/libByAlbum', { templateUrl: 'album.html', controller: 'libraryCtr' } )
			.when( '/libByArtist', { templateUrl: 'artist.html' , controller: 'libraryCtr' } )
			.otherwise( { redirectTo: '/playlist' } );
		});

		//ngController init
		app.controller("playlistCtr", function($scope, $http) {

			//loading library
			//$http.get('lib.json').
			//success(function(data, status, headers, config) {
				console.log("find libray");
				$scope.lib = lib;
		//	}).
		//	error(function(data, status, headers, config) {
		//		console.log("loading data error, lib.json");
		//	});


			//loading playlist
		//	$http.get('playlist.json').
		//	success(function(data, status, headers, config) {
			console.log("find playlist");
			$scope.playlist = playlist;
		//	}).
		//	error(function(data, status, headers, config) {
		//		console.log("loading data error, playlist.js");
		//	});


			$scope.collab = coweb.initCollab({id:"nonsense"});
			$scope.collab.subscribeSync("listChange", this, function(args){
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


		});

		app.controller("libraryCtr", function($scope, $http) {
		$scope.searchArtistText = "search artist";
		$scope.searchArtist = function() {
			console.log("about to search artist " + $scope.searchArtistText);
				//this.collab.postService("player",{query: $scope.searchArtistText}, function() {console.log("Success")});
			}

			$scope.collab = coweb.initCollab({id:"nonsense"});

			$scope.addSong = function(song, singer){
			//	var newSong = function() {
			//		this.song = song;
			//		this.singer = singer;
			//	};
			playlist.push({song : song, singer : singer});
			//placeholder for send notification to Bot about the changes
			$scope.playlist = playlist;
			console.log("add new song locally : " + song + " " + singer);
			this.collab.sendSync("listChange", $scope.playlist);
		};

		

	});
});