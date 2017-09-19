package com.github.bordertech.wcomponents.lib.app.view.toolbar;

import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Toolbar abstract implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractToolbar<T> extends DefaultView<T> implements ToolbarView<T> {

	private final WDiv ajaxPanel = new WDiv() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	public AbstractToolbar() {
		WContainer content = getContent();
		content.add(ajaxPanel);
	}

	public final WDiv getAjaxPanel() {
		return ajaxPanel;
	}

	@Override
	public void addToolbarItem(final ToolbarItem... types) {
		ToolbarModel model = getOrCreateComponentModel();
		if (model.toolbarTypes == null) {
			model.toolbarTypes = new HashSet<>();
		}
		model.toolbarTypes.addAll(Arrays.asList(types));
	}

	@Override
	public void removeToolbarItem(final ToolbarItem... types) {
		ToolbarModel model = getOrCreateComponentModel();
		if (model.toolbarTypes != null) {
			model.toolbarTypes.removeAll(Arrays.asList(types));
			if (model.toolbarTypes.isEmpty()) {
				model.toolbarTypes = null;
			}
		}
	}

	@Override
	public Set<ToolbarItem> getToolbarItems() {
		ToolbarModel model = getComponentModel();
		return model.toolbarTypes == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(model.toolbarTypes);
	}

	@Override
	public boolean isUseToolbarItem(final ToolbarItem type) {
		ToolbarModel model = getComponentModel();
		return model.toolbarTypes == null ? false : model.toolbarTypes.contains(type);
	}

	@Override
	public void clearToolbarItems() {
		getOrCreateComponentModel().toolbarTypes = null;
	}

	protected void setupAjaxControl(final EventType type, final AjaxTrigger trigger) {
		AppAjaxControl ctrl = new AppAjaxControl(trigger, this);
		getAjaxPanel().add(ctrl);
		registerEventAjaxControl(type, ctrl);
	}

	@Override
	protected ToolbarModel newComponentModel() {
		return new ToolbarModel();
	}

	@Override
	protected ToolbarModel getComponentModel() {
		return (ToolbarModel) super.getComponentModel();
	}

	@Override
	protected ToolbarModel getOrCreateComponentModel() {
		return (ToolbarModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ToolbarModel extends DefaultView.ViewModel {

		private Set<ToolbarItem> toolbarTypes;
	}

}
