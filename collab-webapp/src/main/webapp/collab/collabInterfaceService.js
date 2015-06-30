define([
    "angular",
    "coweb/main"],
    function(ng, coweb) {
      var app = ng.module("coPlaylist.collabInterface", []);
      app.factory("collabInterface", function() {

        var session = coweb.initSession();
        session.onStatusChange = function(stat) {
          console.log(stat);
        };
        var argumentations = {key: "the-onlu-session"};
        session.prepare(argumentations);
        return coweb.initCollab({id:"nonsense"});
      });
      //app.service(
    });
