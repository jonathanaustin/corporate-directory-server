package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultVersionableKeyIdObject;
import com.github.bordertech.corpdir.jpa.entity.version.ContactVersionEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Contact details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Contact")
public class ContactEntity extends DefaultVersionableKeyIdObject<ContactEntity, ContactVersionEntity> {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_id")
	private Set<ChannelEntity> channels;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ImageEntity image;

	@ManyToOne(fetch = FetchType.EAGER)
	private LocationEntity location;

	@Embedded
	private AddressEntity address;

	private String firstName;
	private String lastName;
	private String companyTitle;

	/**
	 * Default constructor.
	 */
	protected ContactEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public ContactEntity(final Long id) {
		super(id);
	}

	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the first name
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the last name
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the company title
	 */
	public String getCompanyTitle() {
		return companyTitle;
	}

	/**
	 * @param companyTitle the company title
	 */
	public void setCompanyTitle(final String companyTitle) {
		this.companyTitle = companyTitle;
	}

	/**
	 * @return the address
	 */
	public AddressEntity getAddress() {
		return address;
	}

	/**
	 *
	 * @param address the address
	 */
	public void setAddress(final AddressEntity address) {
		this.address = address;
	}

	/**
	 * @return the location
	 */
	public LocationEntity getLocation() {
		return location;
	}

	/**
	 * @param location the location
	 */
	public void setLocation(final LocationEntity location) {
		this.location = location;
	}

	/**
	 *
	 * @return the channels
	 */
	public Set<ChannelEntity> getChannels() {
		if (channels == null) {
			channels = new HashSet<>();
		}
		return channels;
	}

	/**
	 * @param channel the channel to add
	 */
	public void addChannel(final ChannelEntity channel) {
		if (channels == null) {
			channels = new HashSet<>();
		}
		channels.add(channel);
	}

	/**
	 * @param channel the channel to remove
	 */
	public void removeChannel(final ChannelEntity channel) {
		if (channels != null) {
			channels.remove(channel);
		}
	}

	/**
	 *
	 * @return the contact image
	 */
	public ImageEntity getImage() {
		return image;
	}

	/**
	 *
	 * @param image the contact image
	 */
	public void setImage(final ImageEntity image) {
		this.image = image;
	}

	@Override
	public ContactVersionEntity createVersion(final VersionCtrlEntity ctrl) {
		return new ContactVersionEntity(ctrl, this);
	}

}
