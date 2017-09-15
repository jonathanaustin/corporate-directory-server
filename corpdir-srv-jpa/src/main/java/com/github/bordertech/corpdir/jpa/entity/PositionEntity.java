package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultKeyIdVersionObject;
import com.github.bordertech.corpdir.jpa.entity.links.PositionLinksEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Position in organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Position")
public class PositionEntity extends DefaultKeyIdVersionObject<PositionLinksEntity> {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PositionLinksEntity> dataVersions;

	@ManyToOne
	private PositionTypeEntity type;

	/**
	 * Default constructor.
	 */
	protected PositionEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public PositionEntity(final Long id) {
		super(id);
	}

	/**
	 *
	 * @return the position type
	 */
	public PositionTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the position type
	 */
	public void setType(final PositionTypeEntity type) {
		this.type = type;
	}

	@Override
	public PositionLinksEntity getOrCreateDataVersion(final VersionCtrlEntity ctrl) {
		PositionLinksEntity links = getDataVersion(ctrl.getId());
		if (links == null) {
			links = new PositionLinksEntity(ctrl, this);
			addDataVersion(links);
		}
		return links;
	}

	@Override
	public Set<PositionLinksEntity> getDataVersions() {
		return dataVersions;
	}

	@Override
	public void setDataVersions(final Set<PositionLinksEntity> dataVersions) {
		this.dataVersions = dataVersions;
	}

}
