package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link Position} and {@link PositionEntity}.
 *
 * @author jonathan
 */
public class PositionMapper extends AbstractKeyIdApiEntityMapper<Position, PositionEntity> {

	@Override
	protected void copyApiToEntityFields(final EntityManager em, final Position from, final PositionEntity to) {
		to.setDescription(from.getDescription());
//		if (from.getBelongsToOrgUnit() != null) {
//			Long id = from.getBelongsToOrgUnit().getId();
//			to.setBelongsToOrgUnitKey(MapperUtil.convertEntityIdforApi(id));
//		}
//		to.setContactKeys(MapperUtil.convertEntitiesToApiKeys(from.getContacts()));
//		to.setManagesOrgUnitKeys(MapperUtil.convertEntitiesToApiKeys(from.getManageOrgUnits()));
//		to.setReportPositionKeys(MapperUtil.convertEntitiesToApiKeys(from.getReportPositions()));
	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final PositionEntity from, final Position to) {
		to.setDescription(from.getDescription());
		if (from.getOrgUnit() != null) {
			Long id = from.getOrgUnit().getId();
			to.setOuKey(MapperUtil.convertEntityIdforApi(id));
		}
		to.setContactKeys(MapperUtil.convertEntitiesToApiKeys(from.getContacts()));
		to.setManageOuKeys(MapperUtil.convertEntitiesToApiKeys(from.getManageOrgUnits()));
		to.setSubKeys(MapperUtil.convertEntitiesToApiKeys(from.getSubPositions()));
	}

	@Override
	protected Position createApiObject() {
		return new Position();
	}

	@Override
	protected PositionEntity createEntityObject(final Long id, final String businessKey) {
		return new PositionEntity(id, businessKey);
	}

}
