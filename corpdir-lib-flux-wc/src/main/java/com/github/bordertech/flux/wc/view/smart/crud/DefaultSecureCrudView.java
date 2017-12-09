package com.github.bordertech.flux.wc.view.smart.crud;

import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.smart.secure.SecureCardView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.security.AppPath;

/**
 * Default secure CRUD view.
 *
 * @author jonathan
 */
public class DefaultSecureCrudView<S, T> extends DefaultCrudSmartView<S, T> implements SecureCardView<T> {

	private final AppPath path;

	public DefaultSecureCrudView(final AppPath path, final String viewId, final String title, final WComponent panel) {
		super(viewId, title, panel);
		this.path = path;
	}

	@Override
	public AppPath getAppPath() {
		return path;
	}

	@Override
	public void handleShowCardRequest(final Request request) {
		S criteria = getRequestCriteria(request);
		if (criteria != null) {
			doRequestCriteria(criteria);
		}
	}

	@Override
	public void handleCheckCardRequest(final Request request) {
		SearchView<S> view = getSearchView();
		// Current search
		S current = view.getViewBean();
		// Search parameter on the request
		S criteria = getRequestCriteria(request);
		// Check state is OK
		if (criteria != null && !java.util.Objects.equals(current, criteria)) {
			doRequestCriteria(criteria);
		}
	}

	protected void doRequestCriteria(final S criteria) {
		resetView();
		setAutoSearch(true);
		getSearchView().setViewBean(criteria);
	}

	protected S getRequestCriteria(final Request request) {
		return null;
	}

}
