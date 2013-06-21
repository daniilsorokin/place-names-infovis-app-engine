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
@Table(name = "toponym_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ToponymType.findAll", query = "SELECT t FROM ToponymType t"),
    @NamedQuery(name = "ToponymType.findByTypeNo", query = "SELECT t FROM ToponymType t WHERE t.typeNo = :typeNo"),
    @NamedQuery(name = "ToponymType.findByName", query = "SELECT t FROM ToponymType t WHERE t.name = :name"),
    @NamedQuery(name = "ToponymType.findByNameRus", query = "SELECT t FROM ToponymType t WHERE t.nameRus = :nameRus")})
public class ToponymType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "type_no")
    private Integer typeNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    private String name;
    @Size(max = 2147483647)
    @Column(name = "name_rus")
    private String nameRus;
    @OneToMany(mappedBy = "type")
    private List<ToponymObject> toponymObjectList;

    public ToponymType() {
    }

    public ToponymType(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public ToponymType(Integer typeNo, String name) {
        this.typeNo = typeNo;
        this.name = name;
    }

    public Integer getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
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
        hash += (typeNo != null ? typeNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ToponymType)) {
            return false;
        }
        ToponymType other = (ToponymType) object;
        if ((this.typeNo == null && other.typeNo != null) || (this.typeNo != null && !this.typeNo.equals(other.typeNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.ToponymType[ typeNo=" + typeNo + " ]";
    }

}
