package com.github.bordertech.corpdir.web.ui.dataapi.impl;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudDataApi;
import javax.inject.Inject;

/**
 * Contact CRUD API implementation.
 *
 * @author jonathan
 */
public class ContactApiImpl extends DefaultCorpCrudDataApi<Contact, ContactService> implements ContactApi {

	@Inject
	public ContactApiImpl(final ContactService service) {
		super(Contact.class, service);
	}

}
