/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Dataset;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
@Path("de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.dataset")
public class DatasetFacadeREST extends AbstractFacade<Dataset> {
    @PersistenceContext(unitName = "de.uni.tuebingen.sfs_toponym-clusters-visualization_war_2.0PU")
    private EntityManager em;

    public DatasetFacadeREST() {
        super(Dataset.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Dataset entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Dataset entity) {
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
    public Dataset find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Dataset> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Dataset> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
