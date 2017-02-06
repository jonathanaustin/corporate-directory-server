package com.github.bordertech.corpdir.api;

import com.github.bordertech.corpdir.api.data.Image;

/**
 * Image Service Interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ImageService {

	Image getImage(final Long imageId);

	Long createImage(final Image image);

	void updateImage(final Long imageId, final String desc);

	void deleteImage(final Long imageId);

}
