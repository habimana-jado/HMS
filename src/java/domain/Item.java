
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
public class Item implements Serializable {
    @Id
    private String itemId = UUID.randomUUID().toString();
    private String itemName;
    private Double unitRate;
    private String menuType;
    
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<ItemDescription> itemDescription;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<TableTransaction> tableTransaction;
            
    @ManyToOne
    private ItemCategory itemCategory;
    
    @ManyToOne
    private ItemUnit itemUnit;
    
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<ItemDescription> getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(List<ItemDescription> itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(Double unitRate) {
        this.unitRate = unitRate;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public ItemUnit getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(ItemUnit itemUnit) {
        this.itemUnit = itemUnit;
    }

    public List<TableTransaction> getTableTransaction() {
        return tableTransaction;
    }

    public void setTableTransaction(List<TableTransaction> tableTransaction) {
        this.tableTransaction = tableTransaction;
    }
    
    
}
