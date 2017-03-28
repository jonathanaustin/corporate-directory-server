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

	ServiceResponse<Position> getPosition(final String positionKeyId);

	ServiceResponse<List<Position>> getReportPositions(final String positionKeyId);

	ServiceResponse<List<Contact>> getAssignedContacts(final String positionKeyId);

	ServiceResponse<List<OrgUnit>> getManagesOrgUnits(final String positionKeyId);

	ServiceResponse<Position> createPosition(final Position position);

	ServiceResponse<Position> updatePosition(final String positionKeyId, final Position position);

	ServiceBasicResponse deletePosition(final String positionKeyId);

	ServiceBasicResponse assignContact(final String positionKeyId, final String contactKeyId);

	ServiceBasicResponse unassignContact(final String positionKeyId, final String contactKeyId);

	ServiceBasicResponse assignPositionToPosition(final String positionKeyId, final String reportToPositionKeyId);

}
