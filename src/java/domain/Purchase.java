
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
 * @author  Jean de Dieu HABIMANA @2020
 */
@Entity
public class Purchase implements Serializable {
    @Id
    private String purchaseId = UUID.randomUUID().toString();
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;
    private String ebmNo;
    private String invoiceNo;
    
    @ManyToOne
    private Vendor vendor;
    
    @ManyToOne
    private ItemDescription itemDescription;
    
    @ManyToOne
    private Stock stock;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getEbmNo() {
        return ebmNo;
    }

    public void setEbmNo(String ebmNo) {
        this.ebmNo = ebmNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(ItemDescription itemDescription) {
        this.itemDescription = itemDescription;
    }
    
    
}
