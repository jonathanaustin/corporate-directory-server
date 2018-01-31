package com.github.bordertech.wcomponents.lib.common;

import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.lib.servlet.EnvironmentHelper;

/**
 * Allows a relative base URL to be used.
 * <p>
 * This will be put into WComponents core.
 * </p>
 *
 * @author jonathan
 */
public class WLibLink extends WLink {

	public WLibLink() {
	}

	public WLibLink(final String text, final String url) {
		super(text, url);
	}

	public void setUrl(final String url, final boolean relativeBaseUrl) {
		super.setUrl(url);
		setRelativeBaseUrl(relativeBaseUrl);
	}

	@Override
	public String getUrl() {
		String url = super.getUrl();
		if (url != null && isRelativeBaseUrl()) {
			return EnvironmentHelper.prefixBaseUrl(url);
		}
		return url;
	}

	public void setImageUrl(final String imageUrl, final boolean relativeBaseUrl) {
		super.setImageUrl(imageUrl);
		setRelativeBaseUrl(relativeBaseUrl);
	}

	@Override
	public String getImageUrl() {
		String url = super.getImageUrl();
		if (url != null && isImageRelativeBaseUrl()) {
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

	public void setImageRelativeBaseUrl(final boolean relativeBaseUrl) {
		setAttribute("wc_relative_img", relativeBaseUrl);
	}

	public boolean isImageRelativeBaseUrl() {
		Boolean flag = (Boolean) getAttribute("wc_relative_img");
		return flag != null && flag;
	}

}
