
package domain;

import enums.EPaymentMode;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Payment implements Serializable {
    @Id
    private String paymentId = UUID.randomUUID().toString();
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Enumerated(EnumType.STRING)
    private EPaymentMode paymentMode;
    private Double amountPaid;
    private String mobileNumber;
    private String status;
    private String billNo;
    
    
    @OneToMany(mappedBy = "payment", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<TableTransaction> tableTransaction;
    
    @ManyToOne
    private Person cashier;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public EPaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(EPaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public List<TableTransaction> getTableTransaction() {
        return tableTransaction;
    }

    public void setTableTransaction(List<TableTransaction> tableTransaction) {
        this.tableTransaction = tableTransaction;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Person getCashier() {
        return cashier;
    }

    public void setCashier(Person cashier) {
        this.cashier = cashier;
    }
    
    
    
}
