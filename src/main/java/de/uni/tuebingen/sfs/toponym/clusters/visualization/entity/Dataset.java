package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import static de.uni.tuebingen.sfs.toponym.clusters.visualization.resources.DatasetFacadeREST.LIST_NAME;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@XmlRootElement
public class Dataset {
    public static final String D_NO = "datasetNo";
    public static final String D_NAME = "name";
    public static final String D_INFO = "info";
    public static final String D_TSIZE = "tsize";
    public static final String D_FSIZE = "fsize";

    private Long datasetNo;
    private String name;
    private String info;
    private List<ToponymObject> toponymObjectList = null;
    private List<Formant> formantList = null;
    private Integer sizeToponyms = 0;
    private Integer sizeFormants = 0;

    protected Dataset() {
    }

    public Dataset(long id, String name) {
        this.datasetNo = id;
        this.name = name;
        this.info = "";
    }
    
    public Dataset(String name) {
        this.name = name;
        this.info = "";
    }    
    
    public Dataset(Entity datasetEntity) {
        this.datasetNo = datasetEntity.getKey().getId();
        this.info = "";
        this.name = (String) datasetEntity.getProperty(D_NAME);
        this.sizeToponyms = ((Long) datasetEntity.getProperty(D_TSIZE)).intValue();
        this.sizeFormants = ((Long) datasetEntity.getProperty(D_FSIZE)).intValue();
    }    

    public Long getDatasetNo() {
        return datasetNo;
    }

    public void setDatasetNo(Long datasetNo) {
        this.datasetNo = datasetNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public List<ToponymObject> getToponymObjectList() {
        if(this.toponymObjectList == null) {
            Key datasetListKey =  KeyFactory.createKey("DatasetList", LIST_NAME);
            Key datasetKey = KeyFactory.createKey(datasetListKey, "Dataset", this.datasetNo);
            
            this.toponymObjectList = new ArrayList<>();
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Query query1 = new Query("ToponymObject", datasetKey);
            List<Entity> toponymObjectEnts = datastore.prepare(query1)
                    .asList(FetchOptions.Builder.withDefaults());
            for (Entity toponymEnt : toponymObjectEnts) {
                this.toponymObjectList.add(new ToponymObject(toponymEnt));
            }
        }
        return toponymObjectList;
    }

    public void setToponymObjectList(List<ToponymObject> toponymObjectList) {
        this.toponymObjectList = toponymObjectList;
    }
    
    @XmlElement(name = "toponyms")
    public int getToponumObjectListSize(){
        return this.sizeToponyms;
    }
    
    @XmlTransient
    public List<Formant> getFormantList() {
        if(this.formantList == null){
            Key datasetListKey =  KeyFactory.createKey("DatasetList", LIST_NAME);
            Key datasetKey = KeyFactory.createKey(datasetListKey, "Dataset", this.datasetNo);
            
            this.formantList = new ArrayList<>();
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Query query2 = new Query("Formant", datasetKey);
            List<Entity> formantEnts = datastore.prepare(query2)
                    .asList(FetchOptions.Builder.withDefaults());
            for (Entity formantEnt : formantEnts) {
                this.formantList.add(new Formant(formantEnt));
            }
        }
        return formantList;
    }

    public void setFormantList(List<Formant> formantList) {
        this.formantList = formantList;
    }    

    @XmlElement(name = "formants")
    public int getFormantListSize(){
        return this.sizeFormants;
    }
    
    public void addToponymObjectToList(ToponymObject t){
        if (this.toponymObjectList == null)
            this.toponymObjectList = new ArrayList<>();
        this.toponymObjectList.add(t);
    }

    public void addFormantToList(Formant f){
        if (this.formantList == null)
            this.formantList = new ArrayList<>();
        this.formantList.add(f);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.datasetNo);
        hash = 73 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dataset other = (Dataset) obj;
        if (!Objects.equals(this.datasetNo, other.datasetNo)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "Dataset[ datasetNo=" + datasetNo + "  name=" + name + " ]";
    }

}
