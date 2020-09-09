
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class Transfer implements Serializable {
    @Id
    private String transferId = UUID.randomUUID().toString();
    private Double quantity;
    private String unit;
    @Temporal(TemporalType.DATE)
    private Date transferDate;
    private String remarks;
    
    @ManyToOne
    private ItemDescription itemDescription;
    
    @ManyToOne
    private Stock fromStock;
    
    @ManyToOne
    private Stock toStock;
    
    @ManyToOne
    private Person person;

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(ItemDescription itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Stock getFromStock() {
        return fromStock;
    }

    public void setFromStock(Stock fromStock) {
        this.fromStock = fromStock;
    }

    public Stock getToStock() {
        return toStock;
    }

    public void setToStock(Stock toStock) {
        this.toStock = toStock;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    
}
