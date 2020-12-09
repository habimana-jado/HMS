
package domain;

import enums.EStatus;
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
import javax.persistence.OneToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */

@Entity
public class Person implements Serializable{
    @Id
    private String userId = UUID.randomUUID().toString();
    private String names;
    private String designation;
    private String address;
    private String phone;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    private String username;
    private String password;
    
    @ManyToOne
    private UserDepartment userDepartment;
    
    @OneToMany(mappedBy = "person")
    @Fetch(FetchMode.SUBSELECT)
    private List<Transfer> transfer;
    
    @OneToMany(mappedBy = "person")
//    @Fetch(FetchMode.SUBSELECT)
    private List<TableMaster> tableMaster;
     
    @OneToMany(mappedBy = "cashier")
//    @Fetch(FetchMode.SUBSELECT)
    private List<Payment> payment;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public UserDepartment getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(UserDepartment userDepartment) {
        this.userDepartment = userDepartment;
    }

    public List<Transfer> getTransfer() {
        return transfer;
    }

    public void setTransfer(List<Transfer> transfer) {
        this.transfer = transfer;
    }

    public List<TableMaster> getTableMaster() {
        return tableMaster;
    }

    public void setTableMaster(List<TableMaster> tableMaster) {
        this.tableMaster = tableMaster;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

}
