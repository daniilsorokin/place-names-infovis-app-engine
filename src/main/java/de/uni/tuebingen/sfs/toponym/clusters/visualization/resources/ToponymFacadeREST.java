/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Toponym;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Toponym_;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
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
@Path("toponym")
public class ToponymFacadeREST extends AbstractFacade<Toponym> {
//    @PersistenceContext(unitName = "de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU")
    private EntityManager em;

    public ToponymFacadeREST() {
        super(Toponym.class);
        try {
            // Initialize class
            Class.forName("org.postgresql.Driver");
            // Create an Entity manager
            em = Persistence.createEntityManagerFactory("de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU").createEntityManager();
        }  catch (ClassNotFoundException ex) {
            Logger.getLogger(ToponymFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @POST
//    @Override
//    @Consumes({"application/xml", "application/json"})
//    public void create(Toponym entity) {
//        super.create(entity);
//    }
//
//    @PUT
//    @Override
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Toponym entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Integer id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Toponym find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("list_by_id")
    @Produces("application/json")
    public List<Toponym> findById(@QueryParam("id[]") Integer[] ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Toponym> toponymsRoot = cq.from(Toponym.class);
        cq.where(toponymsRoot.get(Toponym_.id).in((Object[]) ids));
        cq.select(toponymsRoot);
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Override
    @Produces("application/json")
    public List<Toponym> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces("application/json")
    public List<Toponym> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
