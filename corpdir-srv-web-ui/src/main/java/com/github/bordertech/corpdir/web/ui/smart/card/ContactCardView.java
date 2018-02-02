package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpSecureCrudCardView;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.ContactPanel;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudSmartView;

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
