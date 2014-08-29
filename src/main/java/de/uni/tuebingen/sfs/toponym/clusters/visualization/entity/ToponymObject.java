package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import com.google.appengine.api.datastore.Entity;
import static de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant.F_NAME;
import static de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant.F_NO;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

/**
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@XmlRootElement
@CsvDataType
public class ToponymObject {
    public static final String T_KIND = "ToponymObject";
    public static final String T_NO = "toponymNo";
    public static final String T_ENG_NAME = "engName";
    public static final String T_NAME = "name";
    public static final String T_LATITUDE = "lat";
    public static final String T_LONGITUDE = "lng";
    public static final String T_TYPE = "type";
    public static final String T_LANGUAGE = "language";
    
    private Long toponymNo;
    @CsvField(pos = 1)
    private String name;
    private String otherNames;
    private String englishTransliteration;    
    @CsvField(pos = 2)
    private Double latitude;
    @CsvField(pos = 3)
    private Double longitude;
    private List<String> affixList;
    private String type;
    @CsvField(pos = 5)
    private String language;
    @CsvField(pos = 4)
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
    }

    public ToponymObject(Entity toponymEnt) {
        this.toponymNo = toponymEnt.getKey().getId();
        this.name = (String) toponymEnt.getProperty(T_NAME);
        this.latitude = (Double) toponymEnt.getProperty(T_LATITUDE);
        this.longitude = (Double) toponymEnt.getProperty(T_LONGITUDE);
        this.language = (String) toponymEnt.getProperty(T_LANGUAGE);
        this.formant = new Formant(toponymEnt.getParent().getId(),"");
        
        this.type = "";
        this.dataset = "";
        this.otherNames = "";
        this.englishTransliteration = "";
    }    
    
    public Long getToponymNo() {
        return toponymNo;
    }

    public void setToponymNo(Long toponymNo) {
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
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.latitude);
        hash = 31 * hash + Objects.hashCode(this.longitude);
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
        final ToponymObject other = (ToponymObject) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
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
