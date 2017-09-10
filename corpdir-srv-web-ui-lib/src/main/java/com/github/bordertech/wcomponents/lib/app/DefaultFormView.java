package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.lib.app.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;

/**
 * Default form view.
 *
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultFormView<T> extends DefaultView<T> implements FormView<T> {

	@Override
	public void setFormMode(final FormMode mode) {
		if (getFormMode() != mode) {
			getOrCreateComponentModel().entityMode = mode == null ? FormMode.NONE : mode;
			doRefreshViewState();
			doDispatchChangeModeEvent();
		}
	}

	@Override
	public FormMode getFormMode() {
		return getComponentModel().entityMode;
	}

	public void doRefreshViewState() {
		boolean readonly = isFormReadonly();
		FormUtil.doMakeInputsReadonly(this, readonly);
	}

	/**
	 * @return true if form read only
	 */
	@Override
	public boolean isFormReadonly() {
		FormMode mode = getFormMode();
		return !(FormMode.ADD.equals(mode) || FormMode.EDIT.equals(mode));
	}

	@Override
	public boolean isLoaded() {
		FormMode mode = getFormMode();
		return !FormMode.NONE.equals(mode);
	}

	@Override
	public void loadEntity(final T entity, final FormMode mode) {
		resetView();
		setViewBean(entity);
		setFormMode(mode);
		doDispatchLoadOKEvent();
	}

	@Override
	public WContainer getFormHolder() {
		return getContent();
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		// Check entity is loaded
		if (!isLoaded()) {
			dispatchMessage(MsgEventType.ERROR, "No entity has been loaded.");
			setContentVisible(false);
			return;
		}
		doRefreshViewState();
	}

	protected void doDispatchLoadOKEvent() {
		dispatchEvent(FormEventType.LOAD_OK, getViewBean());
	}

	protected void doDispatchChangeModeEvent() {
		dispatchEvent(FormEventType.ENTITY_MODE_CHANGED, getFormMode());
	}

	@Override
	protected FormModel newComponentModel() {
		return new FormModel();
	}

	@Override
	protected FormModel getComponentModel() {
		return (FormModel) super.getComponentModel();
	}

	@Override
	protected FormModel getOrCreateComponentModel() {
		return (FormModel) super.getOrCreateComponentModel();
	}

	public static class FormModel extends ViewModel {

		private FormMode entityMode = FormMode.NONE;
	}
}
