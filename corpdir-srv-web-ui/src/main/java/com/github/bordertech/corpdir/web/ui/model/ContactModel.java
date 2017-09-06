package com.github.bordertech.corpdir.web.ui.model;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.web.ui.util.LocatorUtil;
import com.github.bordertech.wcomponents.lib.model.ActionModel;
import com.github.bordertech.wcomponents.lib.model.SearchModel;
import java.util.List;

/**
 * Contact model.
 *
 * @author jonathan
 */
public class ContactModel implements SearchModel<String, List<Contact>>, ActionModel<Contact> {

	private static final ContactService SERVICE = LocatorUtil.getService(ContactService.class);

	@Override
	public List<Contact> search(final String criteria) {
		DataResponse<List<Contact>> resp = SERVICE.search(criteria);
		return resp.getData();
	}

	@Override
	public Contact create(final Contact entity) {
		DataResponse<Contact> resp = SERVICE.create(entity);
		return resp.getData();
	}

	@Override
	public Contact update(final Contact entity) {
		DataResponse<Contact> resp = SERVICE.update(entity.getId(), entity);
		return resp.getData();
	}

	@Override
	public void delete(final Contact entity) {
		SERVICE.delete(entity.getId());
	}

	@Override
	public Contact refresh(final Contact entity) {
		DataResponse<Contact> resp = SERVICE.retrieve(entity.getId());
		return resp.getData();
	}

	@Override
	public Contact createInstance() {
		return new Contact(null);
	}

}
