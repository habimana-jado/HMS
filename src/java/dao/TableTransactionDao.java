
package dao;

import domain.TableMaster;
import domain.TableTransaction;
import enums.ETableStatus;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
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
    
    public List<TableTransaction> findByTableAndOrStatus(TableMaster table, String status, String stat){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM TableTransaction a WHERE a.status = :status OR a.status = :stat AND a.tableMaster = :table");
        q.setParameter("status", status);
        q.setParameter("stat", stat);
        q.setParameter("table", table);
        List<TableTransaction> list = q.list();
        s.close();
        return list;
    }       
    
    public Double findTotalByTableAndStatus(TableMaster table, String status, String menuType){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT SUM(a.totalPrice) FROM TableTransaction a WHERE a.status = :status AND a.tableMaster = :table AND a.item.menuType = :type");
        q.setParameter("status", status);
        q.setParameter("table", table);
        q.setParameter("type", menuType);
        Double list = (Double) q.uniqueResult();
        s.close();
        return list;
    }
       
    public Double findTotalByDate(Date dt){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT SUM(a.totalPrice) FROM TableTransaction a WHERE a.transactionDate = :x");
        q.setParameter("x", dt);
        Double list = (Double) q.uniqueResult();
        s.close();
        return list;
    }
         
    public Double findTotalByDateAndTableStatus(Date dt, String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT SUM(a.totalPrice) FROM TableTransaction a WHERE a.transactionDate = :x AND a.status = :stat");
        q.setParameter("x", dt);
        q.setParameter("stat", status);
        Double list = (Double) q.uniqueResult();
        s.close();
        return list;
    }
       
    public List<TableMaster> findByTableStatus(ETableStatus tableStatus, String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT DISTINCT a.tableMaster FROM TableTransaction a WHERE a.status= :status AND a.tableMaster.tableStatus = :tableStatus");
        q.setParameter("status", status);
        q.setParameter("tableStatus", tableStatus);
        q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<TableMaster> list = q.list();        
        s.close();
        return list;
    }
    
    public List<TableTransaction> findByStatus(String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM TableTransaction a WHERE a.status= :status");
        q.setParameter("status", status);
        List<TableTransaction> list = q.list();        
        s.close();
        return list;
    }
    
}
