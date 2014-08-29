package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;


/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@XmlRootElement
@CsvDataType
public class Formant {
    public static final String F_NAME = "formantName";
    public static final String F_NO = "formantNo";

    
    private Long formantNo;
    @CsvField(pos = 1)
    private String formantName;
    private List<String> affixList;
    private List<ToponymObject> toponymObjectList;
    
    protected Formant() {}

    public Formant(String formantName) {
        this(null, formantName);
    }

    public Formant(Long formantNo, String formantName) {
        this.formantNo = formantNo;
        this.formantName = formantName;
        this.affixList = new ArrayList<>();
        this.toponymObjectList = new ArrayList<>();
    } 
    
    public Formant(Entity formantEnt) {
        this.formantNo = formantEnt.getKey().getId();
        this.formantName = (String) formantEnt.getProperty(F_NAME);
        this.affixList = new ArrayList<>();
        this.toponymObjectList = new ArrayList<>();
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query1 = new Query("ToponymObject", formantEnt.getKey())
                .setAncestor(formantEnt.getKey());
        List<Entity> toponymObjectEnts = datastore.prepare(query1)
                .asList(FetchOptions.Builder.withDefaults());
        for (Entity toponymEnt : toponymObjectEnts) {
            this.toponymObjectList.add(new ToponymObject(toponymEnt));
        }
        
    }       

    public Long getFormantNo() {
        return formantNo;
    }

    public void setFormantNo(Long formantNo) {
        this.formantNo = formantNo;
    }

    public String getFormantName() {
        return formantName;
    }

    public void setFormantName(String formantName) {
        this.formantName = formantName;
    }

    @XmlTransient
    public List<String> getAffixList() {
        return affixList;
    }

    public void setAffixList(List<String> affixList) {
        this.affixList = affixList;
    }

    @XmlTransient
    public List<ToponymObject> getToponymObjectList() {
        return toponymObjectList;
    }

    public void setToponymObjectList(List<ToponymObject> toponymObjectList) {
        this.toponymObjectList = toponymObjectList;
    }
    
    @XmlElement(name = "toponymIds")
    public List<Long> getToponymObjectIdList() {
        List<Long> toponymObjectIdList = new ArrayList<>();
        for (ToponymObject toponymObject : toponymObjectList) {
            toponymObjectIdList.add(toponymObject.getToponymNo());
        }        
        return toponymObjectIdList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.formantName);
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
        final Formant other = (Formant) obj;
        if (!Objects.equals(this.formantName, other.formantName)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Formant[ formantNo=" + formantNo + "  name=" + formantName + " ]";
    }

}
