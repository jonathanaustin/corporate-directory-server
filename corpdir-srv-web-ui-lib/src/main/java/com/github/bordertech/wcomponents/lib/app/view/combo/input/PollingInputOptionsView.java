package com.github.bordertech.wcomponents.lib.app.view.combo.input;

import com.github.bordertech.wcomponents.AbstractWSelectList;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.InputOptionsMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingInputOptionsCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.model.keys.RetrieveCollectionModelKey;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.input.InputOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.List;

/**
 * Polling View and Input Options View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class PollingInputOptionsView<S, T> extends DefaultMessageComboView<T> implements InputOptionsView<T>, RetrieveCollectionModelKey {

	private final InputOptionsView<T> optionsView;

	private final PollingView<S, List<T>> pollingView;

	private final PollingInputOptionsCtrl<S, T> ctrl = new PollingInputOptionsCtrl<>();

	public PollingInputOptionsView(final InputOptionsView<T> optionsView) {
		super("wclib/hbs/layout/combo-input-select.hbs");

		// Views
		this.optionsView = optionsView;
		this.pollingView = new DefaultPollingView<>();

		// Polling and List Ctrl
		ctrl.setPollingView(pollingView);
		ctrl.addView(getMessageView());

		InputOptionsMainCtrl<T> optionsCtrl = new InputOptionsMainCtrl();
		optionsCtrl.setOptionsView(optionsView);

		ResetViewCtrl resetCtrl = new ResetViewCtrl();

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl-pol", ctrl);
		content.addTaggedComponent("vw-ctrl-opt", optionsCtrl);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", optionsView);

		// Default visibility
		optionsView.setContentVisible(false);
	}

	public PollingView<S, List<T>> getPollingView() {
		return pollingView;
	}

	public InputOptionsView<T> getOptionsView() {
		return optionsView;
	}

	@Override
	public T getViewBean() {
		return optionsView.getViewBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		optionsView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		optionsView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return optionsView.validateView();
	}

	@Override
	public void setRetrieveCollectionModelKey(final String key) {
		ctrl.setRetrieveCollectionModelKey(key);
	}

	@Override
	public String getRetrieveCollectionModelKey() {
		return ctrl.getRetrieveCollectionModelKey();
	}

	@Override
	public void setOptions(final List<T> options) {
		optionsView.setOptions(options);
	}

	@Override
	public List<T> getOptions() {
		return optionsView.getOptions();
	}

	@Override
	public void addOption(final T option) {
		optionsView.addOption(option);
	}

	@Override
	public void removeOption(final T option) {
		optionsView.removeOption(option);
	}

	@Override
	public void updateOption(final T option) {
		optionsView.updateOption(option);
	}

	@Override
	public int getSize() {
		return optionsView.getSize();
	}

	@Override
	public AbstractWSelectList getSelectInput() {
		return optionsView.getSelectInput();
	}

	@Override
	public void showView(final boolean show) {
		optionsView.showView(show);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		optionsView.doMakeFormReadonly(readonly);
	}

}
