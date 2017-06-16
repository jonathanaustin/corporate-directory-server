package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class AbstractSearchListController<T, V> extends WPanel implements MessageContainer {

	private final WMessages messages = new WMessages();

	private final CriteriaView<V> criteriaView;
	private final PollingServiceView<V, List<T>> pollingView;
	private final ListView<T> listView;

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return !pollingView.isVisible() && !listView.isVisible();
		}
	};

	public AbstractSearchListController(final CriteriaView<V> criteriaView, final PollingServiceView<V, List<T>> pollingView, final ListView<T> listView) {
		this.criteriaView = criteriaView;
		this.pollingView = pollingView;
		this.listView = listView;

		// Actions
		criteriaView.setCriteriaAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleCriteria(criteriaView.getCriteria());
			}
		});

		add(criteriaView);
		add(ajaxPanel);
		ajaxPanel.add(pollingView);
		ajaxPanel.add(listView);

		// Default visibility
		pollingView.setVisible(false);
		listView.setVisible(false);

		// AJAX Targets
		criteriaView.addAjaxTarget(messages);
		pollingView.addAjaxTarget(messages);
		listView.addAjaxTarget(messages);

		criteriaView.addAjaxTarget(ajaxPanel);
		pollingView.addAjaxTarget(ajaxPanel);

	}

	public CriteriaView<V> getCriteriaView() {
		return criteriaView;
	}

	public PollingServiceView<V, List<T>> getPollingView() {
		return pollingView;
	}

	public ListView<T> getListView() {
		return listView;
	}

	protected void handleCriteria(final V criteria) {

	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
