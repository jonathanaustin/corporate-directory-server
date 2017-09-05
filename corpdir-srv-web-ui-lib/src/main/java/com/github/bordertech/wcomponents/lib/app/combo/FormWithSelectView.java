package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormWithSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.mvc.impl.MessageCtrl;

/**
 * Form View with a Select View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class FormWithSelectView<S, T> extends DefaultComboView implements FormView<T> {

	private final MessageCtrl messageCtrl;

	private final FormView<T> formView;

	private final SelectView<T> selectView;

	public FormWithSelectView(final Dispatcher dispatcher, final String qualifier, final FormView<T> formView, final SelectView<T> selectView) {
		super("wclib/hbs/layout/combo-ent-select.hbs", dispatcher, qualifier);

		// Messages (default to show all)
		this.messageCtrl = new MessageCtrl(dispatcher, qualifier);
		messageCtrl.setMessageView(new DefaultMessageView(dispatcher, qualifier));

		// Views
		this.formView = formView;
		this.selectView = selectView;

		// Ctrl
		FormWithSelectCtrl<S, T> ctrl = new FormWithSelectCtrl<>(dispatcher, qualifier);
		ctrl.setFormView(formView);
		ctrl.setSelectView(selectView);

		// Reset
		ResetViewCtrl resetCtrl = new ResetViewCtrl(dispatcher, qualifier);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", messageCtrl.getMessageView());
		content.addTaggedComponent("vw-ctrl-msg", messageCtrl);
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-entity", formView);

		selectView.addHtmlClass("wc-panel-type-box");
		formView.addHtmlClass("wc-panel-type-box");

		// Default visibility
		formView.setContentVisible(false);

	}

	public final MessageCtrl getMessageCtrl() {
		return messageCtrl;
	}

	public final FormView<T> getFormView() {
		return formView;
	}

	public final SelectView<T> getSelectView() {
		return selectView;
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
