package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.ContactPanel;
import com.github.bordertech.wcomponents.lib.app.view.smart.crud.DefaultCrudView;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends DefaultCrudView {

	public ContactCrudView() {
		super("Contact", new ContactPanel());
		setSearchModelKey("contact.search");
		setActionModelKey("contact.action");
	}

}
