
package common;

import dao.AccountDao;
import dao.HibernateUtil;
import dao.HotelConfigDao;
import dao.PersonDao;
import dao.TableMasterDao;
import dao.TableTransactionDao;
import dao.UserDepartmentDao;
import domain.Account;
import domain.HotelConfig;
import domain.Person;
import domain.TableMaster;
import domain.TableTransaction;
import domain.UserDepartment;
import enums.EStatus;
import enums.ETableStatus;
import java.util.List;
import uimodel.RestaurantModel;

public class Test {
    public static void main(String[] args) throws Exception {
        
//        HibernateUtil.getSessionFactory().openSession();
//        HibernateUtil.getSessionFactory().close();
//        UserDepartment dep = new UserDepartment();
//        dep.setDepartmentName("ADMINISTRATOR");
//        dep.setStatus(EStatus.ACTIVE);
//        new UserDepartmentDao().register(dep);        
        UserDepartment dep = new UserDepartmentDao().findOne(UserDepartment.class, "6fbcc92c-30af-4f5f-8509-93f31819de07");
        Person u = new Person();
        u.setUserDepartment(dep);
        u.setNames("Kalisa");
        u.setPhone("0788909884");
        u.setStatus(EStatus.ACTIVE);
        new PersonDao().register(u);
        
        Account a = new Account();
        a.setPerson(u);
        a.setUsername("cashier");
        a.setPassword(new PassCode().encrypt("cashier"));
        a.setStatus(EStatus.ACTIVE);
        new AccountDao().register(a);
        
//        HotelConfig h = new HotelConfig();
//        h.setAddressLine1("Kigali-Rwanda");
//        h.setAddressLine2("Kimihurura");
//        h.setAddressLine3("Rugando");
//        h.setCurrency("RWF");
//        h.setEmail("hotel@gmail.com");
//        h.setHotelName("Hotel Test");
//        h.setSlogan("Slogan Test");
//        h.setStreetNo("KK. 324 St");
//        new HotelConfigDao().register(h);

//        List<TableMaster> list = new TableTransactionDao().findByTableStatus(ETableStatus.FULL, "Sent");
//        for(TableMaster t: list){
//            System.out.println(t.getTableNo());
//        }
        
//        for(TableTransaction tr: new TableTransactionDao().findAll(TableTransaction.class)){
//            tr.setTotalPrice(tr.getQuantity() * tr.getItem().getUnitRate());
//            new TableTransactionDao().update(tr);
//        }

//        for(TableMaster tm: new TableTransactionDao().findByTableStatus(ETableStatus.BILLED, "Sent")){
//            System.out.println(tm.getTableNo());
//        }

        new RestaurantModel().generateDailySalesReport();
    }
}
