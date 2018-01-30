package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.dataapi.DefaultCrudDataApi;

/**
 * Contact search and action model.
 *
 * @author jonathan
 */
public class ContactCrudApi extends DefaultCrudDataApi<Contact, ContactService> {

	public ContactCrudApi() {
		super(Contact.class, ContactService.class);
	}

}
