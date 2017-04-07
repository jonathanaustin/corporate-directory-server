package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;
import com.github.bordertech.corpdir.api.common.BasicTreeService;

/**
 * Position Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionService extends BasicTreeService<Position> {

	DataResponse<List<Contact>> getContacts(final String keyId);

	DataResponse<Position> addContact(final String keyId, final String contactKeyId);

	DataResponse<Position> removeContact(final String keyId, final String contactKeyId);

	DataResponse<List<OrgUnit>> getManages(final String keyId);

}
