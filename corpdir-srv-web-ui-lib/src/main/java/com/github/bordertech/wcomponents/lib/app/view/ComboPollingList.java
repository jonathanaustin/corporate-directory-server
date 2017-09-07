package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface ComboPollingList<S, T> extends ComboView<List<T>> {

	ListView<T> getListView();

	PollingView<S, List<T>> getPollingView();

}
