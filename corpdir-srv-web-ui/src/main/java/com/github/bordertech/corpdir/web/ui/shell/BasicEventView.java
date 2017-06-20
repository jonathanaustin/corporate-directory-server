package com.github.bordertech.corpdir.web.ui.shell;

import java.util.List;

/**
 * Basic View with Events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicEventView extends BasicView {

	List<? extends ViewAction> getViewActions();

}
