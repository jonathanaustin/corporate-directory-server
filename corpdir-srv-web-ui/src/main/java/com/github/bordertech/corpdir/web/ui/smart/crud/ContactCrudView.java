package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.ContactPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends AppSecureCrudWrapperView<String, Contact> {

	public ContactCrudView() {
		super("CT", CardType.CONTACT, new DefaultCrudSmartView<String, Contact>("SV", "Contact", new ContactPanel("PL")));
	}

}
