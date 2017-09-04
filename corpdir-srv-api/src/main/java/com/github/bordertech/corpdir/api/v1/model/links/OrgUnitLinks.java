package com.github.bordertech.corpdir.api.v1.model.links;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import java.util.List;

/**
 * Organization unit links that can be versioned.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitLinks extends ApiVersionable {

	/**
	 *
	 * @return the manager position key
	 */
	String getManagerPosId();

	/**
	 *
	 * @param managerPosId the manager position key
	 */
	void setManagerPosId(final String managerPosId);

	/**
	 *
	 * @return the position keys that belong to this org unit
	 */
	List<String> getPositionIds();

	/**
	 *
	 * @param positionIds the list of position keys that belong to this org unit
	 */
	void setPositionIds(final List<String> positionIds);
}
