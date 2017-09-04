package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.feature.PersistObject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * System control record.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "SystemCtrl")
public class SystemCtrlEntity implements PersistObject {

	@Id
	private Integer id;

	/**
	 * Default constructor.
	 */
	public SystemCtrlEntity() {
		this.id = 1;
	}

	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private VersionCtrlEntity versionCtrl;

	public void setCurrentVersion(final VersionCtrlEntity versionCtrl) {
		this.versionCtrl = versionCtrl;
	}

	public VersionCtrlEntity getCurrentVersion() {
		return versionCtrl;
	}

}
