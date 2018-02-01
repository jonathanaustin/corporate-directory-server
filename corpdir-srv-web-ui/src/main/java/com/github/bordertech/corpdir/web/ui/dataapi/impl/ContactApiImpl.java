package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import javax.inject.Inject;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;

/**
 * Contact CRUD API implementation.
 *
 * @author jonathan
 */
public class ContactApiImpl extends DefaultCorpCrudApi<Contact, ContactService> implements ContactApi {

	@Inject
	public ContactApiImpl(final ContactService service) {
		super(Contact.class, service);
	}

}
