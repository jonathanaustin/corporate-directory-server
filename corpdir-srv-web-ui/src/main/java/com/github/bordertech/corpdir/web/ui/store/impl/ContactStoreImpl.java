package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;
import com.github.bordertech.corpdir.web.ui.store.ContactStore;
import javax.inject.Inject;

/**
 * Contact Store with backing API implementation.
 *
 * @author jonathan
 */
public class ContactStoreImpl extends DefaultCorpStore<Contact, ContactApi> implements ContactStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public ContactStoreImpl(final ContactApi api) {
		super(CorpEntityType.CONTACT, api);
	}
}
