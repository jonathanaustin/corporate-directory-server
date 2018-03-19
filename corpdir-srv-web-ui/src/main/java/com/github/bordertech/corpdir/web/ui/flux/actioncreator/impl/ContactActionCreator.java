package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.DefaultCorpCrudActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.ContactApi;
import javax.inject.Inject;

/**
 * Contact action creator implementation.
 *
 * @author jonathan
 */
public class ContactActionCreator extends DefaultCorpCrudActionCreator<Contact, ContactApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public ContactActionCreator(final ContactApi api) {
		super(CorpEntityType.CONTACT, api);
	}
}
