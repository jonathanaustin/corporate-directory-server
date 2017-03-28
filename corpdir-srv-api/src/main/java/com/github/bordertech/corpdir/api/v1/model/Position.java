package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Position in organization.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Position extends AbstractApiObject {

	private String description;
	private String typeKey;
	private String belongsToOrgUnitKey;
	private String reportsToPositionKey;
	private List<String> reportPositionKeys;
	private List<String> managesOrgUnitKeys;
	private List<String> contactKeys;

	/**
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @param description the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 *
	 * @return the position type key
	 */
	public String getTypeKey() {
		return typeKey;
	}

	/**
	 *
	 * @param typeKey the position type key
	 */
	public void setTypeKey(final String typeKey) {
		this.typeKey = typeKey;
	}

	/**
	 *
	 * @return the org unit key the position belongs to
	 */
	public String getBelongsToOrgUnitKey() {
		return belongsToOrgUnitKey;
	}

	/**
	 *
	 * @param belongsToOrgUnitKey the org unit key the position belongs to
	 */
	public void setBelongsToOrgUnitKey(final String belongsToOrgUnitKey) {
		this.belongsToOrgUnitKey = belongsToOrgUnitKey;
	}

	/**
	 * @return the position key this position reports to
	 */
	public String getReportsToPositionKey() {
		return reportsToPositionKey;
	}

	/**
	 * @param reportsToPositionKey the position key this position reports to
	 */
	public void setReportsToPositionKey(final String reportsToPositionKey) {
		this.reportsToPositionKey = reportsToPositionKey;
	}

	/**
	 *
	 * @return the position key that report to this position
	 */
	public List<String> getReportPositionKeys() {
		if (reportPositionKeys == null) {
			reportPositionKeys = new ArrayList<>();
		}
		return reportPositionKeys;
	}

	/**
	 * @param reportPositionKeys the position keys that report to this position
	 */
	public void setReportPositionKeys(final List<String> reportPositionKeys) {
		this.reportPositionKeys = reportPositionKeys;
	}

	/**
	 *
	 * @return the org unit keys this position manages
	 */
	public List<String> getManagesOrgUnitKeys() {
		if (managesOrgUnitKeys == null) {
			managesOrgUnitKeys = new ArrayList<>();
		}
		return managesOrgUnitKeys;
	}

	/**
	 *
	 * @param managesOrgUnitKeys the org unit keys this position manages
	 */
	public void setManagesOrgUnitKeys(final List<String> managesOrgUnitKeys) {
		this.managesOrgUnitKeys = managesOrgUnitKeys;
	}

	/**
	 *
	 * @return the contact keys for this position
	 */
	public List<String> getContactKeys() {
		if (contactKeys == null) {
			contactKeys = new ArrayList<>();
		}
		return contactKeys;
	}

	/**
	 *
	 * @param contactKeys the contact keys for this position
	 */
	public void setContactKeys(final List<String> contactKeys) {
		this.contactKeys = contactKeys;
	}

}
