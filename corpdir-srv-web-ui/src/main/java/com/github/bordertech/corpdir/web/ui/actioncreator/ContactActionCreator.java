package com.github.bordertech.corpdir.web.ui.actioncreator;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;
import com.github.bordertech.corpdir.web.ui.flux.CorpCrudActionCreator;

/**
 * Contact CRUD ActionCreator.
 *
 * @author jonathan
 */
public interface ContactActionCreator extends CorpCrudActionCreator<Contact, ContactApi> {
}
