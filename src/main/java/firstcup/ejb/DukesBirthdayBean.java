package firstcup.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import firstcup.entity.FirstcupUser;

@Stateless
@LocalBean
public class DukesBirthdayBean {

	private static final Logger LOG = Logger
			.getLogger("firstcup.ejb.DukesBirthdayBean");

	@PersistenceContext
	private EntityManager em;

	public Double getAverageAgeDifference() {
		final Query namedQuery = em
				.createNamedQuery("findAverageAgeDifferenceOfAllFirstcupUsers");
		final Double avgAgeDiff = (Double) namedQuery.getSingleResult();
		LOG.log(Level.INFO, "Average age difference is: {0}", avgAgeDiff);
		return avgAgeDiff;
	}

	public int getAgeDifference(final Date date) {
		int ageDifference;

		final Calendar theirBirthday = new GregorianCalendar();
		final Calendar dukesBirthday = new GregorianCalendar(1995,
				Calendar.MAY, 23);

		// Set the Calendar object to the passed in Date
		theirBirthday.setTime(date);

		// Subtract the user's age from Duke's age
		ageDifference = dukesBirthday.get(Calendar.YEAR)
				- theirBirthday.get(Calendar.YEAR);
		LOG.log(Level.INFO, "Raw ageDifference is: {0}", ageDifference);
		// Check to see if Duke's birthday occurs before the user's. If so,
		// subtract one from the age difference
		if (dukesBirthday.before(theirBirthday) && (ageDifference > 0)) {
			ageDifference--;
		}

		final int absAgeDifference = Math.abs(ageDifference);
		// create and store the user's birthday in the database
		final FirstcupUser user = new FirstcupUser(date, absAgeDifference);
		em.persist(user);

		LOG.log(Level.INFO, "Final ageDifference is: {0}", ageDifference);

		return ageDifference;
	}

}
