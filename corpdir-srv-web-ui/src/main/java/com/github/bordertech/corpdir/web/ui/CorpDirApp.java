package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.web.ui.combo.MainComboView;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.util.ConfigLibUtil;

/**
 * Corporate Directory Admin UI.
 *
 * @author jonathan
 */
public class CorpDirApp extends WApplication {

	private final MainComboView mainView = new MainComboView();

	/**
	 * Construct Application.
	 */
	public CorpDirApp() {
		// Custom css
		addCssFile("/css/app.css");

		// Add wclib Setup
		ConfigLibUtil.configApplication(this);

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Corporate Directory"));

		add(mainView);
		mainView.setQualifierAndMessageQualifier("MN");
		mainView.setQualifierAndMessageQualifierContext(true);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WLink("icon source", "https://icons8.com"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return "Corp Dir - " + mainView.getCardTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleStepError() {
		super.handleStepError();
		mainView.getMessageView().getMessages().warn("A request was made that is not in the expected sequence and the application has been refreshed to its current state.");
	}

	/**
	 * Retrieves the BrisApp ancestor of the given component.
	 *
	 * @param descendant the component to find the BrisApp ancestor of.
	 * @return the BrisApp ancestor for the given component, or null if not found.
	 */
	public static CorpDirApp getInstance(final WComponent descendant) {
		return WebUtilities.getAncestorOfClass(CorpDirApp.class, descendant);
	}

}
