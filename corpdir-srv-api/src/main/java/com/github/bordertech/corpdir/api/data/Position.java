package com.github.bordertech.corpdir.api.data;

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
	private PositionType type;
	private String belongsToOrgUnitId;
	private String reportToPositionId;
	private List<String> reportPositionIds;
	private List<String> managesOrgUnitIds;
	private List<String> contactIds;

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
	 * @return the position type
	 */
	public PositionType getType() {
		return type;
	}

	/**
	 *
	 * @param type the position type
	 */
	public void setType(final PositionType type) {
		this.type = type;
	}

	/**
	 *
	 * @return the org unit id the position belongs to
	 */
	public String getBelongsToOrgUnitId() {
		return belongsToOrgUnitId;
	}

	/**
	 *
	 * @param belongsToOrgUnitId the org unit id the position belongs to
	 */
	public void setBelongsToOrgUnitId(final String belongsToOrgUnitId) {
		this.belongsToOrgUnitId = belongsToOrgUnitId;
	}

	/**
	 * @return the position id this position reports to
	 */
	public String getReportToPositionId() {
		return reportToPositionId;
	}

	/**
	 * @param reportToPositionId the position id this position reports to
	 */
	public void setReportToPositionId(final String reportToPositionId) {
		this.reportToPositionId = reportToPositionId;
	}

	/**
	 *
	 * @return the position ids that report to this position
	 */
	public List<String> getReportPositionIds() {
		if (reportPositionIds == null) {
			reportPositionIds = new ArrayList<>();
		}
		return reportPositionIds;
	}

	/**
	 * @param reportPositionIds the position ids that report to this position
	 */
	public void setReportPositionIds(final List<String> reportPositionIds) {
		this.reportPositionIds = reportPositionIds;
	}

	/**
	 *
	 * @return the org unit ids this position manages
	 */
	public List<String> getManagesOrgUnitIds() {
		if (managesOrgUnitIds == null) {
			managesOrgUnitIds = new ArrayList<>();
		}
		return managesOrgUnitIds;
	}

	/**
	 *
	 * @param managesOrgUnitIds the org unit ids this position manages
	 */
	public void setManagesOrgUnitIds(final List<String> managesOrgUnitIds) {
		this.managesOrgUnitIds = managesOrgUnitIds;
	}

	/**
	 *
	 * @return the contact ids for this position
	 */
	public List<String> getContactIds() {
		if (contactIds == null) {
			contactIds = new ArrayList<>();
		}
		return contactIds;
	}

	/**
	 *
	 * @param contactIds the contact ids for this position
	 */
	public void setContactIds(final List<String> contactIds) {
		this.contactIds = contactIds;
	}

}
