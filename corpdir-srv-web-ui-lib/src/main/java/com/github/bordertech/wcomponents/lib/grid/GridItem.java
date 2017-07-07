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

	public GridItem(final Grid grid) {
		this(grid, 1);
	}

	public GridItem(final Grid grid, final int cols) {
		super("/wclib/hbs/grid-css-item.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		this.grid = grid;
		addTaggedComponent("content", contentHolder);
		setSpans(cols);
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

	public void setSpans(final int spans) {
		getOrCreateComponentModel().spans = spans < 1 ? 1 : spans;
	}

	public int getSpans() {
		return getComponentModel().spans;
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
		if (grid.getItemTemplateName() != null) {
			setTemplateName(grid.getItemTemplateName());
		}
		if (grid.getItemEngineName() != null) {
			setEngineName(grid.getItemEngineName());
		}
	}

	protected void setupParameters() {

		int spans = getSpans() > grid.getMaxColumns() ? grid.getMaxColumns() : getSpans();

		addParameter("gridId", getGrid().getId());
		addParameter("spans", spans);
		addParameter("itemId", getId());
		addParameter("resize", isResize());
		addParameter("itemClasses", getHtmlClass());
		addParameter("dropMode", getDropMode());
		addParameter("dragMode", getDragMode());
	}

	public void setDragMode(final DragMode mode) {
		getOrCreateComponentModel().dragMode = mode;
	}

	public DragMode getDragMode() {
		return getComponentModel().dragMode;
	}

	public void setDropMode(final DropMode mode) {
		getOrCreateComponentModel().dropMode = mode;
	}

	public DropMode getDropMode() {
		return getComponentModel().dropMode;
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

		private int spans = 1;

		private boolean resize;

		private DragMode dragMode;

		private DropMode dropMode;
	}

}
