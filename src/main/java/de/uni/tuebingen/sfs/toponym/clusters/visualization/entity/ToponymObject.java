package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import com.google.appengine.api.datastore.Entity;
import static de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant.F_NAME;
import static de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant.F_NO;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@XmlRootElement
public class ToponymObject {
    public static final String T_KIND = "ToponymObject";
    public static final String T_NO = "toponymNo";
    public static final String T_ENG_NAME = "engName";
    public static final String T_NAME = "name";
    public static final String T_LATITUDE = "lat";
    public static final String T_LONGITUDE = "lng";
    public static final String T_TYPE = "type";    
    
    private Integer toponymNo;
    private String name;
    private String otherNames;
    private String englishTransliteration;    
    private Double latitude;
    private Double longitude;
    private List<String> affixList;
    private String type;
    private String language;
    private Formant formant;
    private String dataset;
    
    public ToponymObject() {}

    public ToponymObject(String name, double lat, double lng) {
        this.name = name;
        this.latitude = lat;
        this.longitude = lng;
    }

    public ToponymObject(String name) {
        this.name = name;
        this.formant = new Formant("no formant");
        this.toponymNo = 0;
    }

    public ToponymObject(Entity toponymEnt) {
        this.toponymNo = ((Long) toponymEnt.getProperty(T_NO)).intValue();
        this.englishTransliteration = (String) toponymEnt.getProperty(T_ENG_NAME);
        this.name = (String) toponymEnt.getProperty(T_NAME);
        this.latitude = (Double) toponymEnt.getProperty(T_LATITUDE);
        this.longitude = (Double) toponymEnt.getProperty(T_LONGITUDE);
        this.type = (String) toponymEnt.getProperty(T_TYPE);
        this.formant = new Formant(
                        ((Long) toponymEnt.getProperty(F_NO)).intValue(), 
                        (String) toponymEnt.getProperty(F_NAME)
                        );
    }    
    
    public Integer getToponymNo() {
        return toponymNo;
    }

    public void setToponymNo(Integer toponymNo) {
        this.toponymNo = toponymNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @XmlTransient
    public List<String> getAffixList() {
        return affixList;
    }

    public void setAffixList(List<String> affixList) {
        this.affixList = affixList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEnglishTransliteration() {
        return englishTransliteration;
    }

    public void setEnglishTransliteration(String englishTransliteration) {
        this.englishTransliteration = englishTransliteration;
    }    
    
    public Formant getFormant() {
        return formant;
    }

    public void setFormant(Formant formant) {
        this.formant = formant;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (toponymNo != null ? toponymNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ToponymObject)) {
            return false;
        }
        ToponymObject other = (ToponymObject) object;
        if ((this.toponymNo == null && other.toponymNo != null) || (this.toponymNo != null && !this.toponymNo.equals(other.toponymNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ToponymObject[ toponymNo=" + toponymNo + "  name=" + name + 
                " latitude=" + this.latitude + " longitude=" + this.longitude + " ]";
    }

}
