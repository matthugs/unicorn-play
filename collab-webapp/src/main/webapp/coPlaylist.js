define(
  [ "angular",
    "library/library",
    "playlist/playlist"
  ],
  function(angular) {
    var app = angular.module('coPlaylist',
                             ['ngRoute',
                               "coPlaylist.collabInterface",
                               "coPlaylist.playlist",
                               "coPlaylist.library"
    ]);
    app.config( function ( $routeProvider ) {
      $routeProvider
      .otherwise( { redirectTo: '/playlist' } );
    });
  });


