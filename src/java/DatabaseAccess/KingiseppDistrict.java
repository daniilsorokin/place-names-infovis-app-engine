package DatabaseAccess;

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

/**
 *
 * @author abrskva
 */
@Entity
@Table(name = "kingisepp_district")
@NamedQueries({
    @NamedQuery(name = "KingiseppDistrict.findAll", query = "SELECT k FROM KingiseppDistrict k"),
    @NamedQuery(name = "KingiseppDistrict.findByName", query = "SELECT k FROM KingiseppDistrict k WHERE k.name = :name"),
    @NamedQuery(name = "KingiseppDistrict.findByLatitude", query = "SELECT k FROM KingiseppDistrict k WHERE k.latitude = :latitude"),
    @NamedQuery(name = "KingiseppDistrict.findByLongitude", query = "SELECT k FROM KingiseppDistrict k WHERE k.longitude = :longitude"),
    @NamedQuery(name = "KingiseppDistrict.findByGroupName", query = "SELECT k FROM KingiseppDistrict k WHERE k.groupName = :groupName"),
    @NamedQuery(name = "KingiseppDistrict.findByOtherNames", query = "SELECT k FROM KingiseppDistrict k WHERE k.otherNames = :otherNames"),
    @NamedQuery(name = "KingiseppDistrict.findById", query = "SELECT k FROM KingiseppDistrict k WHERE k.id = :id"),
    @NamedQuery(name = "KingiseppDistrict.findAllByIds", query = "SELECT k FROM KingiseppDistrict k WHERE k.id IN :ids"),
    @NamedQuery(name = "KingiseppDistrict.findAllGroups", query = "SELECT k.groupName FROM KingiseppDistrict k GROUP BY k.groupName"),
    @NamedQuery(name = "KingiseppDistrict.findLatLngByIds", query = "SELECT k.latitude, k.longitude FROM KingiseppDistrict k WHERE k.id IN :ids"),
    @NamedQuery(name = "KingiseppDistrict.findLatLngByGroupName", query = "SELECT k.latitude, k.longitude FROM KingiseppDistrict k WHERE k.groupName = :groupName")})
public class KingiseppDistrict implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "other_names")
    private String otherNames;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public KingiseppDistrict() {
    }

    public KingiseppDistrict(Integer id) {
        this.id = id;
    }

    public KingiseppDistrict(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        if (!(object instanceof KingiseppDistrict)) {
            return false;
        }
        KingiseppDistrict other = (KingiseppDistrict) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DatabaseAccess.KingiseppDistrict[id=" + id + "]";
    }

}
