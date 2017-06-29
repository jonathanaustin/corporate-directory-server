package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTimeoutWarning;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.grid.Grid;
import com.github.bordertech.wcomponents.lib.grid.GridItem;
import com.github.bordertech.wcomponents.lib.resource.ApplicationResourceWContent;
import com.github.bordertech.wcomponents.lib.resource.RegisterWcLibJsResource;
import com.github.bordertech.wcomponents.lib.resource.TemplateWContent;
import java.util.Date;

/**
 * Corporate Directory Admin UI.
 *
 * @author jonathan
 */
public class CorpDirApp extends WApplication implements MessageContainer {

	/**
	 * Messages.
	 */
	private final WMessages messages = new WMessages();

	/**
	 * Card manager.
	 */
	private final WCardManager mgr = new WCardManager();

	/**
	 * Construct Application.
	 */
	public CorpDirApp() {

		// Custom css
		addCssFile("/css/app.css");
		addCssUrl("wclib2/css/grid-5-cols.css");

		// Custom JS
		TemplateWContent registerWclib = new TemplateWContent(new RegisterWcLibJsResource("/wclib2"), "reg");
		add(registerWclib);
		addJsResource(new ApplicationResourceWContent(registerWclib, "regkey"));

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Corporate Directory"));

		// Detail
		WPanel detail = new WPanel();
		detail.setMargin(new Margin(Size.LARGE));
		add(detail);

		// Messages
		detail.add(messages);

		// Card manager
		detail.add(mgr);

		// Cards
		// Dummy Card
//		mgr.add(new WSection("Hello World"));
		Grid grid = new Grid();
		GridItem gridItem = new GridItem(2);
		gridItem.getContentHolder().add(new WText("ITEM 1"));
		grid.getItemsContainer().add(gridItem);

		gridItem = new GridItem(3);
		gridItem.getContentHolder().add(new WText("ITEM 2"));
		grid.getItemsContainer().add(gridItem);

		mgr.add(grid);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WText(new Date().toString()));

		add(new WTimeoutWarning());

		// IDs
		header.setIdName("hdr");
		messages.setIdName("msgs");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return "Corp Dir - " + getCurrentTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleStepError() {
		super.handleStepError();
		getMessages()
				.warn("A request was made that is not in the expected sequence and the application has been refreshed to its current state.");
	}

	/**
	 * @return the title of the current card
	 */
	private String getCurrentTitle() {
		return "Title";
//		return ((WSection) mgr.getVisible()).getDecoratedLabel().getText();
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
