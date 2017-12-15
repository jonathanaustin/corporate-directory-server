package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapperId;
import com.github.bordertech.corpdir.jpa.entity.SystemCtrlEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import javax.persistence.EntityManager;

/**
 * Map {@link SystemCtrl} and {@link SystemCtrlEntity}.
 *
 * @author jonathan
 */
public class SystemCtrlMapper extends AbstractMapperId<SystemCtrl, SystemCtrlEntity> {

	@Override
	protected SystemCtrl createApiObject(final String id) {
		return new SystemCtrl(id);
	}

	@Override
	protected SystemCtrlEntity createEntityObject(final Long id) {
		// Always an id of 1 (only 1 record)
		return new SystemCtrlEntity();
	}

	@Override
	public void copyApiToEntity(final EntityManager em, final SystemCtrl from, final SystemCtrlEntity to) {
		super.copyApiToEntity(em, from, to);
		// Current Version
		String origId = MapperUtil.convertEntityIdforApi(to.getCurrentVersion());
		String newId = MapperUtil.cleanApiKey(from.getCurrentVersionId());
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setCurrentVersion(getVersionCtrlEntity(em, newId));
		}
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final SystemCtrlEntity from, final SystemCtrl to) {
		super.copyEntityToApi(em, from, to);
		to.setCurrentVersionId(MapperUtil.convertEntityIdforApi(from.getCurrentVersion()));
	}

	protected VersionCtrlEntity getVersionCtrlEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntityByApiId(em, keyId, VersionCtrlEntity.class);
	}

}
