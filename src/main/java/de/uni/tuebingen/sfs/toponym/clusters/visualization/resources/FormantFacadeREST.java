/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Stateless
@Path("formant")
public class FormantFacadeREST extends AbstractFacade<Formant> {
    @PersistenceContext(unitName = "de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU")
    private EntityManager em;

    public FormantFacadeREST() {
        super(Formant.class);
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
    public void create(Formant entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Formant entity) {
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
    public Formant find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("all")
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Formant> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Formant> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
