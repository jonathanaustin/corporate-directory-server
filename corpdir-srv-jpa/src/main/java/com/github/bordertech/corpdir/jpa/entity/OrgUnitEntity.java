package com.github.bordertech.corpdir.jpa.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "OrgUnit")
public class OrgUnitEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String alternateKey;
	private String desc;
	@ManyToOne(fetch = FetchType.EAGER)
	private OrgUnitTypeEntity type;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<OrgUnitEntity> subOrgUnits;
	@OneToMany(fetch = FetchType.LAZY)
	private List<PositionEntity> positions;
	@OneToMany(fetch = FetchType.LAZY)
	private List<ContactEntity> contacts;
	private boolean active;
	private boolean custom;

	/**
	 *
	 * @return the unique id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the unique id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the alternate org unit key
	 */
	public String getAlternateKey() {
		return alternateKey;
	}

	/**
	 *
	 * @param alternateKey the alternate org unit key
	 */
	public void setAlternateKey(final String alternateKey) {
		this.alternateKey = alternateKey;
	}

	/**
	 *
	 * @return the description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 *
	 * @param desc the description
	 */
	public void setDesc(final String desc) {
		this.desc = desc;
	}

	/**
	 *
	 * @return the organization type
	 */
	public OrgUnitTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the organization type
	 */
	public void setType(final OrgUnitTypeEntity type) {
		this.type = type;
	}

	/**
	 *
	 * @return the units managed by this unit
	 */
	public List<OrgUnitEntity> getSubOrgUnits() {
		return subOrgUnits;
	}

	/**
	 *
	 * @param subOrgUnits the units managed by this unit
	 */
	public void setSubOrgUnits(final List<OrgUnitEntity> subOrgUnits) {
		this.subOrgUnits = subOrgUnits;
	}

	/**
	 *
	 * @return the positions belonging to this unit
	 */
	public List<PositionEntity> getPositions() {
		return positions;
	}

	/**
	 *
	 * @param positions the positions belonging to this unit
	 */
	public void setPositions(final List<PositionEntity> positions) {
		this.positions = positions;
	}

	/**
	 *
	 * @return the contacts belonging to this unit
	 */
	public List<ContactEntity> getContacts() {
		return contacts;
	}

	/**
	 *
	 * @param contacts the contacts belonging to this unit
	 */
	public void setContacts(final List<ContactEntity> contacts) {
		this.contacts = contacts;
	}

	/**
	 *
	 * @return true if active record
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active record
	 */
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @return true if custom record
	 */
	public boolean isCustom() {
		return custom;
	}

	/**
	 *
	 * @param custom true if custom record
	 */
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

}
