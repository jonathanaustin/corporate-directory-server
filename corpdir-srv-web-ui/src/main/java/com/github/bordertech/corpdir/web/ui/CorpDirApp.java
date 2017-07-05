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
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.lib.grid.Grid;
import com.github.bordertech.wcomponents.lib.resource.ApplicationResourceWContent;
import com.github.bordertech.wcomponents.lib.resource.RegisterWcLibJsResource;
import com.github.bordertech.wcomponents.lib.resource.TemplateWContent;
import com.github.bordertech.wcomponents.lib.view.WDiv;
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
//		addCssUrl("wclib/css/grid-msry-cols.css");
		addCssUrl("wclib/css/grid-css-cols.css");

		// Custom JS
		TemplateWContent registerWclib = new TemplateWContent(new RegisterWcLibJsResource(), "reg");
		add(registerWclib);
		addJsResource(new ApplicationResourceWContent(registerWclib, "regkey"));

		addJsUrl("wclib/js/lib/interact-1.2.6.js");
//		addJsUrl("wclib/js/wc-grid-drag-drop.js");

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
//		WDiv grid = new WDiv();
		grid.setIdName("mygrid");
		grid.setHtmlClass("grid-css");
		mgr.add(grid);
		for (int i = 1; i < 25; i++) {
			WDiv div = new WDiv();
			div.setDragMode(WDiv.DragMode.TRUE);
//			div.setDropMode(WDiv.DropMode.MOVE);
			div.setHtmlClass("drag");
			if (i % 2 == 0) {
				div.addHtmlClass("grid-column-2");
			}

			grid.getItemsContainer().add(div);
			WPanel panel = new WPanel(WPanel.Type.BOX);
			panel.setLayout(new FlowLayout(FlowLayout.Alignment.VERTICAL));
			panel.add(new WText("ITEM " + i));
			div.add(panel);
			if (i % 5 == 0) {
				div.addHtmlClass("grid-column-2");
				panel.add(new WText("ITEM A" + i));
				panel.add(new WText("ITEM B" + i));
				panel.add(new WText("ITEM C" + i));
				panel.add(new WText("ITEM D" + i));
				panel.add(new WText("ITEM E" + i));
			}
		}

//		GridItem gridItem = new GridItem(grid, 2);
//		gridItem.getContentHolder().add(panel);
//		grid.getItemsContainer().add(gridItem);
//
//		gridItem = new GridItem(grid, 3);
//		panel = new WPanel(WPanel.Type.BOX);
//		panel.setMargin(new Margin(Size.SMALL));
//		panel.add(new WText("ITEM 2"));
//		gridItem.getContentHolder().add(panel);
//		grid.getItemsContainer().add(gridItem);
//
//		grid.addHtmlClass("mygrid");
//		WDiv grid = new WDiv();
//		grid.setHtmlClass("grid-border");
//		mgr.add(grid);
//		// North
//		WPanel panel = new WPanel(WPanel.Type.BOX);
//		panel.add(new WText("NORTH"));
//		panel.setHtmlClass("north");
//		grid.add(panel);
//		// South
//		panel = new WPanel(WPanel.Type.BOX);
//		panel.add(new WText("SOUTH"));
//		panel.setHtmlClass("south");
//		grid.add(panel);
//		// East
//		panel = new WPanel(WPanel.Type.BOX);
//		panel.add(new WText("EAST"));
//		panel.setHtmlClass("east");
//		grid.add(panel);
//		// West
//		panel = new WPanel(WPanel.Type.BOX);
//		panel.add(new WText("WEST"));
//		panel.setHtmlClass("west");
//		grid.add(panel);
//		// Center
//		panel = new WPanel(WPanel.Type.BOX);
//		panel.add(new WText("CENTER"));
//		panel.setHtmlClass("center");
//		grid.add(panel);
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
