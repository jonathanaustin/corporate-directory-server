package com.github.bordertech.wcomponents.lib.grid;

import com.github.bordertech.wcomponents.AjaxHelper;
import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.view.WDiv;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;

/**
 * Grid Item.
 *
 * @author exitxl
 */
public class GridItem extends WTemplate {

	private final WDiv contentHolder = new WDiv();

	private final Grid grid;

	public GridItem(final Grid grid, final int cols) {
		super("/wclib/hbs/grid-css-item.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		this.grid = grid;
		addTaggedComponent("content", contentHolder);
		setColumns(cols);
	}

	public Grid getGrid() {
		return grid;
	}

	@Override
	public void handleRequest(final Request request) {
		super.handleRequest(request);
		clearResize();
	}

	public WDiv getContentHolder() {
		return contentHolder;
	}

	public void setColumns(final int cols) {
		getOrCreateComponentModel().cols = cols < 1 ? 1 : cols;
	}

	public int getColumns() {
		return getComponentModel().cols;
	}

	public void resizeItem() {
		// Only do resize if in AJAX operation
		boolean ajax = AjaxHelper.getCurrentOperation() != null;
		if (!isResize() && ajax) {
			getOrCreateComponentModel().resize = true;
		}
	}

	public void clearResize() {
		if (isResize()) {
			getOrCreateComponentModel().resize = false;
		}

	}

	public boolean isResize() {
		return getComponentModel().resize;
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		setupParameters();
	}

	protected void setupParameters() {
		addParameter("gridId", getGrid().getId());
		addParameter("columns", getColumns());
		addParameter("itemId", getId());
		addParameter("resize", isResize());
		addParameter("itemClass", getHtmlClass());
	}

	@Override
	protected void afterPaint(final RenderContext renderContext) {
		super.afterPaint(renderContext);
		clearResize();
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

		private boolean resize;
	}

}
