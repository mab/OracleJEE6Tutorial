package firstcup.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;

import firstcup.ejb.DukesBirthdayBean;

@ManagedBean
@SessionScoped
public class DukesBDay implements Serializable {

	/** serialUID */
	private static final long serialVersionUID = 3622790923227180696L;

	private static final Logger LOG = Logger
			.getLogger("firstcup.web.DukesBDay");

	@EJB
	private DukesBirthdayBean dukesBirthdayBean;

	private int age;

	@NotNull
	private Date yourBD;
	private int ageDiff;
	private int absAgeDiff;
	private Double averageAgeDifference;

	/**
	 * Creates a new instance of DukesBDay
	 */
	public DukesBDay() {
		age = -1;
		yourBD = null;
		ageDiff = -1;
		setAbsAgeDiff(-1);
		averageAgeDifference = -1.0;
	}

	public int getAge() {
		// Use the java.net.* APIs to access the Duke's Age RESTful web service
		HttpURLConnection connection = null;
		BufferedReader rd = null;
		StringBuilder sb = null;
		String line = null;
		URL serverAddress = null;

		try {
			final String dukesAgeWebservice = "http://localhost:8080/dukesageservice-0.0.1-SNAPSHOT/dukesAge";
			serverAddress = new URL(dukesAgeWebservice);
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);

			// Make the connection to Duke's Age
			connection.connect();

			// Read in the response
			rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			// Convert the response to an int
			age = Integer.parseInt(sb.toString());
		} catch (final MalformedURLException e) {
			LOG.warning("A MalformedURLException occurred.");
			e.printStackTrace();
		} catch (final ProtocolException e) {
			LOG.warning("A ProtocolException occurred.");
			e.printStackTrace();
		} catch (final IOException e) {
			LOG.warning("An IOException occurred");
			e.printStackTrace();
		}

		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public Date getYourBD() {
		return yourBD;
	}

	public void setYourBD(final Date yourBD) {
		this.yourBD = yourBD;
	}

	public int getAgeDiff() {
		return ageDiff;
	}

	public void setAgeDiff(final int ageDiff) {
		this.ageDiff = ageDiff;
	}

	public int getAbsAgeDiff() {
		return absAgeDiff;
	}

	public void setAbsAgeDiff(final int absAgeDiff) {
		this.absAgeDiff = absAgeDiff;
	}

	public Double getAverageAgeDifference() {
		return averageAgeDifference;
	}

	public void setAverageAgeDifference(final Double averageAgeDifference) {
		this.averageAgeDifference = averageAgeDifference;
	}

	public String processBirthday() {
		this.setAgeDiff(dukesBirthdayBean.getAgeDifference(yourBD));
		LOG.log(Level.INFO, "age diff from dukesbday {0}", ageDiff);
		this.setAbsAgeDiff(Math.abs(this.getAgeDiff()));
		LOG.log(Level.INFO, "absAgeDiff {0}", absAgeDiff);
		this.setAverageAgeDifference(dukesBirthdayBean
				.getAverageAgeDifference());
		LOG.log(Level.INFO, "averageAgeDifference {0}", averageAgeDifference);
		return "/response.xhtml";
	}

}
