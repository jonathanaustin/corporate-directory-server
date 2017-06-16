package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.shell.EntityView;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WPanel;

/**
 * Abstract entity form view.
 *
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractEntityView<T> extends WPanel implements EntityView<T> {

	/**
	 * @return the bean being displayed.
	 */
	@Override
	public T getEntity() {
		return (T) getBean();
	}

	/**
	 * @return true if form read only
	 */
	public boolean isFormReadOnly() {
		return getComponentModel().formReadOnly;
	}

	/**
	 *
	 * @param formReadOnly true if form read only
	 */
	public void setFormReadOnly(final boolean formReadOnly) {
		EntityViewModel model = getComponentModel();
		boolean changed = model.formReadOnly == formReadOnly;
		if (changed) {
			getOrCreateComponentModel().formReadOnly = formReadOnly;
			handleReadOnlyState();
		}
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			initForm();
			setInitialised(true);
		}
	}

	protected void initForm() {
		handleReadOnlyState();
	}

	protected void handleReadOnlyState() {
		doMakeReadOnly(this, isFormReadOnly());
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

		private boolean formReadOnly = true;
	}
}
