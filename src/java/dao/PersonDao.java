
package dao;

import domain.Item;
import domain.Person;
import domain.UserDepartment;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class PersonDao extends GenericDao<Person>{
   public List<Person> findByDepartment(UserDepartment department){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Person a WHERE a.userDepartment = :name");
        q.setParameter("name", department);
        List<Person> list = q.list();
        s.close();
        return list;
    }
}
