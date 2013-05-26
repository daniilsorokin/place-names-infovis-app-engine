/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniil Sorokin <daniil.sorokin@uni-tuebingen.de>
 */
@Entity
@Table(name = "kingisepp_district")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Toponym.findAll", query = "SELECT t FROM Toponym t"),
    @NamedQuery(name = "Toponym.findByName", query = "SELECT t FROM Toponym t WHERE t.name = :name"),
    @NamedQuery(name = "Toponym.findByLatitude", query = "SELECT t FROM Toponym t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "Toponym.findByLongitude", query = "SELECT t FROM Toponym t WHERE t.longitude = :longitude"),
    @NamedQuery(name = "Toponym.findByGroupName", query = "SELECT t FROM Toponym t WHERE t.groupName = :groupName"),
    @NamedQuery(name = "Toponym.findByOtherNames", query = "SELECT t FROM Toponym t WHERE t.otherNames = :otherNames"),
    @NamedQuery(name = "Toponym.findById", query = "SELECT t FROM Toponym t WHERE t.id = :id")})
public class Toponym implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Size(max = 2147483647)
    @Column(name = "group_name")
    private String groupName;
    @Size(max = 2147483647)
    @Column(name = "other_names")
    private String otherNames;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Toponym() {
    }

    public Toponym(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Toponym)) {
            return false;
        }
        Toponym other = (Toponym) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.uni.tuebingen.sfs.toponym.clusters.visualization.impl.Toponym[ id=" + id + " ]";
    }
    
}
