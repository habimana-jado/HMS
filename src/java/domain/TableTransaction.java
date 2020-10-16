
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class TableTransaction implements Serializable {
    @Id
    private String transactionId = UUID.randomUUID().toString();
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private Double quantity;
    private String kotRemarks;
    private String status;
    private Double totalPrice;
    private String printStatus;
    
    @ManyToOne
    private TableMaster tableMaster;
    
    @OneToMany(mappedBy = "tableTransaction", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Payment> payment;

    @ManyToOne
    private Item item;
    
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getKotRemarks() {
        return kotRemarks;
    }

    public void setKotRemarks(String kotRemarks) {
        this.kotRemarks = kotRemarks;
    }

    public TableMaster getTableMaster() {
        return tableMaster;
    }

    public void setTableMaster(TableMaster tableMaster) {
        this.tableMaster = tableMaster;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }
    
}
