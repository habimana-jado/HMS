
package domain;

import enums.EStatus;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class Restaurant implements Serializable{
    @Id
    private String restaurantId = UUID.randomUUID().toString();
    private String name;
    private String address;
    private String phone;
    private String vatNo;
    private String slogan;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    private String logo;
    
    @OneToMany(mappedBy = "restaurant")
//    @Fetch(FetchMode.SUBSELECT)
    private List<TableGroup> tableGroup;
     
    @OneToMany(mappedBy = "restaurant")
//    @Fetch(FetchMode.SUBSELECT)
    private List<TableMaster> tableMaster;
    
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public List<TableGroup> getTableGroup() {
        return tableGroup;
    }

    public void setTableGroup(List<TableGroup> tableGroup) {
        this.tableGroup = tableGroup;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public List<TableMaster> getTableMaster() {
        return tableMaster;
    }

    public void setTableMaster(List<TableMaster> tableMaster) {
        this.tableMaster = tableMaster;
    }
        
}
