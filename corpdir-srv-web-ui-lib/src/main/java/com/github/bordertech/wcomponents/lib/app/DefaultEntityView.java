package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultViewBound;

/**
 * Abstract entity form view.
 *
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultEntityView<T> extends DefaultViewBound<T> implements EntityView<T> {

	public DefaultEntityView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultEntityView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
	}

	@Override
	public void setEntityMode(final EntityMode mode) {
		if (getEntityMode() != mode) {
			getOrCreateComponentModel().entityMode = mode == null ? EntityMode.NONE : mode;
			doRefreshViewState();
			doDispatchChangeModeEvent();
		}
	}

	@Override
	public EntityMode getEntityMode() {
		return getComponentModel().entityMode;
	}

	public void doRefreshViewState() {
		doMakeReadOnly(getContent(), isFormReadOnly());
	}

	/**
	 * @return true if form read only
	 */
	@Override
	public boolean isFormReadOnly() {
		EntityMode mode = getEntityMode();
		return !(EntityMode.ADD.equals(mode) || EntityMode.EDIT.equals(mode));
	}

	@Override
	public boolean isLoaded() {
		EntityMode mode = getEntityMode();
		return !EntityMode.NONE.equals(mode);
	}

	@Override
	public void loadEntity(final T entity, final EntityMode mode) {
		resetView();
		setViewBean(entity);
		setEntityMode(mode);
		doDispatchLoadOKEvent();
	}

	@Override
	public WContainer getEntityDetailsHolder() {
		return getContent();
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		// Check entity is loaded
		if (!isLoaded()) {
			getViewMessages().error("No entity has been loaded.");
			setContentVisible(false);
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

	protected void doDispatchLoadOKEvent() {
		dispatchViewEvent(ActionEventType.LOAD_OK, getViewBean());
	}

	protected void doDispatchChangeModeEvent() {
		dispatchViewEvent(ActionEventType.ENTITY_MODE_CHANGED, getEntityMode());
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

		private EntityMode entityMode = EntityMode.NONE;
	}
}
