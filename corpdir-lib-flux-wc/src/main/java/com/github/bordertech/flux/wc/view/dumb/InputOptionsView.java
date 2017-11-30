package com.github.bordertech.flux.wc.view.dumb;

import com.github.bordertech.flux.wc.view.dumb.form.FormUpdateable;
import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.wcomponents.AbstractWSelectList;
import java.util.List;

/**
 * Form input from options view. The view bean is the input value.
 *
 * @param <T> the option type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface InputOptionsView<T> extends FluxDumbView<T>, FormUpdateable {

	void setOptions(final List<T> options);

	List<T> getOptions();

	int getSize();

	AbstractWSelectList getSelectInput();

	void showView(final boolean show);

	void setCodeProperty(final String codeProperty);

	String getCodeProperty();

	void setIncludeNullOption(final boolean includeNullOption);

	boolean isIncludeNullOption();

}
