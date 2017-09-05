package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.DefaultFormView;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;

/**
 * Form View with a Toolbar View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class FormWithToolbarView<T> extends DefaultMessageComboView implements FormView<T> {

	private final FormView<T> formView;

	private final FormToolbarView toolbarView;

	public FormWithToolbarView(final Dispatcher dispatcher, final String qualifier) {
		this(dispatcher, qualifier, new DefaultFormView<T>(dispatcher, qualifier));
	}

	public FormWithToolbarView(final Dispatcher dispatcher, final String qualifier, final FormView<T> formView) {
		this(dispatcher, qualifier, formView, new DefaultFormToolbarView(dispatcher, qualifier));
	}

	public FormWithToolbarView(final Dispatcher dispatcher, final String qualifier, final FormView<T> formView, final FormToolbarView toolbarView) {
		super("wclib/hbs/layout/combo-ent-toolbar.hbs", dispatcher, qualifier);

		// Views
		this.formView = formView;
		this.toolbarView = toolbarView;

		// Ctrl
		FormWithToolbarCtrl<T> ctrl = new FormWithToolbarCtrl(dispatcher, qualifier);
		ctrl.setToolbarView(toolbarView);
		ctrl.setFormView(formView);

		ResetViewCtrl resetCtrl = new ResetViewCtrl(dispatcher, qualifier);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-entity", formView);
	}

	public final FormView<T> getFormView() {
		return formView;
	}

	public final FormToolbarView getToolbarView() {
		return toolbarView;
	}

	@Override
	public FormMode getFormMode() {
		return formView.getFormMode();
	}

	@Override
	public void setFormMode(final FormMode mode) {
		formView.setFormMode(mode);
	}

	@Override
	public boolean isFormReadOnly() {
		return formView.isFormReadOnly();
	}

	@Override
	public boolean isLoaded() {
		return formView.isLoaded();
	}

	@Override
	public void loadEntity(final T entity, final FormMode mode) {
		formView.loadEntity(entity, mode);
	}

	@Override
	public T getViewBean() {
		return formView.getViewBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		formView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		formView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return formView.validateView();
	}

	@Override
	public WContainer getFormHolder() {
		return formView.getFormHolder();
	}

}
