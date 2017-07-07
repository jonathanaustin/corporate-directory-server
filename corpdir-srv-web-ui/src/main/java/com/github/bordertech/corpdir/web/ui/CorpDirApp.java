package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTimeoutWarning;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.layout.FlowLayout;
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
		addCssUrl("wclib/css/grid-flex.css");
//		addCssUrl("wclib/css/grid-cols.css");
//		addCssUrl("wclib/css/grid-msry.css");
//		addCssUrl("wclib/css/grid-css.css");

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

		// Dummy Card
//		mgr.add(new WSection("Hello World"));
		// Cards
		WPanel dummy = new WPanel();
		mgr.add(dummy);

		// CSS x5
		dummy.add(buildFlexGrid3());

//		dummy.add(buildColsGrid());
//		dummy.add(new WHeading(HeadingLevel.H1, "Default - CSS"));
//		dummy.add(buildCssGridCols12());
//		dummy.add(buildMasonryGrid());
//		dummy.add(buildCssGrid());
//		dummy.add(buildBorderGrid());
		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WText(new Date().toString()));

		add(new WTimeoutWarning());

		// IDs
		header.setIdName("hdr");
		messages.setIdName("msgs");
	}

	private Grid buildFlexGrid() {
		Grid grid = new Grid();
		grid.setTemplateName("/wclib/hbs/grid-flex.hbs");
		grid.setItemTemplateName("/wclib/hbs/grid-flex-item.hbs");
		buildItems(grid);
		return grid;
	}

	private Grid buildFlexGrid3() {
		Grid grid = new Grid();
		grid.setTemplateName("/wclib/hbs/grid-flex.hbs");
		grid.setItemTemplateName("/wclib/hbs/grid-flex-item.hbs");
		grid.setMaxColumns(3);

		WContainer holder = grid.getItemsContainer();
		for (int i = 1; i < 4; i++) {
			WPanel panel = new WPanel(WPanel.Type.BOX);
			panel.add(new WText("COL " + i));

			GridItem item = new GridItem(grid);
			item.getContentHolder().add(panel);
			holder.add(item);
		}

		return grid;
	}

	private Grid buildColsGrid() {
		Grid grid = new Grid();
		grid.setTemplateName("/wclib/hbs/grid-cols.hbs");
		grid.setItemTemplateName("/wclib/hbs/grid-cols-item.hbs");
		buildItems(grid);
		return grid;
	}

	private Grid buildCssGrid() {
		Grid grid = new Grid();
		buildItems(grid);
		return grid;
	}

	private Grid buildCssGridCols12() {
		Grid grid = new Grid();
		grid.setHtmlClass("columns-12");
		buildItems(grid);
		return grid;
	}

	private Grid buildMasonryGrid() {
		Grid grid = new Grid();
		grid.setMaxColumns(12);
		grid.setTemplateName("/wclib/hbs/grid-msry.hbs");
		grid.setItemTemplateName("/wclib/hbs/grid-msry-item.hbs");
		buildItems(grid);
		return grid;
	}

	private Grid buildBorderGrid() {
		Grid grid = new Grid();
		grid.setHtmlClass("border");

		WContainer holder = grid.getItemsContainer();

		// North
		WPanel panel = new WPanel(WPanel.Type.BOX);
		panel.add(new WText("NORTH"));
		panel.setHtmlClass("north");
		holder.add(panel);
		// South
		panel = new WPanel(WPanel.Type.BOX);
		panel.add(new WText("SOUTH"));
		panel.setHtmlClass("south");
		holder.add(panel);
		// East
		panel = new WPanel(WPanel.Type.BOX);
		panel.add(new WText("EAST"));
		panel.setHtmlClass("east");
		holder.add(panel);
		// West
		panel = new WPanel(WPanel.Type.BOX);
		panel.add(new WText("WEST"));
		panel.setHtmlClass("west");
		holder.add(panel);
		// Center
		panel = new WPanel(WPanel.Type.BOX);
		panel.add(new WText("CENTER"));
		panel.setHtmlClass("center");
		holder.add(panel);

		// Nested
		panel.add(buildCssGrid());

		return grid;
	}

	private void buildItems(final Grid grid) {

		WContainer holder = grid.getItemsContainer();

		for (int i = 1; i < 25; i++) {

			GridItem item = new GridItem(grid);
			holder.add(item);

			WPanel panel = new WPanel(WPanel.Type.BOX);
			panel.setLayout(new FlowLayout(FlowLayout.Alignment.VERTICAL));
			panel.add(new WText("ITEM " + i));
			item.getContentHolder().add(panel);

			if (i % 2 == 0) {
				item.setSpans(2);
			} else if (i % 5 == 0) {
				item.setSpans(5);
				panel.add(new WText("ITEM A" + i));
				panel.add(new WText("ITEM B" + i));
				panel.add(new WText("ITEM C" + i));
				panel.add(new WText("ITEM D" + i));
				panel.add(new WText("ITEM E" + i));
			}
		}
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
