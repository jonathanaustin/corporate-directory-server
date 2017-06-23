package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Headers;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WProgressBar;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
import com.github.bordertech.wcomponents.lib.view.DefaultBasicEventView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;
import com.github.bordertech.wcomponents.lib.view.ViewEvent;
import com.github.bordertech.wcomponents.lib.view.WDiv;

public class DefaultNavView extends DefaultBasicEventView implements NavView {

	private static final String NAV_FIRST_BUTTON_DISABLED_IMAGE = "/icons/first-button-disabled.png";
	private static final String NAV_FIRST_BUTTON_IMAGE = "/icons/first-button.png";
	private static final String NAV_NEXT_BUTTON_DISABLED_IMAGE = "/icons/next-button-disabled.png";
	private static final String NAV_NEXT_BUTTON_IMAGE = "/icons/next-button.png";
	private static final String NAV_PREV_BUTTON_DISABLED_IMAGE = "/icons/prev-button-disabled.png";
	private static final String NAV_PREV_BUTTON_IMAGE = "/icons/prev-button.png";
	private static final String NAV_LAST_BUTTON_DISABLED_IMAGE = "/icons/last-button-disabled.png";
	private static final String NAV_LAST_BUTTON_IMAGE = "/icons/last-button.png";

	/**
	 * Swipe config script.
	 */
	private final WText configScript = new WText();

	/**
	 * First button.
	 */
	private final WButton firstButton = new WButton("first") {
		@Override
		public boolean isDisabled() {
			return getCurrentIdx() == 0;
		}

		@Override
		protected void afterActionExecute(final Request request) {
			if (!isDisabled()) {
				focusMe();
			}
		}
	};

	/**
	 * Previous button.
	 */
	private final WButton prevButton = new WButton("prev") {
		@Override
		public boolean isDisabled() {
			return getCurrentIdx() == 0;
		}

		@Override
		protected void afterActionExecute(final Request request) {
			if (!isDisabled()) {
				focusMe();
			}
		}
	};

	/**
	 * Next button.
	 */
	private final WButton nextButton = new WButton("next") {
		@Override
		public boolean isDisabled() {
			return getCurrentIdx() == getSize() - 1;
		}

		@Override
		protected void afterActionExecute(final Request request) {
			if (!isDisabled()) {
				focusMe();
			}
		}
	};

	/**
	 * Last button.
	 */
	private final WButton lastButton = new WButton("last") {
		@Override
		public boolean isDisabled() {
			return getCurrentIdx() == getSize() - 1;
		}

		@Override
		protected void afterActionExecute(final Request request) {
			if (!isDisabled()) {
				focusMe();
			}
		}
	};

	/**
	 * First button AJAX control.
	 */
	private final WAjaxControl firstAjax = new WAjaxControl(firstButton) {
		@Override
		public boolean isVisible() {
			return !firstButton.isDisabled();
		}
	};

	/**
	 * Previous button AJAX control.
	 */
	private final WAjaxControl prevAjax = new WAjaxControl(prevButton) {
		@Override
		public boolean isVisible() {
			return !prevButton.isDisabled();
		}
	};

	/**
	 * Next button AJAX control.
	 */
	private final WAjaxControl nextAjax = new WAjaxControl(nextButton) {
		@Override
		public boolean isVisible() {
			return !nextButton.isDisabled();
		}
	};

	/**
	 * Last button AJAX control.
	 */
	private final WAjaxControl lastAjax = new WAjaxControl(lastButton) {
		@Override
		public boolean isVisible() {
			return !lastButton.isDisabled();
		}
	};

	public DefaultNavView() {

		WDiv viewHolder = getViewHolder();

		// Layout
		WPanel layout = new WPanel();
		layout.setLayout(new ColumnLayout(new int[]{33, 34, 33}, new ColumnLayout.Alignment[]{ColumnLayout.Alignment.LEFT, ColumnLayout.Alignment.CENTER, ColumnLayout.Alignment.RIGHT}));
		viewHolder.add(layout);

		// Add buttons
		WContainer col = new WContainer();
		layout.add(col);
		col.add(firstButton);
		col.add(prevButton);

		// Progress bar
		WProgressBar bar = new WProgressBar() {
			@Override
			public int getMax() {
				return getSize();
			}

			@Override
			public int getValue() {
				return getCurrentIdx() + 1;
			}
		};
		layout.add(bar);

		col = new WContainer();
		layout.add(col);
		col.add(nextButton);
		col.add(lastButton);

		col.add(firstAjax);
		col.add(prevAjax);
		col.add(nextAjax);
		col.add(lastAjax);

		// Swipe script
		configScript.setEncodeText(false);
		col.add(configScript);

		// Actions
		firstButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleFirst();
			}
		});

		prevButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandlePrevious();
			}
		});

		nextButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleNext();
			}
		});

		lastButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleLast();
			}
		});

		// IDs
		firstAjax.setIdName("ajaxFirst");
		prevAjax.setIdName("ajaxPrev");
		nextAjax.setIdName("ajaxNext");
		lastAjax.setIdName("ajaxlast");

		firstButton.setIdName("btnFirst");
		prevButton.setIdName("btnPrev");
		nextButton.setIdName("btnNext");
		lastButton.setIdName("btnLast");

		// Tooltips
		firstButton.setToolTip("first");
		prevButton.setToolTip("previous");
		nextButton.setToolTip("next");
		lastButton.setToolTip("last");

		// Access keys
		firstButton.setAccessKey('Z');
		prevButton.setAccessKey('X');
		nextButton.setAccessKey('C');
		lastButton.setAccessKey('V');

	}

	@Override
	public void registerViewAction(final ViewAction<NavView, NavEvent> viewAction, final NavEvent... viewEvent) {
		addViewAction(viewAction, viewEvent);
	}

	/**
	 * @return the number of rows
	 */
	@Override
	public int getSize() {
		return getComponentModel().size;
	}

	@Override
	public void setSize(final int size) {
		getOrCreateComponentModel().size = size;
	}

	/**
	 * @return the current index (zero based)
	 */
	@Override
	public int getCurrentIdx() {
		return getComponentModel().index;
	}

	@Override
	public void setCurrentIdx(final int idx) {
		getOrCreateComponentModel().index = idx;
	}

	/**
	 * @param useSwipe true if use swipe on touchscreen
	 */
	@Override
	public void setUseSwipe(final boolean useSwipe) {
		getOrCreateComponentModel().useSwipe = useSwipe;
	}

	/**
	 * @return true if use swipe on touchscreen
	 */
	@Override
	public boolean isUseSwipe() {
		return getComponentModel().useSwipe;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isVisible() {
		// Only visible if it has more than one row to navigate.
		return getSize() > 1;
	}

	@Override
	public void addEventAjaxTarget(AjaxTarget target, ViewEvent... viewEvent) {
		// Set AJAX targets
		firstAjax.addTarget(target);
		prevAjax.addTarget(target);
		nextAjax.addTarget(target);
		lastAjax.addTarget(target);
	}

	/**
	 * Add AJAX target to actions.
	 *
	 * @param target AJAX target for action buttons
	 */
	public void addAjaxTarget(final AjaxTarget target) {
		firstAjax.addTarget(target);
		prevAjax.addTarget(target);
		nextAjax.addTarget(target);
		lastAjax.addTarget(target);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		configButtons();
		// Config swipe
		if (isUseSwipe()) {
			setupSwipe();
		}
	}

	protected void configButtons() {
		// Set Icons
		firstButton.setImage(firstButton.isDisabled() ? NAV_FIRST_BUTTON_DISABLED_IMAGE
				: NAV_FIRST_BUTTON_IMAGE);
		prevButton.setImage(prevButton.isDisabled() ? NAV_PREV_BUTTON_DISABLED_IMAGE
				: NAV_PREV_BUTTON_IMAGE);
		nextButton.setImage(nextButton.isDisabled() ? NAV_NEXT_BUTTON_DISABLED_IMAGE
				: NAV_NEXT_BUTTON_IMAGE);
		lastButton.setImage(lastButton.isDisabled() ? NAV_LAST_BUTTON_DISABLED_IMAGE
				: NAV_LAST_BUTTON_IMAGE);

	}

	/**
	 * Setup swipe for touchscreen.
	 */
	protected void setupSwipe() {
		// Config script
		String swipeUrl = WebUtilities.encode("js/clientSwipe.js");
		String containerId = firstAjax.getTargets().get(0).getId();

		StringBuilder script = new StringBuilder();
		script.append("<script>");
		script.append("require(['").append(swipeUrl).append("'], function(clientSwipe){clientSwipe.setConfig(");
		script.append("'").append(containerId).append("'");
		script.append(",'").append(firstButton.getId()).append("'");
		script.append(",'").append(prevButton.getId()).append("'");
		script.append(",'").append(nextButton.getId()).append("'");
		script.append(",'").append(lastButton.getId()).append("'");
		script.append(")})");
		script.append("</script>");

		// Config script
		configScript.setText(script.toString());

		Headers headers = UIContextHolder.getCurrent().getHeaders();

		// Javascript
		headers.addUniqueHeadLine(Headers.JAVASCRIPT_HEADLINE,
				"requirejs.config({map:{'*':{'Hammer':'js/hammer.js'}}});");

		// CSS
		headers.addUniqueHeadLine("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/clientSwipe.css\"/>");
	}

	/**
	 * Load the first row.
	 */
	protected void doHandleFirst() {
		setCurrentIdx(0);
		nextButton.setFocussed();
		handleIndexChanged(NavEvent.FIRST);
	}

	/**
	 * Load the previous row.
	 */
	protected void doHandlePrevious() {
		int idx = getCurrentIdx();
		idx--;
		setCurrentIdx(idx);
		if (prevButton.isDisabled()) {
			nextButton.setFocussed();
		}
		handleIndexChanged(NavEvent.PREV);
	}

	/**
	 * Load the next row.
	 */
	protected void doHandleNext() {
		int idx = getCurrentIdx();
		idx++;
		setCurrentIdx(idx);
		if (nextButton.isDisabled()) {
			prevButton.setFocussed();
		}
		handleIndexChanged(NavEvent.NEXT);
	}

	/**
	 * Load the last row.
	 */
	protected void doHandleLast() {
		int idx = getSize() - 1;
		setCurrentIdx(idx);
		prevButton.setFocussed();
		handleIndexChanged(NavEvent.LAST);
	}

	/**
	 * Handle the index change.
	 *
	 * @param navEvent the navigation action that caused the change of index
	 */
	protected void handleIndexChanged(final NavEvent navEvent) {
		executeRegisteredViewActions(navEvent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected NavigationPanelModel newComponentModel() {
		return new NavigationPanelModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected NavigationPanelModel getOrCreateComponentModel() {
		return (NavigationPanelModel) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected NavigationPanelModel getComponentModel() {
		return (NavigationPanelModel) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 */
	public static final class NavigationPanelModel extends EventViewModel {

		/**
		 * Current index (zero based).
		 */
		private int index;
		/**
		 * Number of rows.
		 */
		private int size;
		/**
		 * Use swipe on touchscreen.
		 */
		private boolean useSwipe;
	}
}
