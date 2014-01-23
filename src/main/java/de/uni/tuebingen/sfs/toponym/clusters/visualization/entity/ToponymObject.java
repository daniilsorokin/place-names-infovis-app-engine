package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Entity
@Table(name = "toponym_objects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ToponymObject.findAll", query = "SELECT t FROM ToponymObject t"),
    @NamedQuery(name = "ToponymObject.findByToponymNo", query = "SELECT t FROM ToponymObject t WHERE t.toponymNo = :toponymNo"),
    @NamedQuery(name = "ToponymObject.findByName", query = "SELECT t FROM ToponymObject t WHERE t.name = :name"),
    @NamedQuery(name = "ToponymObject.findByOtherNames", query = "SELECT t FROM ToponymObject t WHERE t.otherNames = :otherNames"),
    @NamedQuery(name = "ToponymObject.findByLatitude", query = "SELECT t FROM ToponymObject t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "ToponymObject.findByLongitude", query = "SELECT t FROM ToponymObject t WHERE t.longitude = :longitude")})
public class ToponymObject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "toponym_no")
    private Integer toponymNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    private String name;
    @Size(max = 2147483647)
    @Column(name = "other_names")
    private String otherNames;
    @Column(name = "english_transliteration")
    private String englishTransliteration;    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @ManyToMany(mappedBy = "toponymObjectList")
    private List<Affix> affixList;
    @JoinColumn(name = "type", referencedColumnName = "type_no")
    @ManyToOne
    private ToponymType type;
    @JoinColumn(name = "language", referencedColumnName = "language_no")
    @ManyToOne
    private Language language;
    @JoinColumn(name = "formant", referencedColumnName = "formant_no")
    @ManyToOne
    private Formant formant;
    @JoinColumn(name = "dataset", referencedColumnName = "dataset_no")
    @ManyToOne
    private Dataset dataset;

    protected ToponymObject() {
    }

    public ToponymObject(String name) {
        this.name = name;
    }

    public Integer getToponymNo() {
        return toponymNo;
    }

    public String getName() {
        return name;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @XmlTransient
    @JsonIgnore
    public List<Affix> getAffixList() {
        return affixList;
    }

    public ToponymType getType() {
        return type;
    }

    public Language getLanguage() {
        return language;
    }

    public String getEnglishTransliteration() {
        return englishTransliteration;
    }   
    
    public Formant getFormant() {
        return formant;
    }

    public Dataset getDataset() {
        return dataset;
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
        return "ToponymObject[ toponymNo=" + toponymNo + "  name=" + name + " ]";
    }

}
