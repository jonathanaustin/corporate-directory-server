package com.github.bordertech.corpdir.web.ui.model.impl;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.model.DefaultModelSearchActionService;

/**
 * Contact search and action model.
 *
 * @author jonathan
 */
public class ContactModel extends DefaultModelSearchActionService<Contact> {

	public ContactModel() {
		super(Contact.class, ContactService.class);
	}

}
