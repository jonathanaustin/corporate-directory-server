package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.ContactModel;
import com.github.bordertech.corpdir.web.ui.panel.ContactPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView2;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends DefaultCrudView2 {

	private static final ContactModel MODEL = new ContactModel();

	public ContactCrudView(final String qualifier) {
		super("Contact", MODEL, new ContactPanel(), qualifier);
	}

}
