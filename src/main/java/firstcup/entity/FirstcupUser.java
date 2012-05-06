package firstcup.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "findAverageAgeDifferenceOfAllFirstcupUsers", query = "SELECT AVG(u.ageDifference) FROM FirstcupUser u")
public class FirstcupUser implements Serializable {

	/** serialUID */
	private static final long serialVersionUID = 1346548732568198253L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Calendar birthday;

	private int ageDifference;

	public FirstcupUser() {
	}

	public FirstcupUser(final Date date, final int ageDifference) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		birthday = calendar;
		this.setAgeDifference(ageDifference);
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(final Calendar birthday) {
		this.birthday = birthday;
	}

	public int getAgeDifference() {
		return ageDifference;
	}

	public void setAgeDifference(final int ageDifference) {
		this.ageDifference = ageDifference;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ageDifference;
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FirstcupUser other = (FirstcupUser) obj;
		if (ageDifference != other.ageDifference)
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FirstcupUser [id=" + id + ", birthday=" + birthday
				+ ", ageDifference=" + ageDifference + "]";
	}

}
