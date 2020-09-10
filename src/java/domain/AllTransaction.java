
package domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class AllTransaction implements Serializable {
    @Id
    private String allTransactionId = UUID.randomUUID().toString();
    
    @ManyToOne
    private TableMaster tableMaster;
    
    @ManyToOne
    private TableTransaction tableTransaction;

    @ManyToOne
    private Person person;
    
    public String getAllTransactionId() {
        return allTransactionId;
    }

    public void setAllTransactionId(String allTransactionId) {
        this.allTransactionId = allTransactionId;
    }

    public TableMaster getTableMaster() {
        return tableMaster;
    }

    public void setTableMaster(TableMaster tableMaster) {
        this.tableMaster = tableMaster;
    }

    public TableTransaction getTableTransaction() {
        return tableTransaction;
    }

    public void setTableTransaction(TableTransaction tableTransaction) {
        this.tableTransaction = tableTransaction;
    }
    
   
    
}
