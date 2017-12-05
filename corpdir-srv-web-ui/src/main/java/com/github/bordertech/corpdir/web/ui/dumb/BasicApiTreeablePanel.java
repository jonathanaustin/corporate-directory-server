package com.github.bordertech.corpdir.web.ui.dumb;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.List;
import java.util.Objects;

/**
 * Treeable panel.
 *
 * @author Jonathan Austin
 * @param <T> the treeable entity type
 * @since 1.0.0
 */
public class BasicApiTreeablePanel<T extends ApiTreeable> extends BasicApiKeyPanel<T> {

	private final PollingDropdownOptionsView<String, T> drpParent = new PollingDropdownOptionsView<>("PAR");
	private final PollingMultiSelectPairOptionsView<String, T> multiSub = new PollingMultiSelectPairOptionsView<>("SUB");

	private final String desc;

	/**
	 * Construct basic tree panel.
	 *
	 * @param desc the entity description
	 * @param viewId the viewId
	 */
	public BasicApiTreeablePanel(final String desc, final String viewId) {
		super(viewId);
		this.desc = desc;

		// Parent
		WLabel lbl = new WLabel("Parent " + desc, drpParent.getSelectInput());
		getFormLayout().addField(lbl, drpParent);
		drpParent.setIncludeNullOption(true);
		drpParent.setCodeProperty("id");
		drpParent.getOptionsView().setBeanProperty("parentId");
		drpParent.setStoreKey(DataApiType.LOCATION.getSearchStoreKey());

		// Sub Items
		lbl = new WLabel("Sub " + desc, multiSub.getSelectInput());
		getFormLayout().addField(lbl, multiSub);
		multiSub.setCodeProperty("id");
		multiSub.getOptionsView().setBeanProperty("subIds");
		multiSub.setStoreKey(DataApiType.LOCATION.getSearchStoreKey());

		// FIXME: Temporary delays as firing extra AJAX Trigger
		drpParent.getPollingView().setPollingInterval(50);
		multiSub.getPollingView().setPollingInterval(75);
	}

	public PollingDropdownOptionsView<String, T> getDrpParent() {
		return drpParent;
	}

	public PollingMultiSelectPairOptionsView<String, T> getMultiSub() {
		return multiSub;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	protected void validateComponent(final List<Diagnostic> diags) {
		super.validateComponent(diags);
		T current = getViewBean();

		// Check parent
		T parent = getDrpParent().getSelectedOption();
		if (Objects.equals(current, parent)) {
			diags.add(createErrorDiagnostic(getDrpParent().getSelectInput(), "Cannot make the " + getDesc() + " its own parent."));
		}

		// Check Sub
		List<T> subs = getMultiSub().getSelectedOptions();
		if (subs != null && subs.contains(current)) {
			diags.add(createErrorDiagnostic(getMultiSub().getSelectInput(), "Cannot add the " + getDesc() + " to itself."));
		}
		if (subs != null && parent != null && subs.contains(parent)) {
			diags.add(createErrorDiagnostic(getMultiSub().getSelectInput(), "Cannot have the same " + getDesc() + " as a child and parent."));
		}
	}

}
