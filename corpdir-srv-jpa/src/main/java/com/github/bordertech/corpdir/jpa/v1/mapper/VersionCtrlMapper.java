package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapperId;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;

/**
 * Map {@link VersionCtrl} and {@link VersionCtrlEntity}.
 *
 * @author jonathan
 */
public class VersionCtrlMapper extends AbstractMapperId<VersionCtrl, VersionCtrlEntity> {

	@Override
	protected VersionCtrl createApiObject(final String id) {
		return new VersionCtrl(id);
	}

	@Override
	protected VersionCtrlEntity createEntityObject(final Long id) {
		return new VersionCtrlEntity(id);
	}

}
