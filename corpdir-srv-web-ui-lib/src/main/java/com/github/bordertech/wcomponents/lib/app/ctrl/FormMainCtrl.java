package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller for a Form View.
 *
 * @param <T> the entity type
 * @author jonathan
 */
public class FormMainCtrl<T> extends DefaultController {

	@Override
	public void setupController() {
		super.setupController();

		// Form event type Listeners
		for (FormEventType eventType : FormEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleFormEvents(event);
				}
			};
			registerListener(eventType, listener);
		}

	}

	public final FormView<T> getFormView() {
		return getComponentModel().formView;
	}

	public final void setFormView(final FormView<T> formView) {
		getOrCreateComponentModel().formView = formView;
		addView(formView);
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getFormView() == null) {
			throw new IllegalStateException("A form view has not been set.");
		}
	}

	protected void handleFormEvents(final Event event) {
		FormEventType type = (FormEventType) event.getQualifier().getEventType();
		switch (type) {
			case LOAD:
				doLoad((T) event.getData(), FormMode.VIEW);
				break;
			case LOAD_NEW:
				doLoad((T) event.getData(), FormMode.ADD);
				break;
			case LOAD_OK:
				handleLoadedOKEvent();
				break;
			case RESET_FORM:
				resetFormView();
				break;
			case SHOW_FORM:
				showFormView(true);
				break;

			case ENTITY_MODE_CHANGED:
			case LOAD_ERROR:
				break;
		}
	}

	protected void handleLoadedOKEvent() {
		showFormView(true);
	}

	protected void doLoad(final T entity, final FormMode mode) {
		resetFormView();
		getFormView().loadEntity(entity, mode);
		dispatchEvent(FormEventType.ENTITY_MODE_CHANGED);
	}

	protected void resetFormView() {
		resetViews();
		for (View view : getGroupFormViews()) {
			view.resetView();
		}
	}

	protected void showFormView(final boolean show) {
		getFormView().setContentVisible(show);
		for (View view : getGroupFormViews()) {
			view.setContentVisible(show);
		}
	}

	public void addGroupFormView(final View view) {
		FormToolbarModel model = getOrCreateComponentModel();
		if (model.formGroup == null) {
			model.formGroup = new ArrayList();
		}
		model.formGroup.add(view);
		addView(view);
	}

	public List<View> getGroupFormViews() {
		FormToolbarModel model = getComponentModel();
		return model.formGroup == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(model.formGroup);
	}

	@Override
	protected FormToolbarModel<T> newComponentModel() {
		return new FormToolbarModel();
	}

	@Override
	protected FormToolbarModel<T> getComponentModel() {
		return (FormToolbarModel<T>) super.getComponentModel();
	}

	@Override
	protected FormToolbarModel<T> getOrCreateComponentModel() {
		return (FormToolbarModel<T>) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class FormToolbarModel<T> extends CtrlModel {

		private FormView<T> formView;

		private List<View> formGroup;

	}

}
