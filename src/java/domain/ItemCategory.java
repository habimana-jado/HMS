
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
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class ItemCategory implements Serializable {
    @Id
    private String itemCategoryId = UUID.randomUUID().toString();
    private String categoryName;
    private String underCategory;
    
    @OneToMany(mappedBy = "itemCategory")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Item> item;

    public String getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(String itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUnderCategory() {
        return underCategory;
    }

    public void setUnderCategory(String underCategory) {
        this.underCategory = underCategory;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
    
    
}
