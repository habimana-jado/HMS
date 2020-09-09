
package domain;

import enums.EStatus;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Jean de Dieu @2020
 */
@Entity
public class Account implements Serializable {
    @Id
    private String accountId = UUID.randomUUID().toString();
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    
    @OneToOne
    private Person person;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
