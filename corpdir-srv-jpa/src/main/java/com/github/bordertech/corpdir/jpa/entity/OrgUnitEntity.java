package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultKeyIdVersionObject;
import com.github.bordertech.corpdir.jpa.entity.links.OrgUnitLinksEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class OrgUnitEntity extends DefaultKeyIdVersionObject<OrgUnitLinksEntity> {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<OrgUnitLinksEntity> dataVersions;

	@ManyToOne
	private UnitTypeEntity type;

	/**
	 * Default constructor.
	 */
	protected OrgUnitEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public OrgUnitEntity(final Long id) {
		super(id);
	}

	/**
	 *
	 * @return the organization type
	 */
	public UnitTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the organization type
	 */
	public void setType(final UnitTypeEntity type) {
		this.type = type;
	}

	@Override
	public OrgUnitLinksEntity getOrCreateDataVersion(final VersionCtrlEntity ctrl) {
		OrgUnitLinksEntity links = getDataVersion(ctrl.getId());
		if (links == null) {
			links = new OrgUnitLinksEntity(ctrl, this);
			addDataVersion(links);
		}
		return links;
	}

	@Override
	public Set<OrgUnitLinksEntity> getDataVersions() {
		return dataVersions;
	}

	@Override
	public void setDataVersions(final Set<OrgUnitLinksEntity> dataVersions) {
		this.dataVersions = dataVersions;
	}

}
