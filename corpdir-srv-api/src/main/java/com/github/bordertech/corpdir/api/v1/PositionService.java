package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.ServiceBasicResponse;
import com.github.bordertech.corpdir.api.response.ServiceResponse;
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

	ServiceResponse<List<Position>> getPositions(final String search, final Boolean assigned);

	ServiceResponse<Position> getPosition(final String positionKeyId);

	ServiceResponse<Position> createPosition(final Position position);

	ServiceResponse<Position> updatePosition(final String positionKeyId, final Position position);

	ServiceBasicResponse deletePosition(final String positionKeyId);

	ServiceResponse<List<Contact>> getContacts(final String positionKeyId);

	ServiceResponse<Position> addContact(final String positionKeyId, final String contactKeyId);

	ServiceResponse<Position> removeContact(final String positionKeyId, final String contactKeyId);

	ServiceResponse<List<Position>> getReports(final String positionKeyId);

	ServiceResponse<Position> addReport(final String positionKeyId, final String reportPositionKeyId);

	ServiceResponse<Position> removeReport(final String positionKeyId, final String reportPositionKeyId);

	ServiceResponse<List<OrgUnit>> getManages(final String positionKeyId);

}
