package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject_;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Stateless
@Path("toponymobject")
public class ToponymObjectFacadeREST extends AbstractFacade<ToponymObject> {
    @PersistenceContext(unitName = "de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU")
    private EntityManager em;

    public ToponymObjectFacadeREST() {
        super(ToponymObject.class);
        try {
            // Initialize class
            Class.forName("org.postgresql.Driver");
            // Create an Entity manager
            em = Persistence.createEntityManagerFactory("de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU").createEntityManager();
        }  catch (ClassNotFoundException ex) {
            Logger.getLogger(ToponymObjectFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ToponymObject entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ToponymObject entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ToponymObject find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("set")
    @Produces({"application/xml", "application/json"})
    public List<ToponymObject> findSet(@QueryParam("id[]") List<Integer> ids) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<ToponymObject> root = cq.from(ToponymObject.class);
        cq.where(root.get(ToponymObject_.toponymNo).in(ids));
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @GET
    @Override
    @Path("all")
    @Produces({"application/xml", "application/json"})
    public List<ToponymObject> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ToponymObject> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
