package com.github.bordertech.wcomponents.lib.common;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.WDecoratedLabel;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.servlet.EnvironmentHelper;

/**
 * Allows a relative base URL to be used.
 * <p>
 * This will be put into WComponents core.
 * </p>
 *
 * @author jonathan
 */
public class WLibMenuItem extends WMenuItem {

	public WLibMenuItem(final WDecoratedLabel label) {
		super(label);
	}

	public WLibMenuItem(final WDecoratedLabel label, final String url) {
		super(label, url);
	}

	public WLibMenuItem(final WDecoratedLabel label, final Action action) {
		super(label, action);
	}

	public WLibMenuItem(final String text, final String url) {
		super(text, url);
	}

	public WLibMenuItem(final String text, final Action action) {
		super(text, action);
	}

	public WLibMenuItem(final String text) {
		super(text);
	}

	public WLibMenuItem(final String text, final char accessKey) {
		super(text, accessKey);
	}

	public WLibMenuItem(final String text, final char accessKey, final Action action) {
		super(text, accessKey, action);
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

	public void setRelativeBaseUrl(final boolean relativeBaseUrl) {
		setAttribute("wc_relative", relativeBaseUrl);
	}

	public boolean isRelativeBaseUrl() {
		Boolean flag = (Boolean) getAttribute("wc_relative");
		return flag != null && flag;
	}

}
