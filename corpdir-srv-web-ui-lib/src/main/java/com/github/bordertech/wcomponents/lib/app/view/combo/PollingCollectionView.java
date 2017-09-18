package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.CollectionMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingCollectionCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.model.RetrieveCollectionModelKey;
import com.github.bordertech.wcomponents.lib.app.view.CollectionView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import java.util.Collection;

/**
 * Collection View with a Text Search View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public class PollingCollectionView<S, T, C extends Collection<T>> extends DefaultMessageComboView<C> implements CollectionView<T, C>, RetrieveCollectionModelKey {

	private final CollectionView<T, C> collectionView;

	private final PollingView<S, C> pollingView;

	private final PollingCollectionCtrl<S, T, C> ctrl;

	public PollingCollectionView(final CollectionView<T, C> collectionView) {
		super("wclib/hbs/layout/combo-list-crit.hbs");

		// Views
		this.collectionView = collectionView;
		this.pollingView = new DefaultPollingView<>();

		// Polling and List Ctrl
		ctrl = new PollingCollectionCtrl<>();
		ctrl.setPollingView(pollingView);
		ctrl.addView(getMessageView());

		CollectionMainCtrl listCtrl = new CollectionMainCtrl();
		listCtrl.setCollectionView(collectionView);

		ResetViewCtrl resetCtrl = new ResetViewCtrl();

		// Add views to holder
		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl-pol", ctrl);
		content.addTaggedComponent("vw-ctrl-lst", listCtrl);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", collectionView);

		// Default visibility
		collectionView.setContentVisible(false);
	}

	public PollingView<S, C> getPollingView() {
		return pollingView;
	}

	public CollectionView<T, C> getCollectionView() {
		return collectionView;
	}

	@Override
	public C getViewBean() {
		return collectionView.getViewBean();
	}

	@Override
	public void setViewBean(final C viewBean) {
		collectionView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		collectionView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return collectionView.validateView();
	}

	@Override
	public void addItem(final T item) {
		collectionView.addItem(item);
	}

	@Override
	public void removeItem(final T item) {
		collectionView.removeItem(item);
	}

	@Override
	public void updateItem(final T item) {
		collectionView.updateItem(item);
	}

	@Override
	public void showCollection(final boolean show) {
		collectionView.showCollection(show);
	}

	@Override
	public int getSize() {
		return collectionView.getSize();
	}

	@Override
	public void setRetrieveCollectionModelKey(final String key) {
		ctrl.setRetrieveCollectionModelKey(key);
	}

	@Override
	public String getRetrieveCollectionModelKey() {
		return ctrl.getRetrieveCollectionModelKey();
	}

	public void doStartPolling(final S criteria) {
		ctrl.doStartPolling(criteria);
	}

	@Override
	public void setItems(final C items) {
		collectionView.setItems(items);
	}

	@Override
	public C getItems() {
		return collectionView.getItems();
	}

	@Override
	public void refreshItems(final C items) {
		collectionView.refreshItems(items);
	}

}
