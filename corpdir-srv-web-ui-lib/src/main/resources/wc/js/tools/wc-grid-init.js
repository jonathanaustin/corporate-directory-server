define(['wc/js/tools/masonry.pkgd-4.2.0', 'wc/dom/initialise'], function (Masonry, initialise) {

	function createGrid(gridItemId, options) {
		console.log('Setting up Grid ' + gridItemId);
		var grid = document.getElementById(gridItemId);
		if (grid) {
			console.log('Creating grid ' + grid.id);
			new Masonry(grid, options);
		}
	}

	return function (gridItemId, options) {
		if (initialise.domloaded) {
			createGrid(gridItemId, options);
		} else {
			initialise.register({ postInit: function (elem) {
					createGrid(gridItemId, options);
				} });
		}
	};

});
