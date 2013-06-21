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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "affixes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Affix.findAll", query = "SELECT a FROM Affix a"),
    @NamedQuery(name = "Affix.findByAffixNo", query = "SELECT a FROM Affix a WHERE a.affixNo = :affixNo"),
    @NamedQuery(name = "Affix.findByAffix", query = "SELECT a FROM Affix a WHERE a.affix = :affix")})
public class Affix implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "affix_no")
    private Integer affixNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    private String affix;
    @JoinTable(name = "affix_toponym_items", joinColumns = {
        @JoinColumn(name = "affix_no", referencedColumnName = "affix_no")}, inverseJoinColumns = {
        @JoinColumn(name = "toponym_no", referencedColumnName = "toponym_no")})
    @ManyToMany
    private List<ToponymObject> toponymObjectList;
    @JoinTable(name = "affix_formant_items", joinColumns = {
        @JoinColumn(name = "affix_no", referencedColumnName = "affix_no")}, inverseJoinColumns = {
        @JoinColumn(name = "formant_no", referencedColumnName = "formant_no")})
    @ManyToMany
    private List<Formant> formantList;

    public Affix() {
    }

    public Affix(Integer affixNo) {
        this.affixNo = affixNo;
    }

    public Affix(Integer affixNo, String affix) {
        this.affixNo = affixNo;
        this.affix = affix;
    }

    public Integer getAffixNo() {
        return affixNo;
    }

    public void setAffixNo(Integer affixNo) {
        this.affixNo = affixNo;
    }

    public String getAffix() {
        return affix;
    }

    public void setAffix(String affix) {
        this.affix = affix;
    }

    @XmlTransient
    @JsonIgnore
    public List<ToponymObject> getToponymObjectList() {
        return toponymObjectList;
    }

    public void setToponymObjectList(List<ToponymObject> toponymObjectList) {
        this.toponymObjectList = toponymObjectList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Formant> getFormantList() {
        return formantList;
    }

    public void setFormantList(List<Formant> formantList) {
        this.formantList = formantList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (affixNo != null ? affixNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Affix)) {
            return false;
        }
        Affix other = (Affix) object;
        if ((this.affixNo == null && other.affixNo != null) || (this.affixNo != null && !this.affixNo.equals(other.affixNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Affix[ affixNo=" + affixNo + " ]";
    }

}
