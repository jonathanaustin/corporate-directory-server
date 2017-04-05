package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import java.util.List;

/**
 * Position Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionService {

	DataResponse<List<Position>> getPositions(final String search, final Boolean assigned);

	DataResponse<Position> getPosition(final String positionKeyId);

	DataResponse<Position> createPosition(final Position position);

	DataResponse<Position> updatePosition(final String positionKeyId, final Position position);

	BasicResponse deletePosition(final String positionKeyId);

	DataResponse<List<Contact>> getContacts(final String positionKeyId);

	DataResponse<Position> addContact(final String positionKeyId, final String contactKeyId);

	DataResponse<Position> removeContact(final String positionKeyId, final String contactKeyId);

	DataResponse<List<Position>> getReports(final String positionKeyId);

	DataResponse<Position> addReport(final String positionKeyId, final String reportPositionKeyId);

	DataResponse<Position> removeReport(final String positionKeyId, final String reportPositionKeyId);

	DataResponse<List<OrgUnit>> getManages(final String positionKeyId);

}
