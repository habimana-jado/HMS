package uimodel;

import dao.ItemDao;
import dao.PersonDao;
import dao.TableGroupDao;
import dao.TableMasterDao;
import dao.TableTransactionDao;
import dao.UserDepartmentDao;
import domain.Account;
import domain.Item;
import domain.Payment;
import domain.Person;
import domain.TableGroup;
import domain.TableMaster;
import domain.TableTransaction;
import domain.UserDepartment;
import enums.ETableStatus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@ManagedBean
@SessionScoped
public class RestaurantModel {

    private Account loggedInUser = new Account();
    private Item item = new Item();
    private List<Item> items = new ItemDao().findAll(Item.class);
    private TableTransaction tableTransaction = new TableTransaction();
    private String personId = new String();
    private Payment payment = new Payment();
    private List<TableGroup> tableGroups = new TableGroupDao().findAll(TableGroup.class);
    private List<TableMaster> tableMasters = new TableMasterDao().findAll(TableMaster.class);
    private List<TableTransaction> tableTransactions = new ArrayList<>();
    private TableMaster chosenTableMaster = new TableMaster();
    private List<TableTransaction> tableTransactions1 = new ArrayList<>();
    private List<TableMaster> vacantTableMasters = new TableMasterDao().findByStatus(ETableStatus.VACANT);
    private List<TableMaster> billedTableTransactions = new TableTransactionDao().findByTableStatus(ETableStatus.BILLED, "Billed");
    private List<TableMaster> fullTableTransactions = new TableTransactionDao().findByTableStatus(ETableStatus.FULL, "Sent");
    private UserDepartment waitery = new UserDepartmentDao().findByDepartment("Waiter");
    private List<Person> waiters = new PersonDao().findByDepartment(waitery);
    private String waiterId = new String();
    private List<Item> suggestedItems = new ArrayList<>();
    private String itemSearchKeyWord = new String();
    private Item itemChosen = new Item();
    private TableMasterDao tableDao = new TableMasterDao();
    private String newDate = new SimpleDateFormat("dd MMM yyyy").format(new Date());
    private Double totalBilledBeverage = 0.0;
    private Double totalBilledFoods = 0.0;
    private Double dailyCollection = 0.0;
    private Double dailyBilled = 0.0;
    
    @PostConstruct
    public void init() {
        userInit();
        tableMasters = new TableMasterDao().findAll(TableMaster.class);
        tableGroups = new TableGroupDao().findAll(TableGroup.class);
        vacantTableMasters = new TableMasterDao().findByStatus(ETableStatus.VACANT);
        billedTableTransactions = new TableTransactionDao().findByTableStatus(ETableStatus.BILLED, "Billed");
        fullTableTransactions = new TableTransactionDao().findByTableStatus(ETableStatus.FULL, "Sent");
        waiters = new PersonDao().findByDepartment(waitery);
    }

    public void userInit() {
        loggedInUser = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    }

    public void populateTableTransactions(TableMaster tableMaster) {
        
        chosenTableMaster = tableMaster;
        tableTransactions = new ArrayList<>();
    }
    
    public void populateSentTableTransactions(TableMaster tableMaster) {
        totalBilledBeverage = new TableTransactionDao().findTotalByTableAndStatus(tableMaster, "Sent", "Beverage");
        totalBilledFoods = new TableTransactionDao().findTotalByTableAndStatus(tableMaster, "Sent", "Food");
        
        chosenTableMaster = tableMaster;
        tableTransactions = new TableTransactionDao().findByTableAndStatus(tableMaster, "Sent");
    }
    
    public void populateBilledTableTransactions(TableMaster tableMaster) {
        
        chosenTableMaster = tableMaster;
        tableTransactions = new TableTransactionDao().findByTableAndStatus(tableMaster, "Billed");
    }
        
    public void removeUnsavedTransactions(){
        for(TableTransaction t: new TableTransactionDao().findByStatus("Pending")){
            new TableTransactionDao().delete(t);
        }
    }

    public void registerTransaction() {
        try {
            tableTransaction.setTransactionDate(new Date());
            tableTransaction.setStatus("Pending");
            tableTransaction.setPerson(new PersonDao().findOne(Person.class, waiterId));
            tableTransaction.setTableMaster(chosenTableMaster);
            tableTransaction.setItem(itemChosen);
            new TableTransactionDao().register(tableTransaction);

            tableTransactions = new TableTransactionDao().findByTableAndStatus(chosenTableMaster, "Pending");

            tableTransaction = new TableTransaction();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeTransaction() {
        try {
            TableMaster master = new TableMaster();
            
            for (TableTransaction table : tableTransactions) {
                System.out.println("KOT: "+table.getKotRemarks());
                table.setStatus("Sent");
                new TableTransactionDao().update(table);

                master = table.getTableMaster();
                master.setTableStatus(ETableStatus.FULL);
                tableDao.update(master);
            }

            tableTransactions = new TableTransactionDao().findByTableAndStatus(chosenTableMaster, "Pending");

            vacantTableMasters = new TableMasterDao().findByStatus(ETableStatus.VACANT);
            billedTableTransactions = new TableTransactionDao().findByTableStatus(ETableStatus.BILLED, "Billed");
            fullTableTransactions = new TableTransactionDao().findByTableStatus(ETableStatus.FULL, "Sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchItems() {
        try {
            if (itemSearchKeyWord.isEmpty() || itemSearchKeyWord == null) {
                suggestedItems = new ArrayList<>();
            } else {
                suggestedItems = new ItemDao().findLikeName(itemSearchKeyWord.toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseItem(Item it) {
        itemChosen = it;
        suggestedItems = new ArrayList<>();
        itemSearchKeyWord = itemChosen.getItemName();
    }

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Account loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public TableTransaction getTableTransaction() {
        return tableTransaction;
    }

    public void setTableTransaction(TableTransaction tableTransaction) {
        this.tableTransaction = tableTransaction;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<TableGroup> getTableGroups() {
        return tableGroups;
    }

    public void setTableGroups(List<TableGroup> tableGroups) {
        this.tableGroups = tableGroups;
    }

    public List<TableMaster> getTableMasters() {
        return tableMasters;
    }

    public void setTableMasters(List<TableMaster> tableMasters) {
        this.tableMasters = tableMasters;
    }

    public List<TableTransaction> getTableTransactions() {
        return tableTransactions;
    }

    public void setTableTransactions(List<TableTransaction> tableTransactions) {
        this.tableTransactions = tableTransactions;
    }

    public TableMaster getChosenTableMaster() {
        return chosenTableMaster;
    }

    public void setChosenTableMaster(TableMaster chosenTableMaster) {
        this.chosenTableMaster = chosenTableMaster;
    }

    public List<TableTransaction> getTableTransactions1() {
        return tableTransactions1;
    }

    public void setTableTransactions1(List<TableTransaction> tableTransactions1) {
        this.tableTransactions1 = tableTransactions1;
    }

    public List<TableMaster> getVacantTableMasters() {
        return vacantTableMasters;
    }

    public void setVacantTableMasters(List<TableMaster> vacantTableMasters) {
        this.vacantTableMasters = vacantTableMasters;
    }

    public List<TableMaster> getBilledTableTransactions() {
        return billedTableTransactions;
    }

    public void setBilledTableTransactions(List<TableMaster> billedTableTransactions) {
        this.billedTableTransactions = billedTableTransactions;
    }

    public List<TableMaster> getFullTableTransactions() {
        return fullTableTransactions;
    }

    public void setFullTableTransactions(List<TableMaster> fullTableTransactions) {
        this.fullTableTransactions = fullTableTransactions;
    }

    public List<Person> getWaiters() {
        return waiters;
    }

    public void setWaiters(List<Person> waiters) {
        this.waiters = waiters;
    }

    public UserDepartment getWaitery() {
        return waitery;
    }

    public void setWaitery(UserDepartment waitery) {
        this.waitery = waitery;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public List<Item> getSuggestedItems() {
        return suggestedItems;
    }

    public void setSuggestedItems(List<Item> suggestedItems) {
        this.suggestedItems = suggestedItems;
    }

    public String getItemSearchKeyWord() {
        return itemSearchKeyWord;
    }

    public void setItemSearchKeyWord(String itemSearchKeyWord) {
        this.itemSearchKeyWord = itemSearchKeyWord;
    }

    public Item getItemChosen() {
        return itemChosen;
    }

    public void setItemChosen(Item itemChosen) {
        this.itemChosen = itemChosen;
    }

    public TableMasterDao getTableDao() {
        return tableDao;
    }

    public void setTableDao(TableMasterDao tableDao) {
        this.tableDao = tableDao;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public Double getTotalBilledBeverage() {
        return totalBilledBeverage;
    }

    public void setTotalBilledBeverage(Double totalBilledBeverage) {
        this.totalBilledBeverage = totalBilledBeverage;
    }

    public Double getTotalBilledFoods() {
        return totalBilledFoods;
    }

    public void setTotalBilledFoods(Double totalBilledFoods) {
        this.totalBilledFoods = totalBilledFoods;
    }

    public Double getDailyCollection() {
        return dailyCollection;
    }

    public void setDailyCollection(Double dailyCollection) {
        this.dailyCollection = dailyCollection;
    }

    public Double getDailyBilled() {
        return dailyBilled;
    }

    public void setDailyBilled(Double dailyBilled) {
        this.dailyBilled = dailyBilled;
    }

}
