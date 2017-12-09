package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.ContactPanel;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCrudView extends AppSecureCrudView<String, Contact> {

	public ContactCrudView() {
		super(CardType.CONTACT_CARD, "CT", "Contact", new ContactPanel("PL"));
	}

}
