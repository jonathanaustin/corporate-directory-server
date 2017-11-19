package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.ContactPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends DefaultCrudSmartView {

	public ContactCrudView() {
		super("Contact", new ContactPanel());
		setSearchModelKey("contact.search");
		setActionModelKey("contact.action");
	}

}
