
package domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class TableGroup implements Serializable{
    @Id
    private String tableGroupId = UUID.randomUUID().toString();
    private String groupName;
    
    @ManyToOne
    private Restaurant restaurant;
    
    @OneToMany(mappedBy = "tableGroup", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<TableMaster> tableMaster;

    public String getTableGroupId() {
        return tableGroupId;
    }

    public void setTableGroupId(String tableGroupId) {
        this.tableGroupId = tableGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<TableMaster> getTableMaster() {
        return tableMaster;
    }

    public void setTableMaster(List<TableMaster> tableMaster) {
        this.tableMaster = tableMaster;
    }
    
    
    
}
