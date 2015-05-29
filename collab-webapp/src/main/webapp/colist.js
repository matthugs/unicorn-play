define(
    ["dojo",
     "coweb/main",
     "dijit/registry",
     "dojox/grid/DataGrid",
     "dojo/data/ItemFileWriteStore",
     "dojo/_base/array",
     "dijit/form/Button",
     "dijit/layout/BorderContainer",
     "dijit/layout/ContentPane"
    ],
     function(dojo, coweb, dijit, DataGrid,
         ItemFileWriteStore, arrays) {

         var ColistApp = function() {
             return;
         };
         var proto = ColistApp.prototype;

         proto.init = function() {
             dojo.parser.parse();
             this.grid = dojo.byId("grid");
             console.log("ColistApp init called!");
         };

         var app = new ColistApp();
         dojo.ready(function() {
             app.init();
         });
     }
);
