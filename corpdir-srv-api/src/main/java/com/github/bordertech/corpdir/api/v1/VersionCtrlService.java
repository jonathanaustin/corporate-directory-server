package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicIdService;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;

/**
 * Version Control Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface VersionCtrlService extends BasicIdService<VersionCtrl> {

	/**
	 * Short hand create function.
	 *
	 * @param description the version description
	 * @return a new version id
	 */
	DataResponse<Long> createVersion(final String description);

	/**
	 * Copy a version.
	 *
	 * @param fromId copy from version id
	 * @param toId copy to version id
	 * @param copySystem copy the system details
	 * @param copyCustom copy the custom details
	 * @return basic response if successful or error occurred
	 */
	BasicResponse copyVersion(final Long fromId, final Long toId, boolean copySystem, boolean copyCustom);

}
