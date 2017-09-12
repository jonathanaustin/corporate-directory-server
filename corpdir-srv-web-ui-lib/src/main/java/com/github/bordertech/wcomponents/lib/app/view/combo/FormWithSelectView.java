package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormAndSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;

/**
 * Form View with a Select View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class FormWithSelectView<T> extends DefaultMessageComboView<T> {

	public FormWithSelectView(final FormView<T> formView, final SelectView<T> selectView) {
		super("wclib/hbs/layout/combo-ent-select.hbs");

		// Ctrl
		FormAndSelectCtrl<T> ctrl = new FormAndSelectCtrl<>();
		ctrl.setSelectView(selectView);
		ctrl.addView(getMessageView());

		// Reset
		ResetViewCtrl resetCtrl = new ResetViewCtrl();

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-entity", formView);

		selectView.addHtmlClass("wc-panel-type-box");
		formView.addHtmlClass("wc-panel-type-box");

		// Default visibility
		formView.setContentVisible(false);
	}

}
