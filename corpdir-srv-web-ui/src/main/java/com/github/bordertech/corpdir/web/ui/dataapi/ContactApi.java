package com.github.bordertech.corpdir.web.ui.dataapi;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudVersionDataApi;

/**
 * Contact CRUD API.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ContactApi extends CorpCrudVersionDataApi<Contact, ContactService> {
}
