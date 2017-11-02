package com.github.bordertech.flux.wc.view;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WDecoratedLabel;
import com.github.bordertech.wcomponents.WImage;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;

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
	public static DumbView findParentView(final WComponent component) {
		return WebUtilities.getAncestorOfClass(DumbView.class, component);
	}

	/**
	 * Find a the ViewContainer instance that contains this component (anywhere in its ancestry).
	 *
	 * @param component The contained component.
	 * @return The ComboView ancestor, if found.
	 */
	public static SmartView findParentViewContainer(final WComponent component) {
		return WebUtilities.getAncestorOfClass(SmartView.class, component);
	}

	/**
	 * Injects an image into a menu item.
	 *
	 * @param resourceUrl The path to the image.
	 * @param item The menu item into which the image will be injected.
	 */
	public static void addImageToMenuItem(final String resourceUrl, final WMenuItem item) {
		addImageToLabelHead(resourceUrl, item.getDecoratedLabel());
	}

	/**
	 * Injects an image into the head of a decorated label.
	 *
	 * @param resourceUrl The path to the image.
	 * @param label The label into which the image will be injected.
	 */
	public static void addImageToLabelHead(final String resourceUrl, final WDecoratedLabel label) {
		WImage image = new WImage();
		image.setImageUrl(resourceUrl);
		label.setHead(image);
	}

	/**
	 * Injects an image into the body of a decorated label.
	 *
	 * @param resourceUrl The path to the image.
	 * @param label The label into which the image will be injected.
	 */
	public static void addImageToLabelBody(final String resourceUrl, final WDecoratedLabel label) {
		WComponent labelBody = label.getBody();
		WImage image = new WImage();
		image.setImageUrl(resourceUrl);
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
