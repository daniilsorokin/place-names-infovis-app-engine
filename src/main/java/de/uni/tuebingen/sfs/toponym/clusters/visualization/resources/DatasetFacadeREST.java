package de.uni.tuebingen.sfs.toponym.clusters.visualization.resources;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.sun.jersey.api.json.JSONConfiguration;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Dataset;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant;
import de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.jsefa.Deserializer;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Stateless
@Path("dataset")
public class DatasetFacadeREST {
    public static final String LIST_NAME = "datasets";

    public DatasetFacadeREST() {
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Dataset find(@PathParam("id") Long id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key datasetListKey =  KeyFactory.createKey("DatasetList", LIST_NAME);
        Key datasetKey = KeyFactory.createKey(datasetListKey, "Dataset", id);
        Dataset dataset = null;
        try {
            dataset = new Dataset(datastore.get(datasetKey));
        } catch (EntityNotFoundException ex) {
            Logger.getLogger(DatasetFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataset;
    }

    @POST
    @Path("delete")
    @Consumes("text/plain")
    public Response deleteDataset(String idsAsString) {
//        String[] stringIds = idsAsString.split(",");
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        for (String stringId : stringIds) {
//            int id = Integer.parseInt(stringId);
//            Dataset dataset = super.find(id);
//            if (dataset == null) {
//                System.err.println(idsAsString);
//                return Response.serverError().build();
//            }
//            em.getTransaction().begin();
//            
//            CriteriaDelete<ToponymObject> cdt = cb.createCriteriaDelete(ToponymObject.class);
//            Root<ToponymObject> roott = cdt.from(ToponymObject.class);
//            cdt.where(cb.equal(roott.get(ToponymObject_.dataset), dataset));
//            em.createQuery(cdt).executeUpdate();
//            
//            CriteriaDelete<Formant> cdf = cb.createCriteriaDelete(Formant.class);
//            Root<Formant> rootf = cdf.from(Formant.class);
//            cdf.where(cb.equal(rootf.get(Formant_.dataset), dataset));
//            em.createQuery(cdf).executeUpdate();
//
//            em.remove(dataset);
//            em.getTransaction().commit();
//            
////        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
////        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
////        Root<Formant> root = cq.from(Formant.class);
////        cq.where(cb.equal(root.get(Formant_.formantName), "ка"));
////        cq.select(root);
////        int deletedRows = getEntityManager().createQuery(cq).getResultList().size();
//        }        
        return Response.ok(idsAsString).build();
    }

    
    @GET
    @Path("{id}/toponymobjects")
    @Produces({"application/xml", "application/json"})
    public List<ToponymObject> getDatasetToponyms (@PathParam("id") Long id) {
        Dataset dataset = find(id);
        if (dataset == null) return new ArrayList<>();
        return dataset.getToponymObjectList();
    }
    
    @GET
    @Path("{id}/formants")
    @Produces({"application/xml", "application/json"})
    public List<Formant> getDatasetFormants (@PathParam("id") Long id) {
        Dataset dataset = find(id);
        if (dataset == null) return new ArrayList<>();
        return dataset.getFormantList();
    }    

    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Dataset> findAll() {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Key datasetListKey =  KeyFactory.createKey("DatasetList", LIST_NAME);
            Query query = new Query("Dataset", datasetListKey);
            List<Entity> datasetsEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
            List<Dataset> datasets = new ArrayList<>();
            System.out.println("Query results size: " + datasetsEntities.size());
            for (Entity entity : datasetsEntities) {
                datasets.add(new Dataset(entity));
            }
            return datasets;
        }
        return new ArrayList<>();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return "100";
    }
    
//    public List<Formant> findFormantByName (String name){
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<Formant> root = cq.from(Formant.class);
//        cq.where(cb.equal(root.get(Formant_.formantName), name));
//        cq.select(root);
//        return getEntityManager().createQuery(cq).getResultList();
//    }
//    
//    private Formant getOriginal(Formant f){
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<Formant> root = cq.from(Formant.class);
//        cq.where(cb.and(
//                cb.equal(root.get(Formant_.formantName), f.getFormantName()),
//                cb.equal(root.get(Formant_.dataset), f.getDataset())
//                ));
//        cq.select(root);
//        List<Formant> results = getEntityManager().createQuery(cq).getResultList();
//        if (results.isEmpty()) return null;
//        else return results.get(0);
//    }
//    
    @POST
    @Path("upload/{name}/{type}")
    @Consumes("text/plain")
    public Response loadToponyms(String toponymsAsCsv, @PathParam("name") String datasetName,
            @PathParam("type") String type){
        
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        if(currentUser == null) return Response.ok().build();
        
        CsvConfiguration csvConfiguration = new CsvConfiguration();
        switch(type.toLowerCase()) {
            default:
            case "csv":
                csvConfiguration.setFieldDelimiter(',');
                break;
            case "tsv":
                csvConfiguration.setFieldDelimiter('\t');
                break;
        }
        csvConfiguration.getSimpleTypeConverterProvider().registerConverterType(Double.class, DoubleConverter.class);
        Deserializer deserializer = CsvIOFactory.createFactory(csvConfiguration, ToponymObject.class).createDeserializer();
        StringReader reader = new StringReader(toponymsAsCsv);
        deserializer.open(reader);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key datasetListKey =  KeyFactory.createKey("DatasetList", LIST_NAME);
        Entity datasetEntity = new Entity("Dataset", datasetListKey);
        datasetEntity.setProperty(Dataset.D_NAME, datasetName);
        
        HashMap<Formant,List<ToponymObject>> formants = new HashMap<>();
        Formant noFormant = new Formant("no formant");
        Integer fsize = 0, tsize = 0;
        while (deserializer.hasNext()) {
            ToponymObject t = deserializer.next();
            if (t.getFormant() == null) 
                t.setFormant(noFormant);
            if(formants.containsKey(t.getFormant())) {
                formants.get(t.getFormant()).add(t);
            } else {
                ArrayList<ToponymObject> toPut = new ArrayList<>();
                toPut.add(t);
                formants.put(t.getFormant(), toPut);
                fsize++;
            }
            tsize++;
        }
        datasetEntity.setProperty(Dataset.D_TSIZE, tsize);
        datasetEntity.setProperty(Dataset.D_FSIZE, fsize);
        datastore.put(datasetEntity);
        System.out.println("Datasetname: " + datasetName);
        System.out.println("Loaded formants: " + formants.size());
        ArrayList<Entity> toAdd = new ArrayList<>();
        for (Map.Entry<Formant, List<ToponymObject>> entry : formants.entrySet()) {
            Formant formant = entry.getKey();
            Entity formantEnt = new Entity("Formant", datasetEntity.getKey());
            formantEnt.setProperty(Formant.F_NAME, formant.getFormantName());
            JSONArray jsonArray = new JSONArray();
            List<ToponymObject> list = entry.getValue();
            for (ToponymObject toponymObject : list) {
                Entity toponymEnt = new Entity("ToponymObject", formantEnt.getKey());
                toponymEnt.setProperty(ToponymObject.T_NAME, toponymObject.getName());
                toponymEnt.setProperty(ToponymObject.T_LATITUDE, toponymObject.getLatitude());
                toponymEnt.setProperty(ToponymObject.T_LONGITUDE, toponymObject.getLongitude());
                if (toponymObject.getLanguage() != null)
                    toponymEnt.setProperty(ToponymObject.T_LANGUAGE, toponymObject.getLanguage());
                toponymEnt.setProperty(ToponymObject.T_FORMANT_NAME, formant.getFormantName());
                toAdd.add(toponymEnt);
                jsonArray.put(toponymEnt.getKey().getId());
            }
            System.out.println("jsonArray:" + jsonArray.toString());
            formantEnt.setProperty(Formant.F_TIDS, jsonArray.toString());
            datastore.put(formantEnt);
        }
        datastore.put(toAdd);
        return Response.ok().build();
    }
}
