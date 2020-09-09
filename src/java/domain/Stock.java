
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
 * @author  Jean de Dieu HABIMANA @2020
 */
@Entity
public class Stock implements Serializable {
    @Id
    private String stockId = UUID.randomUUID().toString();
    private String stockName;
    private Boolean isDefault;
    
    @OneToMany(mappedBy = "stock", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Purchase> purchase;

    @OneToMany(mappedBy = "toStock", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Transfer> transfer;
    
    @OneToMany(mappedBy = "fromStock", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Transfer> transfer1;
    
    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public List<Purchase> getPurchase() {
        return purchase;
    }

    public void setPurchase(List<Purchase> purchase) {
        this.purchase = purchase;
    }

    public List<Transfer> getTransfer() {
        return transfer;
    }

    public void setTransfer(List<Transfer> transfer) {
        this.transfer = transfer;
    }

    public List<Transfer> getTransfer1() {
        return transfer1;
    }

    public void setTransfer1(List<Transfer> transfer1) {
        this.transfer1 = transfer1;
    }
    
    
}
