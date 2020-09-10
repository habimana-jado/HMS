
package dao;

import domain.ItemDescription;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class ItemDescriptionDao extends GenericDao<ItemDescription>{
    public List<ItemDescription> findByStatus(String key){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM ItemDescription a WHERE a.status = :name");
        q.setParameter("name", key );
        List<ItemDescription> list = q.list();
        s.close();
        return list;
    }
}
