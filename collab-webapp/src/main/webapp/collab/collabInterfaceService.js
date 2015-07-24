/**
*
* Service of collab Interface
* Init when start the CoPlaylist webapp
*
*/
define([
  "angular",
  "coweb/main"],
  function(ng, coweb) {
    var app = ng.module("coPlaylist.collabInterface", []);
    app.factory("collabInterface", function() {
        return coweb.initCollab({id:"nonsense"});
      });
  });
