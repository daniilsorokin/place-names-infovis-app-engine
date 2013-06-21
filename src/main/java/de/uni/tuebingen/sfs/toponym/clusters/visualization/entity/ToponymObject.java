/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double latitude;
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

    public ToponymObject() {
    }

    public ToponymObject(Integer toponymNo) {
        this.toponymNo = toponymNo;
    }

    public ToponymObject(Integer toponymNo, String name) {
        this.toponymNo = toponymNo;
        this.name = name;
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
    @JsonIgnore
    public List<Affix> getAffixList() {
        return affixList;
    }

    public void setAffixList(List<Affix> affixList) {
        this.affixList = affixList;
    }

    public ToponymType getType() {
        return type;
    }

    public void setType(ToponymType type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Formant getFormant() {
        return formant;
    }

    public void setFormant(Formant formant) {
        this.formant = formant;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymObject[ toponymNo=" + toponymNo + " ]";
    }

}
