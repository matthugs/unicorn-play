define(
	[
	"coweb/main",
	"jQuery",
	"angular",
	"angular-route",
	"bootstrap",
	],
	function(coweb,$,angular,ngRoute) {
		var session = coweb.initSession();
		session.onStatusChange = function(stat) {
			console.log(stat);
		};
		var argumentations = {key: "the-onlu-session"};
		session.prepare(argumentations);

		var app = angular.module('coPlaylist', ['ngRoute']);
		var playlist = [
		{"song":"Not Enough", "singer":"Avril Lavigne"},
		{"song":"Criminal", "singer":"Britney Spear"},
		{"song":"Broke hearted", "singer":"Karmin"}
		];

		var lib=[
		{"song":"Sugar", "singer":"Maroon5"},
		{"song":"My Bloody Valentine", "singer":"Tata Young"},
		{"song":"Animal", "singer":"Maron5"},
		{"song":"One More Night", "singer":"Maroon5"}

		];

		app.config( function ( $routeProvider ) {
			$routeProvider
			.when( '/playlist', { templateUrl: 'playlist.html', controller: 'playlistCtr' })
			.when( '/library', { templateUrl: 'library.html', controller: 'playlistCtr', reloadOnSearch: false  } )
			.when( '/libBySong', { templateUrl: 'song.html', controller: 'playlistCtr', reloadOnSearch: false  } )
			.when( '/libByAlbum', { templateUrl: 'album.html', controller: 'playlistCtr', reloadOnSearch: false  } )
			.when( '/libByArtist', { templateUrl: 'artist.html' , controller: 'playlistCtr', reloadOnSearch: false } )
			.otherwise( { redirectTo: '/playlist' } );
		});

		app.controller("playlistCtr", function($scope, $http) {
			$http.get('lib.json').
			success(function(data, status, headers, config) {
				console.log("find lib json");
				$scope.lib = lib;
			}).
			error(function(data, status, headers, config) {
				console.log("loading data error, lib.json");
			});

			$http.get('playlist.json').
			success(function(data, status, headers, config) {
				console.log("find playlist json");
				$scope.playlist = playlist;
			}).
			error(function(data, status, headers, config) {
				console.log("loading data error, playlist.js");
			});


			$scope.collab = coweb.initCollab({id:"nonsense"});
			$scope.collab.subscribeSync("listChange", this, function(args){
				console.log("detect list Change!!");
				console.log(args.value);
				if (playlist !== args.value) {
					playlist = args.value;
					$scope.playlist = playlist;
					console.log("update remotely");
					console.log(playlist);
				}

			});

			$scope.addSong = function(song, singer){
				var newSong = function() {
					this.song = song;
					this.singer = singer;
				};
				playlist.push({song : song, singer : singer});
				$scope.playlist = playlist;
				console.log("add new song locally : " + song + " " + singer);
				this.collab.sendSync("listChange", $scope.playlist);

			};


			$scope.$watchCollection( 'playlist',
				function(newValue, oldValue){
					console.log('updating playlist');
					console.log(newValue);
					console.log(oldValue);
				}
				);
			


			$scope.onRemoteChange = function(args) {


				console.log("remote change detected!!!!!");
				var value = args.value;
				if (args.type === "insert") {
					console.log("remote change detected! -- insert type");
					this.onRemoteInsert(value, args.position);
				} else if (args.type === "delete") {
					console.log("remote change detected! -- delete type");
					this.onRemoteDelete(args.position);
				}
			};








		});



});