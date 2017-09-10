package com.github.bordertech.wcomponents.lib.app.toolbar;

import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;

/**
 * Entity form toolbar implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultFormToolbarView<T> extends DefaultToolbarView<T> implements FormToolbarView<T> {

	private final WMenuItem itemEdit = new ToolbarMenuItem("Edit", ToolbarEventType.EDIT) {
		@Override
		public boolean isVisible() {
			return isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return !FormMode.VIEW.equals(mode);
		}
	};

	private final WMenuItem itemCancel = new ToolbarMenuItem("Cancel", ToolbarEventType.CANCEL) {
		@Override
		public boolean isVisible() {
			return isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return FormMode.VIEW.equals(mode);
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemUpdate = new ToolbarMenuItem("Save", ToolbarEventType.UPDATE) {
		@Override
		public boolean isVisible() {
			return getFormMode() == FormMode.EDIT;
		}
	};

	private final WMenuItem itemCreate = new ToolbarMenuItem("Save", ToolbarEventType.CREATE) {
		@Override
		public boolean isVisible() {
			return getFormMode() == FormMode.ADD;
		}
	};

	private final WMenuItem itemDelete = new ToolbarMenuItem("Delete", ToolbarEventType.DELETE) {
		@Override
		public boolean isVisible() {
			return isFormReady() && !isDisabled();
		}

		@Override
		public boolean isDisabled() {
			FormMode mode = getFormMode();
			return FormMode.ADD == mode || FormMode.EDIT == mode;
		}
	};

	public DefaultFormToolbarView() {
		WMenu menu = getMenu();
		menu.add(itemEdit);
		menu.add(itemUpdate);
		menu.add(itemCreate);
		menu.add(itemCancel);
		menu.add(itemDelete);

		setUseAdd(false);
		setUseReset(false);
	}

	@Override
	public void setFormMode(final FormMode mode) {
		getOrCreateComponentModel().entityMode = mode == null ? FormMode.VIEW : mode;
	}

	@Override
	public FormMode getFormMode() {
		return getComponentModel().entityMode;
	}

	@Override
	public boolean isFormReady() {
		return getComponentModel().entityLoaded;
	}

	@Override
	public void setFormReady(final boolean entityLoaded) {
		getOrCreateComponentModel().entityLoaded = entityLoaded;
	}

	@Override
	protected FormToolbarModel newComponentModel() {
		return new FormToolbarModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FormToolbarModel getComponentModel() {
		return (FormToolbarModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FormToolbarModel getOrCreateComponentModel() {
		return (FormToolbarModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class FormToolbarModel extends ToolbarModel {

		private FormMode entityMode = FormMode.VIEW;

		private boolean entityLoaded;

	}

}
