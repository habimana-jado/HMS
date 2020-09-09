
package dao;

import common.PassCode;
import domain.Account;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
public class AccountDao extends GenericDao<Account>{
         
    public List<Account> loginencrypt(String u, String pass) throws Exception {

        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Account> list = new ArrayList<>();

        List<Account> users = new AccountDao().findAll(Account.class);
        String z = "";
        for (Account us : users) {
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
