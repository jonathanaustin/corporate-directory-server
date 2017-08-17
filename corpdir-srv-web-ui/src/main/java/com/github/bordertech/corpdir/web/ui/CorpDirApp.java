package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.view.BasicEntityPanel;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTimeoutWarning;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.app.impl.BasicCriteriaWithListView;
import com.github.bordertech.wcomponents.lib.app.impl.BasicEntityWithActionView;
import com.github.bordertech.wcomponents.lib.app.impl.BasicSelectListView;
import com.github.bordertech.wcomponents.lib.app.type.ActionEventType;
import com.github.bordertech.wcomponents.lib.app.view.ListView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.impl.BasicView;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultDispatcher;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	private final DefaultDispatcher dispatcher = new DefaultDispatcher();

	/**
	 * Construct Application.
	 */
	public CorpDirApp() {

		add(dispatcher);

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

		WButton button = new WButton("Reset");
		button.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				dispatcher.dispatch(new Event(ActionEventType.RESET));
			}
		});

		// View 1
		BasicView view = new BasicCriteriaWithListView<String>(dispatcher) {
			@Override
			protected List<String> doSearchServiceCall(final String criteria) {
				return myStringServiceCall(criteria);
			}
		};

		// View 2
		BasicEntityWithActionView<OrgUnit> view2 = new BasicEntityWithActionView<OrgUnit>(dispatcher) {
			@Override
			public OrgUnit doService(final ActionEventType type, final OrgUnit bean) {
				return myEntityService(type, bean);
			}
		};
		view2.getEntityView().getContent().add(new BasicEntityPanel());

		// View 3
		ListView listView = new BasicSelectListView(dispatcher, "X");
		BasicView view3 = new BasicCriteriaWithListView<OrgUnit>(dispatcher, "X", listView) {
			@Override
			protected List<OrgUnit> doSearchServiceCall(final String criteria) {
				return mySearchServiceCall(criteria);
			}
		};

		DefaultController ctrl = new DefaultController(dispatcher);
		ctrl.addView(view);
		ctrl.addView(view2);
		ctrl.addView(view3);

		WDiv div = new WDiv();
		div.add(ctrl);
		div.add(button);
		div.add(wrapInSection((WDiv) view, "View 1"));
		div.add(wrapInSection((WDiv) view2, "View 2"));
		div.add(wrapInSection((WDiv) view3, "View 3"));
		mgr.add(div);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WText(new Date().toString()));

		add(new WTimeoutWarning());

		// IDs
		header.setIdName("hdr");
		messages.setIdName("msgs");
	}

	protected final WSection wrapInSection(final WDiv div, final String title) {
		WSection section = new WSection(title);
		section.getContent().add(div);
		section.setMargin(new Margin(Size.XL));
		return section;
	}

	protected List<OrgUnit> mySearchServiceCall(final String criteria) {
		if ("error".equals(criteria)) {
			throw new SystemException("Big error");
		}
		try {
			Thread.sleep(3000);
		} catch (Exception e) {

		}
		if ("error2".equals(criteria)) {
			throw new SystemException("Big error2");
		}
		List<OrgUnit> items = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			OrgUnit item = new OrgUnit();
			item.setBusinessKey("A" + i);
			item.setDescription("DESC" + i);
			item.setId("A" + i);
			items.add(item);
		}
		return items;
	}

	protected List<String> myStringServiceCall(final String criteria) {
		if ("error".equals(criteria)) {
			throw new SystemException("Big error");
		}
		try {
			Thread.sleep(3000);
		} catch (Exception e) {

		}
		if ("error2".equals(criteria)) {
			throw new SystemException("Big error2");
		}
		List<String> items = new ArrayList<>();
		items.add("A1");
		items.add("A2");
		items.add("A3");
		items.add("A4");
		items.add("A5");
		return items;
	}

	public OrgUnit myEntityService(final ActionEventType type, final OrgUnit bean) {
		switch (type) {
			case ADD:
				return new OrgUnit();
			default:
				bean.setDescription("Been in update:" + new Date().toString());
				return bean;
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
