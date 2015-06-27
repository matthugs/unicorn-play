//
// Cooperative web application template.
//
// Copyright (c) The Dojo Foundation 2011. All Rights Reserved.
//

// assumes admin servlet deployed with the application 
var cowebConfig = {
    adminUrl : './admin'
};


/*require({moduleUrl : 'lib'},
        ['coweb/main', "collab-webapp/playlist"],
function(coweb) {
    // do application setup here
    
  //  dojo.ready(function() {
        // on page load, prepare, join, and update in a session
  //  });
});*/
requirejs.config({
	
	paths: {
        coweb : 'lib/coweb',
        cowebx: 'lib/cowebx',
        org : 'lib/org',
        jQuery: 'http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min',
        angular: 'http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min',
        'angular-route':'http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-route.min',
        bootstrap: 'http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/js/bootstrap.min'
    },
    shim: {
        'jQuery': { 'exports': '$'},
        'angular': {'exports': 'angular'},
        'angular-route': {'exports': 'angular-route'},
        'bootstrap': {deps: ['jQuery']},
    },
    priority: [
    "angular"
    ],
});
require([
    'coweb/main',
    'jQuery',
    'angular',
    'playlist',
    ], function(coweb,$,angular,app) {
        var $html = angular.element(document.getElementsByTagName('html')[0]);
        angular.element().ready(function() {
            // bootstrap the app
            angular.bootstrap(document, ['coPlaylist']);
        });
    });


