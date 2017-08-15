package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.app.view.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;

/**
 * Abstract entity form view.
 *
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEntityView<T> extends DefaultView<T> implements EntityView<T> {

	public DefaultEntityView(final Dispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void setEntityMode(final EntityMode mode) {
		if (getEntityMode() != mode) {
			boolean current = isFormReadOnly();
			getOrCreateComponentModel().entityMode = mode == null ? EntityMode.VIEW : mode;
			if (current != isFormReadOnly()) {
				doRefreshViewState();
			}
		}
	}

	@Override
	public EntityMode getEntityMode() {
		return getComponentModel().entityMode;
	}

	@Override
	public void doRefreshViewState() {
		doMakeReadOnly(getViewHolder(), isFormReadOnly());
	}

	/**
	 * @return true if form read only
	 */
	@Override
	public boolean isFormReadOnly() {
		EntityMode mode = getEntityMode();
		return !EntityMode.ADD.equals(mode) && !EntityMode.EDIT.equals(mode);
	}

	@Override
	public boolean isLoaded() {
		return getViewBean() != null;
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		// Check entity is loaded
		if (!isLoaded()) {
			getViewMessages().error("No entity has been loaded.");
			makeHolderInvisible();
			return;
		}
		doRefreshViewState();
	}

	protected void doMakeReadOnly(final WComponent component, final boolean readOnly) {
		if (component instanceof Input) {
			((Input) component).setReadOnly(readOnly);
		}

		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				doMakeReadOnly(child, readOnly);
			}
		}
	}

	@Override
	protected EntityViewModel newComponentModel() {
		return new EntityViewModel();
	}

	@Override
	protected EntityViewModel getComponentModel() {
		return (EntityViewModel) super.getComponentModel();
	}

	@Override
	protected EntityViewModel getOrCreateComponentModel() {
		return (EntityViewModel) super.getOrCreateComponentModel();
	}

	public static class EntityViewModel extends ViewModel {

		private EntityMode entityMode = EntityMode.VIEW;
	}
}
