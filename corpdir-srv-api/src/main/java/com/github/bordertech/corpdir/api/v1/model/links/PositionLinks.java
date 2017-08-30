package com.github.bordertech.corpdir.api.v1.model.links;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import java.util.List;

/**
 * Position links that can be versioned.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionLinks extends ApiVersionable {

	/**
	 *
	 * @return the org unit key the position belongs to
	 */
	String getOuId();

	/**
	 *
	 * @param ouId the org unit key the position belongs to
	 */
	void setOuId(final String ouId);

	/**
	 *
	 * @return the org unit keys this position manages
	 */
	List<String> getManageOuIds();

	/**
	 *
	 * @param manageOuIds the org unit keys this position manages
	 */
	void setManageOuIds(final List<String> manageOuIds);

	/**
	 *
	 * @return the contact keys for this position
	 */
	List<String> getContactIds();

	/**
	 *
	 * @param contactIds the contact keys for this position
	 */
	void setContactIds(final List<String> contactIds);

}
