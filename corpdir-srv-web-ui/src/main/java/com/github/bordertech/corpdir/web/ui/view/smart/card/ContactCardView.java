package com.github.bordertech.corpdir.web.ui.view.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.view.dumb.panel.ContactPanel;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpCrudSmartView;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpSecureCrudCardView;

/**
 * Contact CRUD view.
 *
 * @author jonathan
 */
public class ContactCardView extends DefaultCorpSecureCrudCardView<Contact> {

	public ContactCardView() {
		super("CT", CardType.CONTACT, new DefaultCorpCrudSmartView<Contact>("SV", "Contact", new ContactPanel("PL")));
	}

}
