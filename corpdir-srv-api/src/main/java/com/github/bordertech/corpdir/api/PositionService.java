package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.Position;
import java.util.List;

/**
 * Position Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionService {

	Position getPosition(final Long positionId);

	Position getPosition(final String positionAltKey);

	List<Position> getSubPositions(final Long positionId);

	List<Position> getSubPositions(final String positionAltKey);

	List<Contact> getAssignedContacts(final Long positionId);

	List<Contact> getAssignedContacts(final String positionAltKey);

	Long createPosition(final Position position);

	Position updatePosition(final Position position);

	void deletePosition(final Long positionId);

	void assignContact(final Long positionId, final Long contactId);

	void unassignContact(final Long positionId, final Long contactId);

	void assignPositionToPosition(final Long positionId, final Long newParentId);

}
