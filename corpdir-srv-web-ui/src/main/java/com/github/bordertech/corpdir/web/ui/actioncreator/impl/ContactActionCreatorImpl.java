package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.ContactActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudActionCreator;
import javax.inject.Inject;

/**
 * Contact action creator implementation.
 *
 * @author jonathan
 */
public class ContactActionCreatorImpl extends DefaultCorpCrudActionCreator<Contact, ContactApi> implements ContactActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public ContactActionCreatorImpl(final ContactApi api) {
		super(CorpEntityType.CONTACT, api);
	}
}
