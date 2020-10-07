
package dao;

import common.PassCode;
import domain.Item;
import domain.Person;
import domain.UserDepartment;
import enums.EStatus;
import java.util.ArrayList;
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
        Query q = s.createQuery("SELECT a FROM Person a WHERE a.userDepartment = :name AND a.status = :st");
        q.setParameter("name", department);
        q.setParameter("st", EStatus.ACTIVE);
        List<Person> list = q.list();
        s.close();
        return list;
    }
       
    public List<Person> loginencrypt(String u, String pass) throws Exception {

        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Person> list = new ArrayList<>();

        List<Person> users = new PersonDao().findAll(Person.class);
        String z = "";
        for (Person us : users) {
            if (us.getUsername().matches(u)) {
                if ((new PassCode().decrypt(us.getPassword())).matches(pass)) {
                    list.add(us);
                }
            }
        }
        s.close();
        return list;

    }
}
