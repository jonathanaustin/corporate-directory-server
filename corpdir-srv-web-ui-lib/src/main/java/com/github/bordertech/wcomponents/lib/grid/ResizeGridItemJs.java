package com.github.bordertech.wcomponents.lib.grid;

import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;

/**
 *
 * @author exitxl
 */
public class ResizeGridItemJs extends WText {

	public ResizeGridItemJs() {
		setEncodeText(false);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		if (!isInitialised()) {
			// Find grid Item
			GridItem item = WebUtilities.getAncestorOfClass(GridItem.class, this);
			if (item != null) {
				setText("<script type='text/javascript'>require(['wc/js/tools/wc-grid-fit-item.js'], function(fit){fit('" + item.getId() + "');});</script>");
			}
			setInitialised(true);
		}
	}

	public void resizeGridItem() {
		setVisible(true);
	}

	@Override
	protected void afterPaint(final RenderContext renderContext) {
		setVisible(false);
	}

}
