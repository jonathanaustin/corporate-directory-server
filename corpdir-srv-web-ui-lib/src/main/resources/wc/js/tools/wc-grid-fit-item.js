define(['wc/js/tools/wc-GridInteract-1.0.0.js'], function(GridInteract){
    
    function resizeGrid(gridItemId){
        return function(){
            console.log('Resizing item ' + gridItemId);
            var gridItem = document.getElementById(gridItemId);
            if (gridItem){
                console.log('Found item ' + gridItemId);
//                var msnry = Masonry.data('.grid');
//                if (msnry){
//                    console.log('Fitting item ' + gridItemId);
//                    msnry.layout();
//                }
            }
        }
    }

    return function(gridItemId){
        window.setTimeout(resizeGrid(gridItemId), 1200);
    };
});
