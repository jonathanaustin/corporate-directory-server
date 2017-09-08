package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 * Toolbar default implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultToolbarView<T> extends DefaultView<T> implements ToolbarView<T> {

	private final WMenu menu = new WMenu();

	private final WMenuItem itemBack = new ToolbarMenuItem("Back", ToolbarEventType.BACK) {
		@Override
		public boolean isVisible() {
			return isUseBack();
		}
	};

	private final WMenuItem itemAdd = new ToolbarMenuItem("Add", ToolbarEventType.ADD) {
		@Override
		public boolean isVisible() {
			return isUseAdd();
		}
	};

	private final WMenuItem itemReset = new ToolbarMenuItem("Reset", ToolbarEventType.RESET_VIEW) {
		@Override
		public boolean isVisible() {
			return isUseReset();
		}
	};

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}

	};

	public DefaultToolbarView() {
		WContainer content = getContent();
		content.add(menu);
		content.add(ajaxPanel);

		menu.add(itemBack);
		menu.add(itemAdd);
		menu.add(itemReset);
		menu.addHtmlClass("wc-neg-margin");
	}

	public final WMenu getMenu() {
		return menu;
	}

	public final WMenuItem getItemBack() {
		return itemBack;
	}

	public final WMenuItem getItemAdd() {
		return itemAdd;
	}

	public final WMenuItem getItemReset() {
		return itemReset;
	}

	@Override
	protected void initViewContent(final Request request) {
		setupMenuAjax();
		super.initViewContent(request);
	}

	protected void setupMenuAjax() {
		// Action
		Action action = new Action() {
			@Override
			public void execute(final ActionEvent event) {
				ToolbarMenuItem item = (ToolbarMenuItem) event.getSource();
				doDispatchToolbarEvent(item.getItemEvent(), item.getItemData());
			}
		};

		// Add Action and AJAX control for each menu item
		for (MenuItem menuItem : menu.getMenuItems(true)) {
			if (menuItem instanceof WMenuItem) {
				WMenuItem item = (WMenuItem) menuItem;
				item.setAction(action);
				ajaxPanel.add(new ToolbarAJaxControl(item, this));
			}
		}
	}

	protected void addTarget(final AjaxTarget target) {
		// Add a target to each AJAX control
		for (WComponent child : ajaxPanel.getChildren()) {
			WAjaxControl ctrl = (WAjaxControl) child;
			if (!ctrl.getTargets().contains(target)) {
				ctrl.addTarget(target);
			}
		}
	}

	@Override
	public final boolean isUseBack() {
		return getComponentModel().useBack;
	}

	@Override
	public final void setUseBack(final boolean useBack) {
		getOrCreateComponentModel().useBack = useBack;
	}

	@Override
	public final boolean isUseAdd() {
		return getComponentModel().useAdd;
	}

	@Override
	public final void setUseAdd(final boolean useAdd) {
		getOrCreateComponentModel().useAdd = useAdd;
	}

	@Override
	public final boolean isUseReset() {
		return getComponentModel().useReset;
	}

	@Override
	public final void setUseReset(final boolean useReset) {
		getOrCreateComponentModel().useReset = useReset;
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventTarget(target, eventType);
		addTarget(target);
	}

	protected void doDispatchToolbarEvent(final EventType eventType, final Object data) {
		dispatchViewEvent(eventType, data);
	}

	@Override
	protected ToolbarModel newComponentModel() {
		return new ToolbarModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ToolbarModel getComponentModel() {
		return (ToolbarModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ToolbarModel getOrCreateComponentModel() {
		return (ToolbarModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ToolbarModel extends DefaultView.ViewModel {

		private boolean useBack = false;
		private boolean useAdd = true;
		private boolean useReset = true;
	}

	public static class ToolbarMenuItem extends WMenuItem {

		private final EventType event;
		private final Object data;

		public ToolbarMenuItem(final String text, final EventType event) {
			this(text, event, null);
		}

		public ToolbarMenuItem(final String text, final EventType event, final Object data) {
			super(text);
			this.event = event;
			this.data = data;
		}

		public EventType getItemEvent() {
			return event;
		}

		public Object getItemData() {
			return data;
		}
	}

	public static class ToolbarAJaxControl extends WAjaxControl {

		public ToolbarAJaxControl(final AjaxTrigger trigger, final AjaxTarget target) {
			super(trigger, target);
		}

		@Override
		public boolean isVisible() {
			return getTrigger().isVisible();
		}
	}
}
