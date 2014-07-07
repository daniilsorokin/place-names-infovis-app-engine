package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
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
        String listName = "formants";
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key formantListKey =  KeyFactory.createKey("FormantList", listName);
        Query query = new Query("Formant", formantListKey);
        List<Entity> formantEnts = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        List<Formant> formants = new ArrayList<>();
        System.out.println("Query results size: " + formantEnts.size());
        for (Entity entity : formantEnts) {
            formants.add(new Formant(((Long) entity.getProperty("formantNo")).intValue(), 
                    (String) entity.getProperty("formantName")));
        }
        return formants;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return "100";
    }
}
