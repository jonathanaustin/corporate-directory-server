package com.github.bordertech.wcomponents.lib.app;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSuggestions;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import java.util.Collections;
import java.util.List;

/**
 * Default text search.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CriteriaTextView extends DefaultCriteriaView<String> {

	private final WTextField txtField = new WTextField();

	private final WSuggestions suggestions = new WSuggestions();

	public CriteriaTextView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public CriteriaTextView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);

		WContainer content = getContent();

		WPanel panel = new WPanel();
		content.add(panel);

		WFieldLayout layout = new WFieldLayout();
		panel.add(layout);

//		txtField.setMandatory(true);
		layout.addField("Search", txtField);

		layout.addField(getSearchButton());

		panel.add(suggestions);
		suggestions.setRefreshAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleSuggestions();
			}
		});
		suggestions.setVisible(false);

		panel.setDefaultSubmitButton(getSearchButton());

	}

	public final WSuggestions getSuggestions() {
		return suggestions;
	}

	public void setUseSuggestions(final boolean useSuggestions) {
		txtField.setSuggestions(useSuggestions ? suggestions : null);
		suggestions.setVisible(useSuggestions);
	}

	@Override
	public void updateViewBean() {
		setViewBean(txtField.getValue());
	}

	protected void doHandleSuggestions() {
		List<String> values = buildSuggestions(suggestions.getAjaxFilter());
		suggestions.setSuggestions(values);
	}

	protected List<String> buildSuggestions(final String filter) {
		return Collections.EMPTY_LIST;
	}

}
