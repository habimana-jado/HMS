
package dao;

import domain.Payment;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class PaymentDao extends GenericDao<Payment>{
    
    public List<Payment> findByTransactionDate(Date dt){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Payment a WHERE a.paymentDate = :dt");
        q.setParameter("dt", dt);
        List<Payment> list = q.list();
        s.close();
        return list;
    }
}
