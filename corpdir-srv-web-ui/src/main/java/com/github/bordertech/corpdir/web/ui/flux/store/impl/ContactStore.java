package com.github.bordertech.corpdir.web.ui.flux.store.impl;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.ContactApi;
import com.github.bordertech.corpdir.web.ui.flux.store.DefaultCorpCrudVersionStore;
import javax.inject.Inject;

/**
 * Contact Store with backing API implementation.
 *
 * @author jonathan
 */
public class ContactStore extends DefaultCorpCrudVersionStore<Contact, ContactApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public ContactStore(final ContactApi api) {
		super(CorpEntityType.CONTACT, api);
	}
}
