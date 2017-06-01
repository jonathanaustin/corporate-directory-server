package com.github.bordertech.corpdir.web.ui.polling;

/**
 * Service result holder to use in the Future.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ServiceResultHolder {

	private Object result;

	/**
	 * @return the service result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the service result
	 */
	public void setResult(final Object result) {
		this.result = result;
	}

}
