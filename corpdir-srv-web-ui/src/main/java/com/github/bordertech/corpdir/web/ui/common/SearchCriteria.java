package com.github.bordertech.corpdir.web.ui.common;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jonathan
 */
public class SearchCriteria implements Serializable {

	private String searchText;
	private Boolean active;
	private Boolean custom;
	private Date startDate;
	private Date endDate;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public Boolean getCustom() {
		return custom;
	}

	public void setCustom(final Boolean custom) {
		this.custom = custom;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
