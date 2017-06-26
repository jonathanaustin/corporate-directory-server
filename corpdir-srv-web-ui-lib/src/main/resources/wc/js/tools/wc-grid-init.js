require(['wc/ajax/Trigger','wc/dom/formUpdateManager', 'wc/dom/initialise', 'wc/dom/event', 'wc/dom/Widget', 'wc/dom/classList'],
    function(Trigger, formUpdateManager, initialise, event, Widget, classList){
    //
    // Packery
    //
    var maxCols = 4;
    var isDrag = false;
//    var draggies = [];
    var GRID_WIDGET = new Widget("","grid");
    var CTRL_WIDGET = new Widget("", "grid-ctrl");
    var ITEM_WIDGET = new Widget("", "grid-item");
    var gridElementId;
    var gridie;
    
    
    //
    // Wire up AJAX
    //
    function writeState(form, stateContainer) {
          var elems = gridie.getGrid().getItemElements();
          elems.forEach(function(elem) {
              formUpdateManager.writeStateField(stateContainer, gridElementId + '.items', elem.id);
              formUpdateManager.writeStateField(stateContainer, elem.id + '.itemcols', getCurrentItemSize(elem));
          });
    }
    

    // Setup trigger
    var onSuccess = function(){
        console.log('Grid ajax fired successfully');
      };
    var onError = function(){
        console.log('Grid ajax fired with error');
    };
//    var trigger;
    
    
    
    // Packery Events
    function onLayoutComplete() {
        console.log('Layout complete fired');
    }

    function onRemoveComplete(){
        console.log('Remove complete fired');
        gridie.getGrid().refresh();
//        window.setTimeout(trigger.fire(), 250);
    }
      
    //
    // Click event on each grid item
    //
    function addGridClickEvents(){
         var elems = gridie.getItemElements();
         elems.forEach( function(itemElem) {
             event.add(itemElem, event.TYPE.click, gridClick);
        });
    }

    function gridClick($event){
        var item = $event.currentTarget;
        var element = $event.target;
        if (element) {
            if (classList.contains(element, "fa-hand-o-up")) {
                handleZoomUp(item);
            } else if (classList.contains(element, "fa-hand-o-down")) {
                handleZoomDown(item);
            } else if (classList.contains(element, "fa-trash")) {
                handleRemoveItem(item);
            }
        }
    } 
    function handleRemoveItem(item){
        // TO DO
        // gridie.extract(item);
    }
    
    function getMinColsForItem(item){
        for (i = 1; i <= maxCols; i++) {
            var hasValue = classList.contains(item, 'grid-item-col' + i);
            if (hasValue){
                return i;
            }
        }
        return 1;
    }
    function getCurrentItemSize(item){
        var cols = 1;
        for (i = 1; i <= maxCols; i++) {
            // Min value
            if (classList.contains(item, 'grid-item-col' + i)){
                cols = i;
            }
            // Expanded value
            if (classList.contains(item, 'is-expanded' + i)){
                cols = i;
            }
        }
        return cols;
    }
    
    function handleZoomUp(item){
        var changed = false;
        console.log('zoom up fired');
        
        var min = getMinColsForItem(item) + 1;
        for (i = min; i <= maxCols; i++) {
            var name = 'is-expanded' + i;
            var expanded = classList.contains(item, name);
            if (!expanded){
                classList.add(item, name);
                changed = true;
                break;
            }
        }
        if (changed) {
            console.log('zoom up');
            gridie.getGrid().refresh();
            resizeHighchart(item);
        }
    }
    
    function handleZoomDown(item){
        var changed = false;
        console.log('zoom down fired');
        var min = getMinColsForItem(item);
        for (i = maxCols; i > min; i--) {
            var name = 'is-expanded' + i;
            console.log(name);
            var expanded = classList.contains(item, name);
            if (expanded){
                classList.remove(item, name);
                changed = true;
                break;
            }
        }
        if (changed) {
            console.log('zoom down');
            gridie.getGrid().layout();
            resizeHighchart(item);
        }
    }
    
    function resizeHighchart(item){
        //var hasHighchart = classList.contains(item, 'myhighchart');
        if (window.Highcharts && classList.contains(item, 'myhighchart')){
            window.Highcharts.charts.forEach( function( chart ) {
               if (item.contains(chart.renderTo)){
                  window.setTimeout(function(){chart.reflow();}, 250);
                }
            });
        }
    }
    


    
    function handleToggleDrag(){
        // switch flag
        isDrag = !isDrag;
        setToggleState();
    }
    
    function setToggleState(){
//        var method = isDrag ? 'enable' : 'disable';
//        var gridElem = document.getElementById(gridElementId);
//        draggies.forEach( function( draggie ) {
//            $(draggie).draggable( "option", "disabled", !isDrag);
//        });
        var $items = $('.gridItem');
        $items.draggable( "option", "disabled", !isDrag);
        
        var nodes = CTRL_WIDGET.findDescendants(gridElem);
        for (i = 0; i < nodes.length; ++i) {
            if (isDrag){
                classList.remove(nodes[i], 'grid-ctrl-hidden');
            } else {
                classList.add(nodes[i], 'grid-ctrl-hidden');
            }
        }
        
        var ctrlToggleElem = document.querySelector('.toggle-ctrl-button');
        classList.remove(ctrlToggleElem, "fa-edit", "fa-pencil");
        classList.add(ctrlToggleElem, isDrag ? "fa-edit" : "fa-pencil");
    }
    
    //
    // Body Click
    //
    function handleBodyClick($event){
        var element = $event.target;
        if (element && classList.contains(element, "toggle-ctrl-button")) {
            handleToggleDrag();
        }
    }



    function doInit(element) {
        console.log('Setting up Grid');
        
//        setItemPositions(element);
        // Load Gridie
        require(['wc/js/tools/wc-GridInteract-1.0.0.js'], function(GridInteract){
            setupGrid(element, GridInteract);
        });
    }
    
//    function setItemPositions(element){
//        console.log('Initialise positions');
//        var gridElem = GRID_WIDGET.findDescendant(element);
//        var nodes = ITEM_WIDGET.findDescendants(gridElem);
//        for (var i = 0; i < nodes.length; ++i) {
//            var item = nodes[i];
//            var cols = getCurrentItemSize(item);
//            item.setAttribute("data-x", i);
//            item.setAttribute("data-y", cols);
//        }
//    }

    function setupGrid(element, GridInteract){    
        console.log('Setting up Grid');

        var gridElem = GRID_WIDGET.findDescendant(element);
        gridie = new GridInteract(gridElem);
        var gridElem = GRID_WIDGET.findDescendant(element);
        gridElementId = gridElem.id;
//        trigger = new Trigger({id: gridElementId, loads:[""]}, onSuccess, onError);
       
//        msnry.on( 'layoutComplete', onLayoutComplete );
//        pckry.on( 'dragItemPositioned', onDragComplete );
//        msnry.on( 'removeComplete', onRemoveComplete );
        formUpdateManager.subscribe(writeState);
//        event.add(element, event.TYPE.click, handleBodyClick);
        // Setup grid
//        addGridClickEvents();
        
//        setToggleState();
    }
    

    

    // initialise.register({postInit: doInit});
    initialise.register({postInit: function(elem){window.setTimeout(doInit, 1000, elem);}});

});
