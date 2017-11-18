package com.github.bordertech.flux.wc.app.view.dumb.search;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSuggestions;
import com.github.bordertech.wcomponents.WTextField;
import java.util.Collections;
import java.util.List;

/**
 * Default text search.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SearchTextView extends AbstractSearchView<String> {

	private final WTextField txtField = new WTextField();

	private final WSuggestions suggestions = new WSuggestions();

	public SearchTextView(final String viewId) {
		this(viewId, false);
	}

	public SearchTextView(final String viewId, final boolean mandatory) {
		super(viewId);

		WContainer content = getContent();

		WPanel panel = new WPanel();
		content.add(panel);

		WFieldLayout layout = new WFieldLayout();
		layout.setLabelWidth(30);
		panel.add(layout);

		txtField.setMandatory(mandatory);

		WContainer container = new WContainer();
		container.add(txtField);
		container.add(getSearchButton());
		getSearchButton().addHtmlClass("wc-margin-w-sm");

		layout.addField("Search", container);

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
		String value = txtField.getValue();
		setViewBean(value == null ? "" : value);
	}

	protected void doHandleSuggestions() {
		List<String> values = buildSuggestions(suggestions.getAjaxFilter());
		suggestions.setSuggestions(values);
	}

	protected List<String> buildSuggestions(final String filter) {
		return Collections.EMPTY_LIST;
	}

}
