package com.github.bordertech.wcomponents.lib.grid;

import com.github.bordertech.wcomponents.AjaxHelper;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.view.WDiv;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;

/**
 * Grid Item.
 *
 * @author exitxl
 */
public class GridItem extends WTemplate {

	private final WDiv contentHolder = new WDiv();
	private final WDiv resizeHolder = new WDiv() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	private final ResizeGridItemJs jsResizeItem = new ResizeGridItemJs();

	public GridItem(final int cols) {
		super("/wclib/hbs/gridItem.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);

		addTaggedComponent("content", contentHolder);
		addTaggedComponent("resize", resizeHolder);
		resizeHolder.add(jsResizeItem);
		setColumns(cols);
	}

	public void resizeGridItem() {
		// Only resize in AJAX request
		if (AjaxHelper.getCurrentOperation() != null) {
			jsResizeItem.resizeGridItem();
		}
	}

	public WDiv getContentHolder() {
		return contentHolder;
	}

	public AjaxTarget getResizeGridItemTarget() {
		return resizeHolder;
	}

	public void setColumns(final int cols) {
		getOrCreateComponentModel().cols = cols < 1 ? 1 : cols;
	}

	public int getColumns() {
		return getComponentModel().cols;
	}

	public String getLayoutClass() {
		Grid grid = WebUtilities.getAncestorOfClass(Grid.class, this);
		return grid.getLayoutClass();
	}

	public String getWidthClass() {
		int cols = getColumns();
		return cols > 1 ? "grid-item--width" + cols : "";
	}

	@Override
	protected GridItemModel newComponentModel() {
		return new GridItemModel();
	}

	@Override
	protected GridItemModel getComponentModel() {
		return (GridItemModel) super.getComponentModel();
	}

	@Override
	protected GridItemModel getOrCreateComponentModel() {
		return (GridItemModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the event view.
	 */
	public static class GridItemModel extends TemplateModel {

		private int cols = 1;
	}

}
