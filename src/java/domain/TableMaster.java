
package domain;

import enums.ETableStatus;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class TableMaster implements Serializable{
    @Id
    private String tableMasterId = UUID.randomUUID().toString();
    private String tableNo;
    private int chairNo;
    @Enumerated(EnumType.STRING)
    private ETableStatus tableStatus;
    private String type;
    
    @ManyToOne
    private TableGroup tableGroup;

    @ManyToOne
    private Restaurant restaurant;
    
    @OneToMany(mappedBy = "tableMaster")
//    @Fetch(FetchMode.SUBSELECT)
    private List<TableTransaction> tableTransaction;
    
    
    @ManyToOne
    private Person person;
    
     
    public String getTableMasterId() {
        return tableMasterId;
    }

    public void setTableMasterId(String tableMasterId) {
        this.tableMasterId = tableMasterId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public int getChairNo() {
        return chairNo;
    }

    public void setChairNo(int chairNo) {
        this.chairNo = chairNo;
    }

    public ETableStatus getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(ETableStatus tableStatus) {
        this.tableStatus = tableStatus;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public void setTableGroup(TableGroup tableGroup) {
        this.tableGroup = tableGroup;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<TableTransaction> getTableTransaction() {
        return tableTransaction;
    }

    public void setTableTransaction(List<TableTransaction> tableTransaction) {
        this.tableTransaction = tableTransaction;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
