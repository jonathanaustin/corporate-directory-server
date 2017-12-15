package com.github.bordertech.flux.wc.view;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WDecoratedLabel;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.common.WLibImage;

/**
 * Utility methods useful when working with Views.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class ViewUtil {

	/**
	 * Do not allow instantiation.
	 */
	private ViewUtil() {
	}

	/**
	 * Find a the View instance that contains this component (anywhere in its ancestry).
	 *
	 * @param component The contained component.
	 * @return The View ancestor, if found.
	 */
	public static FluxDumbView findParentView(final WComponent component) {
		return WebUtilities.getAncestorOfClass(FluxDumbView.class, component);
	}

	/**
	 * Find a the ViewContainer instance that contains this component (anywhere in its ancestry).
	 *
	 * @param component The contained component.
	 * @return The ComboView ancestor, if found.
	 */
	public static FluxSmartView findParentSmartView(final WComponent component) {
		return WebUtilities.getAncestorOfClass(FluxSmartView.class, component);
	}

	/**
	 * Injects an image into a menu item.
	 *
	 * @param resourceUrl The path to the image.
	 * @param item The menu item into which the image will be injected.
	 * @param baseUrl true if a relative base URL
	 */
	public static void addImageToMenuItem(final String resourceUrl, final WMenuItem item, final boolean baseUrl) {
		addImageToLabelHead(resourceUrl, item.getDecoratedLabel(), baseUrl);
	}

	/**
	 * Injects an image into the head of a decorated label.
	 *
	 * @param resourceUrl The path to the image.
	 * @param label The label into which the image will be injected.
	 * @param baseUrl true if a relative base URL
	 */
	public static void addImageToLabelHead(final String resourceUrl, final WDecoratedLabel label, final boolean baseUrl) {
		WLibImage image = new WLibImage();
		image.setImageUrl(resourceUrl, baseUrl);
		label.setHead(image);
	}

	/**
	 * Injects an image into the body of a decorated label.
	 *
	 * @param resourceUrl The path to the image.
	 * @param label The label into which the image will be injected.
	 * @param baseUrl true if a relative base URL
	 */
	public static void addImageToLabelBody(final String resourceUrl, final WDecoratedLabel label, final boolean baseUrl) {
		WComponent labelBody = label.getBody();
		WLibImage image = new WLibImage();
		image.setImageUrl(resourceUrl, baseUrl);
		if (labelBody instanceof WText) {
			String bodyText;
			// For accessibility reasons we must not lose the text that describes this label.
			bodyText = ((WText) labelBody).getText();
			if (bodyText != null) {
				image.setAlternativeText(bodyText);
			}
		}
		label.setBody(image);
	}
}
