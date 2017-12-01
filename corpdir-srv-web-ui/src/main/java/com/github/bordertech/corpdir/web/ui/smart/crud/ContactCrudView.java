package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.dumb.panel.ContactPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends DefaultCrudSmartView<String, Contact> {

	public ContactCrudView() {
		super("CT", "Contact", new ContactPanel("PL"));
	}

}
