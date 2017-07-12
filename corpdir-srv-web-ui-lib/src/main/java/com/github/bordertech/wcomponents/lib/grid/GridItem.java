package com.github.bordertech.wcomponents.lib.grid;

import com.github.bordertech.wcomponents.AjaxHelper;
import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.view.WDiv;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import java.util.Map;

/**
 * Grid Item.
 *
 * @author exitxl
 */
public class GridItem extends WTemplate {

	private final WTemplate config = new WTemplate();

	private final WDiv contentHolder = new WDiv();

	private final Grid grid;

	public GridItem(final Grid grid) {
		this(grid, 1);
	}

	public GridItem(final Grid grid, final int spans) {
		super("/wclib/hbs/grid-item.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		this.grid = grid;
		addTaggedComponent("config", config);
		addTaggedComponent("content", contentHolder);
		setSpans(spans);
		config.setVisible(false);
		config.setEngineName(TemplateRendererFactory.TemplateEngine.HANDLEBARS);
	}

	public Grid getGrid() {
		return grid;
	}

	public WTemplate getConfig() {
		return config;
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
		getOrCreateComponentModel().spans = spans < 0 ? 0 : spans;
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
		if (grid.getItemConfigName() != null) {
			config.setTemplateName(grid.getItemConfigName());
			config.setVisible(true);
		}
	}

	protected void setupParameters() {

		int spans = getSpans() > grid.getMaxColumns() ? grid.getMaxColumns() : getSpans();
		String spanClass = spans > 0 ? "span-" + spans : "";

		addParameter("gridId", getGrid().getId());
		addParameter("spanClass", spanClass);
		addParameter("spans", spans);
		addParameter("itemId", getId());
		addParameter("resize", isResize());
		addParameter("htmlClasses", getHtmlClass());
		addParameter("dropMode", getDropMode());
		addParameter("dragMode", getDragMode());

		if (config.isVisible()) {
			for (Map.Entry<String, Object> entry : getParameters().entrySet()) {
				config.addParameter(entry.getKey(), entry.getValue());
			}
		}
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

		private int spans;

		private boolean resize;

		private DragMode dragMode;

		private DropMode dropMode;
	}

}
