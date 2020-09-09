
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
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@Entity
public class UserDepartment implements Serializable{
    @Id
    private String departmentId = UUID.randomUUID().toString();
    private String departmentName;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    
    @OneToMany(mappedBy = "userDepartment", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Person> user;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public List<Person> getUser() {
        return user;
    }

    public void setUser(List<Person> user) {
        this.user = user;
    }

}
