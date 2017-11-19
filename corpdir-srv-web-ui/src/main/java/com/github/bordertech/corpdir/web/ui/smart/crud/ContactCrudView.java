package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.smart.panel.ContactPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends DefaultCrudSmartView {

	public ContactCrudView() {
		super("CT", "Contact", new ContactPanel("PL"));
//		setSearchModelKey("contact.search");
//		setActionModelKey("contact.action");
	}

}
