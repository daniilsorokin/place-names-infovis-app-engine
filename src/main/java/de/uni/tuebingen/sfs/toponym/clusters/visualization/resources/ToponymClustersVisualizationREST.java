package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Path("")
public class ToponymClustersVisualizationREST {
    
    @Path("message")
    @GET
    @Produces("text/plain")
    public String getMessage(){
        return "Test";
    }
}
