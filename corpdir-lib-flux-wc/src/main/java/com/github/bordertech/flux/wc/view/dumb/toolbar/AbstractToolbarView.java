package com.github.bordertech.flux.wc.view.dumb.toolbar;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.FluxAjaxControl;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.dumb.ToolbarView;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WDiv;
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
public class AbstractToolbarView<T> extends DefaultDumbView<T> implements ToolbarView<T> {

	private final WDiv ajaxPanel = new WDiv() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	public AbstractToolbarView(final String viewId) {
		super(viewId);
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
		return model.toolbarTypes != null && model.toolbarTypes.contains(type);
	}

	@Override
	public void clearToolbarItems() {
		getOrCreateComponentModel().toolbarTypes = null;
	}

	protected void setupAjaxControl(final ViewEventType type, final AjaxTrigger trigger) {
		FluxAjaxControl ctrl = new FluxAjaxControl(trigger, this);
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
	public static class ToolbarModel extends DefaultDumbView.ViewModel {

		private Set<ToolbarItem> toolbarTypes;
	}

}
