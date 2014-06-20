package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
//import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Path("formant")
public class FormantFacadeREST {

    public FormantFacadeREST() {
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Formant find(@PathParam("id") Integer id) {
        return new Formant("testformant");
    }

    @GET
    @Path("{id}/toponyms")
    @Produces({"application/xml", "application/json"})
    public List<ToponymObject> findToponyms(@PathParam("id") Integer id) {
        return new ArrayList<>();
    }

//    @GET
//    @Path("{id}/toponyms/ids")
//    @Produces("application/json")
//    public JSONArray findToponymIds(@PathParam("id") Integer id) {
////        Formant formant = super.find(id);
//        List<Integer> ids = new ArrayList<>();
////        for (ToponymObject toponymObject : formant.getToponymObjectList()) {
////            ids.add(toponymObject.getToponymNo());
////        }
//        return new JSONArray(ids);
//    }
    
    @GET
    @Path("set")
    @Produces({"application/xml", "application/json"})
    public List<Formant> findSet(@QueryParam("id[]") List<Integer> ids) {
        return new ArrayList<>();
    }    

    
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Formant> findAll() {
        return new ArrayList<>();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return "100";
    }
}
