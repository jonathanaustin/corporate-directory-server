package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.event.base.RetrieveActionType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.flux.app.store.retrieve.RetrieveListStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.wc.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.smart.polling.DefaultPollingMessageSmartView;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;
import java.util.List;

/**
 * Abstract Polling Smart View that retrieves a list.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <L> the list item type
 * @param <T> the item type
 */
public abstract class AbstractListSmartView<S, L, T> extends DefaultPollingMessageSmartView<T> {

	public AbstractListSmartView(final String viewId, final String template) {
		super(viewId, template);
	}

	@Override
	protected void handlePollingStartEvent(final PollingBaseViewEvent type) {
		dispatchEvent(RetrieveBaseEventType.RETRIEVE, getListCriteria(), RetrieveActionType.CALL_ASYNC);
	}

	@Override
	protected void handlePollingCheckStatusEvent() {
		if (isListStoreDone()) {
			setPollingStatus(PollingStatus.STOPPED);
			handleListResult();
		}
	}

	protected void handleListResult() {
		resetContent();
		try {
			List<L> items = getListStore().retrieve(getListCriteria());
			handleResultSuccessful(items);
		} catch (Exception e) {
			handleResultError(e);
		}
	}

	protected abstract void handleResultSuccessful(final List<L> items);

	protected void handleResultError(final Exception excp) {
		handleMessageError("Error loading details. " + excp.getMessage());
	}

	public void setListStoreType(final StoreType storeType) {
		getOrCreateComponentModel().listStoreType = storeType;
	}

	public StoreType getListStoreType() {
		return getComponentModel().listStoreType;
	}

	public void setListCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	public S getListCriteria() {
		return getComponentModel().criteria;
	}

	protected boolean isListStoreDone() {
		return getListStore().isRetrieveDone(getListCriteria());
	}

	protected RetrieveListStore<S, L> getListStore() {
		return StoreUtil.getStore(getListStoreType(), getFullQualifier());
	}

	@Override
	protected PollingListModel<S> newComponentModel() {
		return new PollingListModel();
	}

	@Override
	protected PollingListModel<S> getComponentModel() {
		return (PollingListModel) super.getComponentModel();
	}

	@Override
	protected PollingListModel<S> getOrCreateComponentModel() {
		return (PollingListModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class PollingListModel<S> extends ViewContainerModel {

		private StoreType listStoreType;

		private S criteria;
	}
}
