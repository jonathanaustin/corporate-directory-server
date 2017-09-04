package com.github.bordertech.corpdir.api.v1.model.links;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import java.util.List;

/**
 * Contact links that can be versioned.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ContactLinks extends ApiVersionable {

	/**
	 *
	 * @return the assigned position keys
	 */
	List<String> getPositionIds();

	/**
	 *
	 * @param positionIds the assigned position keys
	 */
	void setPositionIds(final List<String> positionIds);
}
