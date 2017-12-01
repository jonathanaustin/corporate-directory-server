package com.github.bordertech.flux.wc.common;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.util.MemoryUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom WAjaxControl to only be visible if its trigger is visible.
 *
 * @author jonathan
 */
public class AppAjaxControl extends WAjaxControl {

	public AppAjaxControl(final AjaxTrigger trigger) {
		super(trigger);
	}

	public AppAjaxControl(final AjaxTrigger trigger, final AjaxTarget target) {
		super(trigger);
		addTarget(target);
	}

	@Override
	public boolean isVisible() {
		return getTrigger() == null || getTrigger().isVisible();
	}

	@Override
	public void addTarget(final AjaxTarget target) {
		if (target != null && !getTargets().contains(target)) {
			AppAjaxControlModel model = getOrCreateComponentModel();
			if (model.targets == null) {
				model.targets = new ArrayList<>();
			}
			model.targets.add(target);
			MemoryUtil.checkSize(model.targets.size(), this.getClass().getSimpleName());
		}
	}

	@Override
	public List<AjaxTarget> getTargets() {
		List<AjaxTarget> targets = getComponentModel().targets;
		return targets == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(targets);
	}

	public void removeTarget(final AjaxTarget target) {
		AppAjaxControlModel model = getOrCreateComponentModel();
		if (model.targets != null) {
			model.targets.remove(target);
			if (model.targets.isEmpty()) {
				model.targets = null;
			}
		}
	}

	public void removeAllTargets() {
		AppAjaxControlModel model = getOrCreateComponentModel();
		model.targets = null;
	}

	@Override
	protected AppAjaxControlModel newComponentModel() {
		return new AppAjaxControlModel();
	}

	@Override // For type safety only
	protected AppAjaxControlModel getComponentModel() {
		return (AppAjaxControlModel) super.getComponentModel();
	}

	@Override // For type safety only
	protected AppAjaxControlModel getOrCreateComponentModel() {
		return (AppAjaxControlModel) super.getOrCreateComponentModel();
	}

	public static class AppAjaxControlModel extends AjaxControlModel {

		private List<AjaxTarget> targets;
	}
}
