package de.uni.tuebingen.sfs.toponym.clusters.visualization.entity;

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
    private Integer formantNo;
    private String formantName;
    private List<String> affixList;
    private List<ToponymObject> toponymObjectList;
    
    protected Formant() {}

    public Formant(String formantName) {
        this.formantName = formantName;
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
        List<Integer> toponymObjectIdList = new ArrayList<>();
        for (ToponymObject toponymObject : toponymObjectList) {
            toponymObjectIdList.add(toponymObject.getToponymNo());
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
