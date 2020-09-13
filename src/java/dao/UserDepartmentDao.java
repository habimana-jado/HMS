
package dao;

import domain.Person;
import domain.UserDepartment;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class UserDepartmentDao extends GenericDao<UserDepartment>{
     public UserDepartment findByDepartment(String department){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM UserDepartment a WHERE a.departmentName = :name");
        q.setParameter("name", department);
        UserDepartment list = (UserDepartment) q.uniqueResult();
        s.close();
        return list;
    }
}
