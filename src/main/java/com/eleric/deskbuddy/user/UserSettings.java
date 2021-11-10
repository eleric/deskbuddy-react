package com.eleric.deskbuddy.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_settings")
public class UserSettings {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "photo_freq")
	private int photoFreq;
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPhotoFreq() {
		return photoFreq;
	}

	public void setPhotoFreq(int photoFreq) {
		this.photoFreq = photoFreq;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserSettings that = (UserSettings) o;
		return getUserName().equals(that.getUserName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserName());
	}

	@Override
	public String toString() {
		return "UserSettings{" + "id=" + id + ", userName='" + userName + '\''
				+ ", photoFreq=" + photoFreq + ", modifiedDate=" + modifiedDate
				+ '}';
	}
}
