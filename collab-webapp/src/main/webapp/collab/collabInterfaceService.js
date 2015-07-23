define([
    "angular",
    "coweb/main"],
    function(ng, coweb) {
      var app = ng.module("coPlaylist.collabInterface", []);
      app.factory("collabInterface", function() {

        return coweb.initCollab({id:"nonsense"});
      });
      //app.service(
    });
