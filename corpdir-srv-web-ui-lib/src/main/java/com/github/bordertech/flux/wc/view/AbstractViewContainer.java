package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.ViewContainer;

/**
 * Smart view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractViewContainer extends AbstractView implements ViewContainer {

	public AbstractViewContainer(final String templateName) {
		super(templateName);
	}

	@Override
	public boolean isQualifierContext() {
		return getComponentModel().qualifierContext;
	}

	@Override
	public void setQualifierContext(final boolean qualifierContext) {
		getOrCreateComponentModel().qualifierContext = qualifierContext;
	}

	@Override
	protected ViewContainerModel newComponentModel() {
		return new ViewContainerModel();
	}

	@Override
	protected ViewContainerModel getComponentModel() {
		return (ViewContainerModel) super.getComponentModel();
	}

	@Override
	protected ViewContainerModel getOrCreateComponentModel() {
		return (ViewContainerModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class ViewContainerModel extends ViewModel {

		private boolean qualifierContext;

	}
}
