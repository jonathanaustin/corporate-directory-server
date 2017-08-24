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
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.Arrays;
import java.util.List;

/**
 * Toolbar default implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultToolbarView extends DefaultView implements ToolbarView {

	private final WMenu menu = new WMenu();

	private final WMenuItem itemBack = new MyMenuItem("Back", ActionEventType.BACK) {
		@Override
		public boolean isVisible() {
			return isUseBack();
		}
	};

	private final WMenuItem itemAdd = new MyMenuItem("Add", ActionEventType.ADD) {
		@Override
		public boolean isVisible() {
			return isUseAdd();
		}
	};

	private final WMenuItem itemReset = new MyMenuItem("Reset", ActionEventType.RESET_VIEW) {
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

	/**
	 * Construct the Menu Bar.
	 *
	 * @param dispatcher the controller for this view
	 */
	public DefaultToolbarView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	/**
	 * Construct the Menu Bar.
	 *
	 * @param dispatcher the controller for this view
	 * @param qualifier the qualifier
	 */
	public DefaultToolbarView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

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
				MyMenuItem item = (MyMenuItem) event.getSource();
				doDispatchToolbarEvent(item.getItemEvent());
			}
		};

		// Add Action and AJAX control for each menu item
		for (MenuItem menuItem : menu.getMenuItems()) {
			if (menuItem instanceof WMenuItem) {
				WMenuItem item = (WMenuItem) menuItem;
				item.setAction(action);
				ajaxPanel.add(new MyAjaxControl(item));
			}
		}
	}

	protected void addTarget(final AjaxTarget target) {
		addTargets(Arrays.asList(target));
	}

	protected void addTargets(final List<AjaxTarget> targets) {
		// Add a target to each AJAX control
		for (WComponent child : ajaxPanel.getChildren()) {
			WAjaxControl ctrl = (WAjaxControl) child;
			ctrl.addTargets(targets);
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

	protected void doDispatchToolbarEvent(final ActionEventType eventType) {
		dispatchViewEvent(eventType);
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

	public static class MyMenuItem extends WMenuItem {

		private final ActionEventType event;

		public MyMenuItem(final String text, final ActionEventType event) {
			super(text);
			this.event = event;
		}

		public ActionEventType getItemEvent() {
			return event;
		}
	}

	public static class MyAjaxControl extends WAjaxControl {

		public MyAjaxControl(final AjaxTrigger trigger) {
			super(trigger);
		}

		@Override
		public boolean isVisible() {
			return getTrigger().isVisible();
		}
	}
}
