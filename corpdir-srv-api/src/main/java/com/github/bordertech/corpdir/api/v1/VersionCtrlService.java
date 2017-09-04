package com.github.bordertech.corpdir.api.v1;

import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import java.io.Serializable;
import java.util.List;

/**
 * Version Control Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface VersionCtrlService extends Serializable {

	/**
	 * @return the current version details
	 */
	DataResponse<List<VersionCtrl>> retrieveVersions();

	/**
	 * @return the current version details
	 */
	DataResponse<Long> getCurrentVersion();

	/**
	 * @param versionId
	 * @return the current version details
	 */
	DataResponse<Long> setCurrentVersion(final Long versionId);

	/**
	 * @param description the version description
	 * @return a new version id
	 */
	DataResponse<Long> createVersion(final String description);

	/**
	 *
	 * @param versionId the version id to delete (cannot be the current version)
	 * @return basic response if successful or error occurred
	 */
	BasicResponse deleteVersion(final Long versionId);

	/**
	 *
	 * @param fromId copy from version id
	 * @param toId copy to version id
	 * @param copySystem copy the system details
	 * @param copyCustom copy the custom details
	 * @return basic response if successful or error occurred
	 */
	BasicResponse copyVersion(final Long fromId, final Long toId, boolean copySystem, boolean copyCustom);

}
