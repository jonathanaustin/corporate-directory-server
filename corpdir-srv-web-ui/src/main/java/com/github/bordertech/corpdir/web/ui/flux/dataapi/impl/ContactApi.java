package com.github.bordertech.corpdir.web.ui.flux.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.DefaultCorpCrudVersionDataApi;
import javax.inject.Inject;

/**
 * Contact CRUD API implementation.
 *
 * @author jonathan
 */
public class ContactApi extends DefaultCorpCrudVersionDataApi<Contact, ContactService> {

	@Inject
	public ContactApi(final ContactService service) {
		super(Contact.class, service);
	}

}
