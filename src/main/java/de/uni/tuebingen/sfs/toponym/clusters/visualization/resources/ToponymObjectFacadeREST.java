package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject;
import java.util.ArrayList;
import java.util.List;
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
        for (Entity entity : toponyms) {
            toponymsObjects.add(new ToponymObject(entity.getProperty("name").toString()));
        }
        return toponymsObjects;
    }
    
    @POST
    @Path("new")
    public Object addToponym(@QueryParam("name") String name){
        String listName = "toponymObjects";
        Key toponymObjectListKey = KeyFactory.createKey("ToponymObjectList", listName);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity toponym = new Entity("ToponymObject", toponymObjectListKey);
        toponym.setProperty("name", name);
        datastore.put(toponym);
        return Response.ok().build();
    }
    
    @GET
    @Path("name/{id}")
    public String findName(@PathParam("id") Integer id) {
        return "English transliteration";
    }   

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return "100";
    }
}
