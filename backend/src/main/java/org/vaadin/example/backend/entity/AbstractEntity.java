package org.vaadin.example.backend.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * AbstractEntity is abstract base class for all business entities. It defines
 * id and equals implementation that is able to determine the entity identity
 * even if it's not yet persisted to database.
 * 
 * @author Peter
 */
@MappedSuperclass
public abstract class AbstractEntity {

	@Column(name = "UUID", nullable = false, unique = true)
	private String uuid;

	@Version
	@Column(name = "Revision")
	private long revision;

	public AbstractEntity() {
		uuid = UUID.randomUUID().toString();
	}

	public String getUuid() {
		return uuid;
	}

	/**
	 * @return entity's database primary key if persisted
	 */
	public abstract Long getId();

	/**
	 * @return true if this entity is persisted to database, false otherwise.
	 */
	public boolean isPersisted() {
		return getId() != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof AbstractEntity) {
			return this.uuid.equals(((AbstractEntity) obj).uuid);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
}
