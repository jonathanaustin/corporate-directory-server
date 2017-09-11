package com.github.bordertech.wcomponents.lib.app.view.bar;

import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarItemType;

/**
 * Toolbar abstract implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractToolbar<T> extends DefaultView<T> implements ToolbarView<T> {

	@Override
	public void addToolbarType(final ToolbarItemType... types) {
		ToolbarModel model = getOrCreateComponentModel();
		if (model.toolbarTypes == null) {
			model.toolbarTypes = new HashSet<>();
		}
		model.toolbarTypes.addAll(Arrays.asList(types));
	}

	@Override
	public void removeToolbarType(final ToolbarItemType... types) {
		ToolbarModel model = getOrCreateComponentModel();
		if (model.toolbarTypes != null) {
			model.toolbarTypes.removeAll(Arrays.asList(types));
			if (model.toolbarTypes.isEmpty()) {
				model.toolbarTypes = null;
			}
		}
	}

	@Override
	public Set<ToolbarItemType> getToolbarTypes() {
		ToolbarModel model = getComponentModel();
		return model.toolbarTypes == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(model.toolbarTypes);
	}

	@Override
	public boolean isUseToolbarType(final ToolbarItemType type) {
		ToolbarModel model = getComponentModel();
		return model.toolbarTypes == null ? false : model.toolbarTypes.contains(type);
	}

	@Override
	public void clearToolbarTypes() {
		getOrCreateComponentModel().toolbarTypes = null;
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

		private Set<ToolbarItemType> toolbarTypes;
	}

}
