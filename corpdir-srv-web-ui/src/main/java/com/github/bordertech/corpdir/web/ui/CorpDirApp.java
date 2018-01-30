package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.web.ui.smart.main.MainAppView;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WDefinitionList;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.common.WLibLink;
import com.github.bordertech.wcomponents.lib.config.ConfigLibUtil;
import com.github.bordertech.wcomponents.lib.servlet.EnvironmentHelper;

/**
 * Corporate Directory Admin UI.
 *
 * @author jonathan
 */
public class CorpDirApp extends WApplication {

	private final MainAppView mainView = new MainAppView();

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

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		// ICONS8
		WLink icons8 = new WLink("icon source", "https://icons8.com");

		// Swagger link
		WLibLink swagger = new WLibLink("swagger api", "swagger-ui/index.html?url=") {
			@Override
			public String getUrl() {
				String url = super.getUrl();
				String suffix = EnvironmentHelper.prefixBaseUrl("api/swagger");
				return url + suffix;
			}
		};
		swagger.setRelativeBaseUrl(true);

		// Github
		WLibLink github = new WLibLink("github", "https://github.com/BorderTech/corporate-directory-server");
//		github.setImageUrl("wclib/icons/github/GitHub-Mark-32px.png");
//		github.setImageRelativeBaseUrl(true);

		WDefinitionList links = new WDefinitionList(WDefinitionList.Type.FLAT);
		links.addTerm("info", icons8, swagger, github);
		footer.add(links);
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
