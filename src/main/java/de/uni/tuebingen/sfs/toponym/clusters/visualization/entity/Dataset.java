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
@Table(name = "datasets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dataset.findAll", query = "SELECT d FROM Dataset d"),
    @NamedQuery(name = "Dataset.findByDatasetNo", query = "SELECT d FROM Dataset d WHERE d.datasetNo = :datasetNo"),
    @NamedQuery(name = "Dataset.findByName", query = "SELECT d FROM Dataset d WHERE d.name = :name"),
    @NamedQuery(name = "Dataset.findByInfo", query = "SELECT d FROM Dataset d WHERE d.info = :info")})
public class Dataset implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dataset_no")
    private Integer datasetNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    private String name;
    @Size(max = 2147483647)
    private String info;
    @OneToMany(mappedBy = "dataset")
    private List<ToponymObject> toponymObjectList;

    public Dataset() {
    }

    public Dataset(Integer datasetNo) {
        this.datasetNo = datasetNo;
    }

    public Dataset(Integer datasetNo, String name) {
        this.datasetNo = datasetNo;
        this.name = name;
    }

    public Integer getDatasetNo() {
        return datasetNo;
    }

    public void setDatasetNo(Integer datasetNo) {
        this.datasetNo = datasetNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        hash += (datasetNo != null ? datasetNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dataset)) {
            return false;
        }
        Dataset other = (Dataset) object;
        if ((this.datasetNo == null && other.datasetNo != null) || (this.datasetNo != null && !this.datasetNo.equals(other.datasetNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.uni.tuebingen.sfs.toponym.clusters.visualization.entity.Dataset[ datasetNo=" + datasetNo + " ]";
    }

}
