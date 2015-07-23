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

        var session = coweb.initSession();  //init collab session

        session.onStatusChange = function(stat) {
          console.log(stat); // log the status of collab session
        };

        var argumentations = {key: "the-onlu-session"}; //make sure there is only one session
        session.prepare(argumentations);
        return coweb.initCollab({id:"nonsense"});
      });
  });
