
package dao;

import domain.Restaurant;
import domain.TableGroup;
import domain.TableMaster;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class TableMasterDao extends GenericDao<TableMaster>{
    
    public List<TableMaster> findByRestaurant(Restaurant restaurant){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM TableMaster a WHERE a.tableGroup = :x");
        q.setParameter("x", restaurant);
        List<TableMaster> list = q.list();
        s.close();
        return list;
    }
}
