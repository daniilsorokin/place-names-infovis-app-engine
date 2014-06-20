package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
        return new ArrayList<>();
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
