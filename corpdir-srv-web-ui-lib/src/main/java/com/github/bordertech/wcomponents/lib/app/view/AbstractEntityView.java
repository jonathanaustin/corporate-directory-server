package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.view.AbstractBasicView;

/**
 * Abstract entity form view.
 *
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractEntityView<T> extends AbstractBasicView implements EntityView<T> {

	/**
	 * @return the bean being displayed.
	 */
	@Override
	public T getEntity() {
		return (T) getBeanValue();
	}

	@Override
	public void setEntity(final T entity) {
		setBean(entity);
	}

	@Override
	public void setEntityMode(final EntityMode mode) {
		getOrCreateComponentModel().entityMode = mode == null ? EntityMode.View : mode;
		handleViewState();
	}

	@Override
	public EntityMode getEntityMode() {
		return getComponentModel().entityMode;
	}

	/**
	 * @return true if form read only
	 */
	public boolean isFormReadOnly() {
		EntityMode mode = getEntityMode();
		return mode != EntityMode.Create && mode != EntityMode.Edit;
	}

	@Override
	protected void initContent(Request request) {
		super.initContent(request);
		handleViewState();
	}

	protected void handleViewState() {
		doMakeReadOnly(getContent(), isFormReadOnly());
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EntityViewModel getComponentModel() {
		return (EntityViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EntityViewModel getOrCreateComponentModel() {
		return (EntityViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class EntityViewModel extends PanelModel {

		private EntityMode entityMode = EntityMode.View;
	}
}
