package com.github.bordertech.wcomponents.lib.common;

import com.github.bordertech.wcomponents.ImageResource;
import com.github.bordertech.wcomponents.WImage;
import com.github.bordertech.wcomponents.lib.servlet.EnvironmentHelper;

/**
 * Allows a relative base URL to be used.
 * <p>
 * This will be put into WComponents core.
 * </p>
 *
 * @author jonathan
 */
public class WLibImage extends WImage {

	public WLibImage() {
	}

	public WLibImage(final String imageResource, final String description) {
		super(imageResource, description);
	}

	public WLibImage(final ImageResource image) {
		super(image);
	}

	public void setImageUrl(final String imageUrl, final boolean relativeBaseUrl) {
		super.setImageUrl(imageUrl);
		setRelativeBaseUrl(relativeBaseUrl);
	}

	@Override
	public String getImageUrl() {
		String url = super.getImageUrl();
		if (url != null && isRelativeBaseUrl()) {
			return EnvironmentHelper.prefixBaseUrl(url);
		}
		return url;
	}

	public void setRelativeBaseUrl(final boolean relativeBaseUrl) {
		setAttribute("wc_relative", relativeBaseUrl);
	}

	public boolean isRelativeBaseUrl() {
		Boolean flag = (Boolean) getAttribute("wc_relative");
		return flag != null && flag;
	}

}
