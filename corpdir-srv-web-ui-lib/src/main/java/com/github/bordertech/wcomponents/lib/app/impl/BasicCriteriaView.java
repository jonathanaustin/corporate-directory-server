package com.github.bordertech.wcomponents.lib.app.impl;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WSuggestions;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import java.util.Collections;
import java.util.List;

/**
 * Default text search.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicCriteriaView extends AbstractCriteriaView<String> {

	private final WTextField txtField = new WTextField();

	private final WSuggestions suggestions = new WSuggestions();

	public BasicCriteriaView(final Dispatcher dispatcher) {
		super(dispatcher);

		WDiv content = getContent();

		WFieldLayout layout = new WFieldLayout();
		content.add(layout);

		txtField.setMandatory(true);
		layout.addField("Search", txtField);

		layout.addField(getSearchButton());

		content.add(suggestions);
		suggestions.setRefreshAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleSuggestions();
			}
		});

	}

	public final WSuggestions getSuggestions() {
		return suggestions;
	}

	public void setUseSuggestions(final boolean useSuggestions) {
		txtField.setSuggestions(useSuggestions ? suggestions : null);
		suggestions.setVisible(useSuggestions);
	}

	@Override
	public void doUpdateViewBean() {
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
