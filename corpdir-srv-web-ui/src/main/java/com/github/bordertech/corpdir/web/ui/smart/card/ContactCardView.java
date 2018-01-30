package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.ContactPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudSmartView;

/**
 * Contact crud view.
 *
 * @author jonathan
 */
public class ContactCardView extends AppSecureCrudCardView<Contact> {

	public ContactCardView() {
		super("CT", CardType.CONTACT, new DefaultCorpCrudSmartView<Contact>("SV", "Contact", new ContactPanel("PL")));
	}

}
