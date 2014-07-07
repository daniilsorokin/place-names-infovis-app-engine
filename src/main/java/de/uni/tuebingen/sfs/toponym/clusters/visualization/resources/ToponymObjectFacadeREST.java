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
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Path("toponymobject")
public class ToponymObjectFacadeREST{

    public ToponymObjectFacadeREST() {
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public ToponymObject find(@PathParam("id") Integer id) {
        return new ToponymObject("Test");
    }

    @GET
    @Path("set")
    @Produces("application/json")
    public List<ToponymObject> findSet(@QueryParam("id[]") List<Integer> ids) {
        return new ArrayList<>();
    }

//    @GET
//    @Path("formant")
//    @Produces({"application/xml", "application/json"})
//    public List<ToponymObject> findFormant(@QueryParam("id") Integer id) {
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery cq = cb.createQuery();
//        Root<ToponymObject> root = cq.from(ToponymObject.class);
//        cq.where(cb.equal(root.get(ToponymObject_.formant), getEntityManager().find(Formant.class, id)));
//        cq.select(root);
//        return getEntityManager().createQuery(cq).getResultList();
//    }
    
    @GET
    @Produces("application/json")
    public List<ToponymObject> findAll() {
        String listName = "toponymObjects";
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key toponymObjectListKey =  KeyFactory.createKey("ToponymObjectList", listName);
        Query query = new Query("ToponymObject", toponymObjectListKey);
        List<Entity> toponyms = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        List<ToponymObject> toponymsObjects = new ArrayList<>();
        System.out.println("Query results size: " + toponyms.size());
        for (Entity entity : toponyms) {
            toponymsObjects.add(new ToponymObject(entity));
        }
        return toponymsObjects;
    }
    
    @POST
    @Path("new")
    public Response addToponym(@QueryParam("name") String name, @QueryParam("lat") double lat, @QueryParam("lng") double lng){
        String listName = "toponymObjects";
        Key toponymObjectListKey = KeyFactory.createKey("ToponymObjectList", listName);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity toponym = new Entity("ToponymObject", toponymObjectListKey);
        toponym.setProperty("name", name);
        toponym.setProperty("lat", lat);
        toponym.setProperty("lng", lng);
        datastore.put(toponym);
        return Response.ok().build();
    }

    @POST
    @Path("upload-json")
    @Consumes("application/json")
    public Response loadToponyms(List<ToponymObject> toponyms){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String listName = "toponymObjects";
        Key toponymObjectListKey = KeyFactory.createKey("ToponymObjectList", listName);
        System.out.println("Loaded list size: " + toponyms.size());
        System.out.println("Head: " + (toponyms.size() > 0 ? toponyms.get(0) : ""));
        HashSet<Formant> formants = new HashSet<>();
        ArrayList<Entity> toponymEnts = new ArrayList<>();
        for (ToponymObject toponymObject : toponyms) {
            Entity toponymEnt = new Entity("ToponymObject", toponymObjectListKey);
            toponymEnt.setProperty("toponymNo", toponymObject.getToponymNo());
            toponymEnt.setProperty("engName", toponymObject.getEnglishTransliteration());
            toponymEnt.setProperty("name", toponymObject.getName());
            toponymEnt.setProperty("lat", toponymObject.getLatitude());
            toponymEnt.setProperty("lng", toponymObject.getLongitude());
            toponymEnt.setProperty("type", toponymObject.getType());
            Formant formant = toponymObject.getFormant();
            formants.add(formant);
            toponymEnt.setProperty("formantName", formant.getFormantName());
            toponymEnt.setProperty("formantNo", formant.getFormantNo());
            toponymEnts.add(toponymEnt);
        }
        datastore.put(toponymEnts);
        
        String listName2 = "formants";
        Key formantsListKey = KeyFactory.createKey("FormantList", listName2);
        ArrayList<Entity> formantEnts = new ArrayList<>();
        for (Formant formant : formants) {
            Entity formantEnt = new Entity("Formant", formantsListKey);
            formantEnt.setProperty("formantName", formant.getFormantName());
            formantEnt.setProperty("formantNo", formant.getFormantNo());
            formantEnts.add(formantEnt);
        }
        datastore.put(formantEnts);
        return Response.ok().build();
    }  
    
    @GET
    @Path("name/{id}")
    public String findName(@PathParam("id") Integer id){
        return "English transliteration";
    }   

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return "100";
    }
}
