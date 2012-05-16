package firstcup.web;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;

import firstcup.ejb.DukesBirthdayBean;
import firstcup.web.ws.DukesAgeWS;

@ManagedBean
@SessionScoped
public class DukesBDay implements Serializable {

    /** serialUID */
    private static final long serialVersionUID = 3622790923227180696L;

    private static final Logger LOG = Logger.getLogger("firstcup.web.DukesBDay");

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
        final DukesAgeWS dukesAgeWS = ProxyFactoryAdapter.create(DukesAgeWS.class);
        final String ageString = dukesAgeWS.getText();
        age = Integer.parseInt(ageString);
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
        this.setAverageAgeDifference(dukesBirthdayBean.getAverageAgeDifference());
        LOG.log(Level.INFO, "averageAgeDifference {0}", averageAgeDifference);
        return "/response.xhtml";
    }

}
