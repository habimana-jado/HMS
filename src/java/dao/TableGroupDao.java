
package dao;

import domain.Restaurant;
import domain.TableGroup;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class TableGroupDao extends GenericDao<TableGroup>{
    
    public List<TableGroup> findByRestaurant(Restaurant restaurant){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM TableGroup a WHERE a.restaurant = :x");
        q.setParameter("x", restaurant);
        List<TableGroup> list = q.list();
        s.close();
        return list;
    }
}
