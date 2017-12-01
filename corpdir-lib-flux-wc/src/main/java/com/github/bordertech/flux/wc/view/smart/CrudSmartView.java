package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.wc.view.smart.consumer.SearchStoreConsumer;

/**
 * Crud SMART view with a Search Store.
 *
 * @param <T> the form entity type
 * @author jonathan
 */
public interface CrudSmartView<S, T> extends FormSmartView<T>, SearchStoreConsumer<S, T> {
}
