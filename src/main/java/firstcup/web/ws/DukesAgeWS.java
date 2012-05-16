package firstcup.web.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Interface for DukesAgesService.
 * 
 * Could be extracted to an api package.
 * 
 * @author matthias.baumgart
 * 
 */
@Path("/dukesAge")
public interface DukesAgeWS {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText();

}