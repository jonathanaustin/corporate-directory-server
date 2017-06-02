package com.github.bordertech.corpdir.web.ui.common;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WDefinitionList;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;

/**
 * Abstract edit view.
 *
 * @author Jonathan Austin
 * @param <T>
 * @since 1.0.0
 */
public class BasicDetailPanel<T extends ApiKeyIdObject> extends WPanel {

	private final WPanel formPanel = new WPanel();

	private final WFieldLayout formLayout = new WFieldLayout();

	private final WPanel versionPanel = new WPanel() {
		@Override
		public boolean isVisible() {
			return isExistingRecord();
		}
	};

	/**
	 * Construct basic detail panel.
	 */
	public BasicDetailPanel() {
		add(formPanel);
		add(versionPanel);

		setupFormDefaults();
		setupVersionDetails();
	}

	/**
	 * @return the panel that hold the form details
	 */
	protected WPanel getFormPanel() {
		return formPanel;
	}

	/**
	 * @return the field layout holding the basic input fields
	 */
	protected WFieldLayout getFormLayout() {
		return formLayout;
	}

	/**
	 * @return the panel that holds the record id and version details
	 */
	protected WPanel getVersionPanel() {
		return versionPanel;
	}

	/**
	 * @return true if the bean has id details
	 */
	protected boolean isExistingRecord() {
		T bean = getApiBean();
		return bean != null && bean.getId() != null;
	}

	/**
	 * @param apiBean the bean to be displayed
	 */
	public void setApiBean(final T apiBean) {
		setBean(apiBean);
	}

	/**
	 * @return the bean being displayed.
	 */
	public T getApiBean() {
		return (T) getBean();
	}

	/**
	 * Setup the form input details.
	 */
	private void setupFormDefaults() {
		WFieldLayout layout = getFormLayout();

		WPanel panel = getFormPanel();
		panel.add(layout);

		// Business Key
		WTextField txtBusKey = new WTextField();
		txtBusKey.setMandatory(true);
		txtBusKey.setBeanProperty("businessKey");
		layout.addField("Business key", txtBusKey);

		// Description
		WTextField txtDesc = new WTextField();
		txtDesc.setMandatory(true);
		txtDesc.setBeanProperty("description");
		layout.addField("Description", txtDesc);

		// Active
		WCheckBox chbActive = new WCheckBox();
		chbActive.setBeanProperty("active");
		layout.addField("Active", chbActive);
	}

	/**
	 * Setup the record id and version details.
	 */
	private void setupVersionDetails() {

		WPanel panel = getVersionPanel();

		WDefinitionList def = new WDefinitionList();
		panel.add(def);

		// Record Details
		// Custom
		WText txtCustom = new WText();
		txtCustom.setBeanProperty("custom");
		def.addTerm("Custom", txtCustom);

		// Version
		WText txtVersion = new WText();
		txtVersion.setBeanProperty("version");
		def.addTerm("Version", txtVersion);

		// ID
		WText txtId = new WText();
		txtId.setBeanProperty("id");
		def.addTerm("ID", txtId);
	}

	/**
	 * @return true if form read only
	 */
	public boolean isFormReadOnly() {
		return getComponentModel().formReadOnly;
	}

	/**
	 *
	 * @param formReadOnly true if form read only
	 */
	public void setFormReadOnly(final boolean formReadOnly) {
		BasicDetailModel model = getComponentModel();
		boolean changed = model.formReadOnly == formReadOnly;
		if (changed) {
			getOrCreateComponentModel().formReadOnly = formReadOnly;
			handleReadOnlyState();
		}
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			initForm();
			setInitialised(true);
		}
	}

	protected void initForm() {
		handleReadOnlyState();
	}

	protected void handleReadOnlyState() {
		doMakeReadOnly(getFormPanel(), isFormReadOnly());
	}

	protected void doMakeReadOnly(final WComponent component, final boolean readOnly) {
		if (component instanceof Input) {
			((Input) component).setReadOnly(readOnly);
		}

		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				doMakeReadOnly(child, readOnly);
			}
		}
	}

	@Override
	protected BasicDetailModel newComponentModel() {
		return new BasicDetailModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BasicDetailModel getComponentModel() {
		return (BasicDetailModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BasicDetailModel getOrCreateComponentModel() {
		return (BasicDetailModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class BasicDetailModel extends PanelModel {

		private boolean formReadOnly = true;
	}
}
