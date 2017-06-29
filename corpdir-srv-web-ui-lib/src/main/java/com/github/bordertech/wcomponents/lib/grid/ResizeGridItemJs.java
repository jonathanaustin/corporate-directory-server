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
		setVisible(false);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		if (!isInitialised()) {
			// Find grid ID
			Grid grid = WebUtilities.getAncestorOfClass(Grid.class, this);
			if (grid != null) {
				setText("<script type='text/javascript'>require(['wclib/js/wc-grid-resize'], function(fit){fit('" + grid.getId() + "');});</script>");
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
