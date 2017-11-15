package com.github.bordertech.flux.wc.app.view;

import com.github.bordertech.flux.wc.app.view.form.FormUpdateable;
import com.github.bordertech.flux.wc.view.DumbView;

/**
 * Form input via search view. The view bean is the input value.
 *
 * @param <T> the option type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface InputViaSearchView<T> extends DumbView<T>, FormUpdateable {

}
