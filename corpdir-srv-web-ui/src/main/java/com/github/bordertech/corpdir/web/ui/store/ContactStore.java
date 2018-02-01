package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;

/**
 * Contact Store with backing API.
 *
 * @author jonathan
 */
public interface ContactStore extends CorpCrudStore<Contact, ContactApi> {
}
