define(
    ["dojo",
    "coweb/main",
    "dijit/registry",
    "dojox/grid/DataGrid",
    "dojo/data/ItemFileWriteStore",
    "cowebx/dojo/BusyDialog/BusyDialog",
    "dojo/_base/array",
    "dijit/form/Button",
    "dijit/layout/BorderContainer",
    "dijit/layout/ContentPane"
    ],
    function(dojo, coweb, dijit, DataGrid,
      ItemFileWriteStore, BusyDialog, arrays) {

      var ColistApp = function() {
        return;
      };
      var proto = ColistApp.prototype;

      proto.init = function() {
        console.log("ColistApp init called!");
        dojo.parser.parse();
        this.grid = dijit.byId("grid"); // not dojo.byId, which would give
        //the DOM object
        this.grid.canSort = function() {return false;};
        this.dataStore = null;
        this.dataStoreHandles = {};

        this.initCollab();

        this.localListData = [];
        this.buildList();

        //we're using this like a hashmap, where the keys are the "id"s of the
        //rows
        this.removed = {};
        var addButton = dijit.byId("addButton");
        var removeButton = dijit.byId("remButton");
        dojo.connect(addButton, "onClick", this, "onAddRow");
        dojo.connect(removeButton, "onClick", this, "onRemoveRow");

        //Session management stuff
        var session = coweb.initSession();
        session.onStatusChange = function(stat) {
          console.log(stat);
        };
        //BusyDialog.createBusy(session);
        var argumentations = {key: "the-onlu-session"};
        session.prepare(argumentations);
      };


      /*
       * begin code for hooking up dojo's DataStore object to methods
       * which send along the data to opencoweb 
       */

      // "static" variable (or at least that's why we assign it this way)
      ColistApp.typeToFuncMapping = {
        "update": {ds : "onSet", coop: "onLocalUpdate"},
        "insert": {ds : "onNew", coop: "onLocalInsert"},
        "delete": {ds : "onDelete", coop: "onLocalDelete"}
      };
      console.log();
      proto._connectToDataStore = function(type) {
        var funcs = ColistApp.typeToFuncMapping[type];
        var handle = dojo.connect(this.dataStore, funcs.ds, this, funcs.coop);
        // the handle must be stored to allow us to disconnect later
        this.dataStoreHandles[type] = handle;
      };
      proto._disconnectFromDataStore = function(type) {
        // do nothing if there is no handle stored in our handle map
        if(!this.dataStoreHandles[type]) return;
        dojo.disconnect(this.dataStoreHandles[type]);
        this.dataStoreHandles[type] = null;
      };
      proto._connectAllToDataStore = function() {
        arrays.forEach(Object.keys(ColistApp.typeToFuncMapping),
            function(type) {
              this._connectToDataStore(type);
            }, this);
      };
      proto._disconnectAllFromDataStore = function() {
        arrays.forEach(Object.keys(ColistApp.typeToFuncMapping),
            function(type) {
              this._disconnectFromDataStore(type);
            }, this);
      };
       proto._dsConnect = function(connect, type) {
       if (connect) {
       // get info about the data store and local functions
       var funcs = ColistApp.typeToFuncMapping[type];
       // do the connect
       var h = dojo.connect(this.dataStore, funcs.ds, this, funcs.coop);
       // store the connect handle so we can disconnect later
       this.dataStoreHandles[type] = h;
       } else {
       if (!this.dataStoreHandles[type])
       return;
       // disconnect using the previously stored handle
       dojo.disconnect(this.dataStoreHandles[type]);
       // delete the handle
       this.dataStoreHandles[type] = null;
       }
       };
      /*
       * end code for hooking up dojo's DataStore object to coweb
       */
      proto._serializeItem = function(item) {
        var plainOldJSONObject = {};
        arrays.forEach(this.dataStore.getAttributes(item), function(attr) {
          plainOldJSONObject[attr] = this.dataStore.getValue(item, attr);
        }, this); // tells forEach to bind "this" to the CollabApp object,
        // rather than what we're iterating over
        return plainOldJSONObject;
      };
      proto.onLocalInsert = function(item, parentInfoWhichWeDontNeed) {
        var newlyInsertedRow = this._serializeItem(item);
        var valueToPassAlong = {row: newlyInsertedRow};
        var position = this.grid.getItemIndex(item);
        this.localListData.splice(position, 0, newlyInsertedRow);
        this.collab.sendSync("listChange", valueToPassAlong, "insert",
            position);
        console.log("onLocalInsert called! added at postion " + position);
      };
      proto.onLocalUpdate = function(item, parentInfoWhichWeDontNeed) {
        //temp stub
      };
      proto.onLocalDelete = function(item) {
       // get all attribute values
       // name includes row id for conflict resolution
       var id = this.dataStore.getIdentity(item);
       var pos = this.removed[id];
       delete this.removed[id];
       this.localListData.splice(pos, 1);
       // Update this.removed data structure in case any positions need to be re-aligned.
       for (var k in this.removed) {
       if (this.removed[k] > pos)
       --this.removed[k];
       }
       this.collab.sendSync("listChange", null, "delete", pos);
       console.log("onLocalRemove called! remove at postion " + pos);
      };

      proto.initCollab = function() {
        console.log("initCollab called!");
        this.collab = coweb.initCollab({id:"nonsense"});
        this.collab.subscribeSync("listChange", this, "onRemoteChange");
        // now when a remote change with topic "lsitChange" is received, 
        // the method called "onRemoteChange will be invoked on this object
      };

      proto.onRemoteChange = function(args) {
        //temporarily stubbed
        console.log("remote change detected!!!!!");
       var value = args.value;
       if (args.type === "insert") {
       console.log("remote change detected! -- insert type");
       this.onRemoteInsert(value, args.position);
       } else if (args.type === "update") {
              console.log("remote change detected! -- update type");
       //this.onRemoteUpdate(value, args.position);
       } else if (args.type === "delete") {
              console.log("remote change detected! -- delete type");
       this.onRemoteDelete(args.position);
       }
      };

       proto.onRemoteInsert = function(value, position) {
       // This is the unfortunate case we must rebuild the data grid (since I can't insert at arbitrary position...).
       console.log("On remote insert called!!");
       this.localListData.splice(position,0,value.row);
       this.buildList();
       };
       
       /**
        * Called when an item disappears from a remote data store. Removes the
        * item with the same id from the local data store.
        *
        * @param position Which item to delete.
        */
       proto.onRemoteDelete = function(position) {
              var item = this.grid.getItem(position);
       this._dsConnect(false, "delete");
       
       this.localListData.splice(position, 1);
       this.dataStore.deleteItem(item);
       
       this._dsConnect(true, "delete");
       console.log("On remote delete called!!");

       };
       
       
      proto.buildList = function() {
        console.log("buildList called!");
        var emptyData = {data:{identifier:"id", label:"name", items:[]}};
        var store = new ItemFileWriteStore(emptyData);
        arrays.forEach(this.localListData, function(at) {
          store.newItem(at);
        });

        this.dataStore = store;
        this.grid.setStore(store);
        this._connectAllToDataStore();

      };


      proto.onAddRow = function() {
        var date = new Date();
        var id = String(Math.random()).substr(2) + String(date.getTime());
        var toBeAdded = {
          id: id,
          name: "New item",
          amount: 0
        };
        this.dataStore.newItem(toBeAdded);
        console.log("onAddRow called! added: " + toBeAdded.id);
      };
       

      proto.onRemoveRow = function() {
        console.log("onRemoveRow called!");
        var selected = this.grid.selection.getSelected();
        // Remember the positions of the removed elements in the "removed" object
        arrays.forEach(selected, function(item) {
          this.removed[this.dataStore.getIdentity(item)] = this.grid.getItemIndex(item);
        }, this);
        this.grid.removeSelectedRows();
      };

      var app = new ColistApp();
      dojo.ready(function() {
        app.init();
      });
    }
);
