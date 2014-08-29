package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private Long datasetNo;
    private String name;
    private String info;
    private List<ToponymObject> toponymObjectList;
    private List<Formant> formantList;

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
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        this.toponymObjectList = new ArrayList<>();
        Query query1 = new Query("ToponymObject", datasetEntity.getKey())
                .setAncestor(datasetEntity.getKey());
        List<Entity> toponymObjectEnts = datastore.prepare(query1)
                .asList(FetchOptions.Builder.withDefaults());
        for (Entity toponymEnt : toponymObjectEnts) {
            this.toponymObjectList.add(new ToponymObject(toponymEnt));
        }
        
        this.formantList = new ArrayList<>();
        Query query2 = new Query("Formant", datasetEntity.getKey())
                .setAncestor(datasetEntity.getKey());
        List<Entity> formantEnts = datastore.prepare(query2)
                .asList(FetchOptions.Builder.withDefaults());
        for (Entity formantEnt : formantEnts) {
            this.formantList.add(new Formant(formantEnt));
        }
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
        return toponymObjectList;
    }

    public void setToponymObjectList(List<ToponymObject> toponymObjectList) {
        this.toponymObjectList = toponymObjectList;
    }
    
    @XmlTransient
    public List<Formant> getFormantList() {
        return formantList;
    }

    public void setFormantList(List<Formant> formantList) {
        this.formantList = formantList;
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
