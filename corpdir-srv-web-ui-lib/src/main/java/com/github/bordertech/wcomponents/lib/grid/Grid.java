package com.github.bordertech.wcomponents.lib.grid;

import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.view.WDiv;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Grid panel.
 *
 * @author exitxl
 */
public class Grid extends WDiv {

	private final WTemplate gridTemplate = new WTemplate("/wclib/hbs/grid.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);

	private final WContainer itemsContainer = new WContainer() {
		@Override
		protected void paintComponent(final RenderContext renderContext) {
			List<String> items = getItemOrderIds();
			if (items.isEmpty()) {
				super.paintComponent(renderContext);
			} else {
				// Create map of IDs
				Map<String, WComponent> map = new HashMap<>();
				for (WComponent child : getChildren()) {
					map.put(child.getId(), child);
				}
				// Paint in correct order
				for (String id : items) {
					WComponent child = map.get(id);
					if (child != null) {
						child.paint(renderContext);
					}
				}
			}
		}
	};

	/**
	 * Construct panel.
	 */
	public Grid() {
		add(gridTemplate);
		gridTemplate.addTaggedComponent("items", itemsContainer);
	}

	public WTemplate getGridTemplate() {
		return gridTemplate;
	}

	public String getGridOptions() {
		return getComponentModel().gridOptions;
	}

	public void setGridOptions(final String gridOptions) {
		getOrCreateComponentModel().gridOptions = gridOptions;
	}

	public String getLayoutClass() {
		return getComponentModel().layoutClass;
	}

	public void setLayoutClass(final String layoutClass) {
		getOrCreateComponentModel().layoutClass = layoutClass;
	}

	public List<String> getItemOrderIds() {
		List<String> order = getComponentModel().itemOrderIds;
		return order == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(order);
	}

	public void setItemOrderIds(final List<String> itemOrderIds) {
		getOrCreateComponentModel().itemOrderIds = itemOrderIds;
	}

	public WContainer getItemsContainer() {
		return itemsContainer;
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		gridTemplate.addParameter("layoutClass", getLayoutClass());
	}

	@Override
	protected GridModel newComponentModel() {
		return new GridModel();
	}

	@Override
	protected GridModel getComponentModel() {
		return (GridModel) super.getComponentModel();
	}

	@Override
	protected GridModel getOrCreateComponentModel() {
		return (GridModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the event view.
	 */
	public static class GridModel extends DivModel {

		private List<String> itemOrderIds;

		private String layoutClass = "grid-5-cols";

		private String gridOptions = "{columnWidth: '.grid-sizer', itemSelector: '.gridItem', percentPosition: true}";
	}

}
