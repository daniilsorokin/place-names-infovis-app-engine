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
import static de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject.T_ENG_NAME;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@XmlRootElement
public class Formant {
    public static final String F_NAME = "formantName";
    public static final String F_NO = "formantNo";

    
    private Integer formantNo;
    private String formantName;
    private List<String> affixList;
    private List<ToponymObject> toponymObjectList;
    
    protected Formant() {}

    public Formant(String formantName) {
        this(null, formantName);
    }

    public Formant(Integer formantNo, String formantName) {
        this.formantNo = formantNo;
        this.formantName = formantName;
        this.affixList = new ArrayList<>();
        this.toponymObjectList = new ArrayList<>();
    } 
    
    public Formant(Entity formantEnt) {
        this.formantNo = ((Long) formantEnt.getProperty(F_NO)).intValue();
        this.formantName = (String) formantEnt.getProperty(F_NAME);
        this.affixList = new ArrayList<>();
        this.toponymObjectList = new ArrayList<>();
        
    }       

    public Integer getFormantNo() {
        return formantNo;
    }

    public void setFormantNo(Integer formantNo) {
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
    public List<Integer> getToponymObjectIdList() {
        String listName = "toponymObjects";
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key toponymObjectListKey =  KeyFactory.createKey("ToponymObjectList", listName);
        Filter formantNoFilter = new FilterPredicate(F_NO,
                                    FilterOperator.EQUAL,
                                    this.formantNo);
        Query query = new Query("ToponymObject", toponymObjectListKey)
                .setFilter(formantNoFilter);
        List<Entity> toponyms = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        
        List<Integer> toponymObjectIdList = new ArrayList<>();
        if(!toponyms.isEmpty()){
            for (Entity toponym : toponyms) {
                toponymObjectIdList.add( ((Long) toponym.getProperty(ToponymObject.T_NO)).intValue() );
            }        
        }
        return toponymObjectIdList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formantNo != null ? formantNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Formant)) {
            return false;
        }
        Formant other = (Formant) object;
        if ((this.formantNo == null && other.formantNo != null) || (this.formantNo != null && !this.formantNo.equals(other.formantNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formant[ formantNo=" + formantNo + "  name=" + formantName + " ]";
    }

}
