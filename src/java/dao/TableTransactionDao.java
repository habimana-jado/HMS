
package dao;

import domain.TableMaster;
import domain.TableTransaction;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class TableTransactionDao extends GenericDao<TableTransaction>{
    
    public List<TableTransaction> findByTableAndStatus(TableMaster table, String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM TableTransaction a WHERE a.status = :status AND a.tableMaster = :table");
        q.setParameter("status", status);
        q.setParameter("table", table);
        List<TableTransaction> list = q.list();
        s.close();
        return list;
    }
}
