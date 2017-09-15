package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.model.ModelSearchActionService;

/**
 * Contact search and action model.
 *
 * @author jonathan
 */
public class ContactModel extends ModelSearchActionService<Contact> {

	public ContactModel() {
		super(Contact.class, ContactService.class);
	}

}
