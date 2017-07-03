package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanAndProviderBoundComponentModel;
import com.github.bordertech.wcomponents.RenderContext;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.XmlStringBuilder;
import com.github.bordertech.wcomponents.servlet.WebXmlRenderContext;

/**
 * A DIV that can be an AJAX Target and Subordinate Target.
 *
 * @author jonathan
 */
public class WDiv extends WContainer implements AjaxTarget, SubordinateTarget {

	public enum DropMode {
		COPY,
		MOVE,
		LINK;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public enum DragMode {
		TRUE,
		FALSE,
		AUTO;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	@Override
	protected void beforePaint(final RenderContext renderContext) {
		XmlStringBuilder xml = ((WebXmlRenderContext) renderContext).getWriter();

		xml.appendTagOpen("div");
		xml.appendAttribute("id", getId());
		xml.appendOptionalAttribute("class", getHtmlClass());
		xml.appendOptionalAttribute("hidden", isHidden(), "true");
		xml.appendOptionalAttribute("dropzone", getDropMode());
		xml.appendOptionalAttribute("draggable", getDragMode());
		xml.appendClose();
	}

	public void setDragMode(final DragMode mode) {
		setAttribute("dragmode", mode);
	}

	public DragMode getDragMode() {
		return (DragMode) getAttribute("dragmode");
	}

	public void setDropMode(final DropMode mode) {
		setAttribute("dropmode", mode);
	}

	public DropMode getDropMode() {
		return (DropMode) getAttribute("dropmode");
	}

	@Override
	protected void afterPaint(final RenderContext renderContext) {
		XmlStringBuilder xml = ((WebXmlRenderContext) renderContext).getWriter();
		xml.appendEndTag("div");
	}

	@Override
	protected DivModel newComponentModel() {
		return new DivModel();
	}

	@Override
	protected DivModel getComponentModel() {
		return (DivModel) super.getComponentModel();
	}

	@Override
	protected DivModel getOrCreateComponentModel() {
		return (DivModel) super.getOrCreateComponentModel();
	}

	public static class DivModel extends BeanAndProviderBoundComponentModel {
	}
}
