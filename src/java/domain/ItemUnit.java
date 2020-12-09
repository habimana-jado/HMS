
package domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author nizey
 */
@Entity
public class ItemUnit implements Serializable {
    @Id
    private String itemUnitId = UUID.randomUUID().toString();
    private String unitName;
    
    @OneToMany(mappedBy = "itemUnit")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Item> item;

    public String getItemUnitId() {
        return itemUnitId;
    }

    public void setItemUnitId(String itemUnitId) {
        this.itemUnitId = itemUnitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
    
}
