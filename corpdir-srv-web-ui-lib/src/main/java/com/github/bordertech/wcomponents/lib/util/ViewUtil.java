package com.github.bordertech.wcomponents.lib.util;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WImage;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Default updateable view that can be used as a child a of a from view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ViewUtil {

	private ViewUtil() {
	}

	public static View findParentView(final WComponent component) {
		return WebUtilities.getAncestorOfClass(View.class, component);
	}

	public static ComboView findParentCombo(final WComponent component) {
		return WebUtilities.getAncestorOfClass(ComboView.class, component);
	}

	public static void addImageToMenuItem(final String resource, final WMenuItem menuItem) {
		WImage image = new WImage();
		image.setImageUrl(resource);
		menuItem.getDecoratedLabel().setHead(image);
	}

}
