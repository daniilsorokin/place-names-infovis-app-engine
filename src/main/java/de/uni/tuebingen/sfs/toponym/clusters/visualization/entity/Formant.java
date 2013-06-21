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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "formants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formant.findAll", query = "SELECT f FROM Formant f"),
    @NamedQuery(name = "Formant.findByFormantNo", query = "SELECT f FROM Formant f WHERE f.formantNo = :formantNo"),
    @NamedQuery(name = "Formant.findByFormantName", query = "SELECT f FROM Formant f WHERE f.formantName = :formantName")})
public class Formant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "formant_no")
    private Integer formantNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "formant_name")
    private String formantName;
    @ManyToMany(mappedBy = "formantList")
    private List<Affix> affixList;
    @OneToMany(mappedBy = "formant")
    private List<ToponymObject> toponymObjectList;

    public Formant() {
    }

    public Formant(Integer formantNo) {
        this.formantNo = formantNo;
    }

    public Formant(Integer formantNo, String formantName) {
        this.formantNo = formantNo;
        this.formantName = formantName;
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
    @JsonIgnore
    public List<Affix> getAffixList() {
        return affixList;
    }

    public void setAffixList(List<Affix> affixList) {
        this.affixList = affixList;
    }

    @XmlTransient
    @JsonIgnore
    public List<ToponymObject> getToponymObjectList() {
        return toponymObjectList;
    }

    public void setToponymObjectList(List<ToponymObject> toponymObjectList) {
        this.toponymObjectList = toponymObjectList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formantNo != null ? formantNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Formant[ formantNo=" + formantNo + " ]";
    }

}
