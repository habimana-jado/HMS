package uimodel;

import common.FileUpload;
import common.PassCode;
import dao.HotelConfigDao;
import dao.ItemCategoryDao;
import dao.ItemDao;
import dao.ItemUnitDao;
import dao.PersonDao;
import dao.RestaurantDao;
import dao.TableGroupDao;
import dao.TableMasterDao;
import dao.UserDepartmentDao;
import domain.HotelConfig;
import domain.Item;
import domain.ItemCategory;
import domain.ItemUnit;
import domain.Person;
import domain.Restaurant;
import domain.TableGroup;
import domain.TableMaster;
import domain.UserDepartment;
import enums.EStatus;
import enums.ETableStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@ManagedBean
@SessionScoped
public class AdminModel {

    private Person loggedInUser = new Person();
    private Person user = new Person();
    private HotelConfig hotelConfig = new HotelConfig();
    private String userDepartmentId = new String();
    private List<Person> users = new PersonDao().findAll(Person.class);
    private List<UserDepartment> userDepartments = new UserDepartmentDao().findAll(UserDepartment.class);
    private HotelConfig hotel = new HotelConfigDao().findOne(HotelConfig.class, Long.parseLong("1"));
    private List<String> chosenImage = new ArrayList<>();
    private UserDepartment userDepartment = new UserDepartment();
    private Person editPerson = new Person();
    private List<Restaurant> restaurants = new RestaurantDao().findAll(Restaurant.class);
    private List<TableGroup> tableGroups = new TableGroupDao().findAll(TableGroup.class);
    private List<TableMaster> tableMasters = new TableMasterDao().findAll(TableMaster.class);
    private Restaurant restaurant = new Restaurant();
    private TableGroup tableGroup = new TableGroup();
    private TableMaster tableMaster = new TableMaster();
    private String tableGroupId = new String();
    private String restaurantId = new String();
    private ItemUnit itemUnit = new ItemUnit();
    private List<ItemUnit> itemUnits = new ItemUnitDao().findAll(ItemUnit.class);
    private ItemCategory itemCategory = new ItemCategory();
    private List<ItemCategory> itemCategories = new ItemCategoryDao().findAll(ItemCategory.class);
    private String itemCategoryId = new String();
    private String itemUnitId = new String();
    private Item item = new Item();
    private List<Item> items = new ItemDao().findAll(Item.class);
    private String password = "12345";
    

    @PostConstruct
    public void init() {
        userInit();
        users = new PersonDao().findAll(Person.class);
        restaurants = new RestaurantDao().findAll(Restaurant.class);
        tableGroups = new TableGroupDao().findAll(TableGroup.class);
        tableMasters = new TableMasterDao().findAll(TableMaster.class);
        items = new ItemDao().findAll(Item.class);
        itemCategories = new ItemCategoryDao().findAll(ItemCategory.class);
        itemUnits = new ItemUnitDao().findAll(ItemUnit.class);
    }

    public void userInit() {
        loggedInUser = (Person) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    }

    public void updateAccount(Person p) throws Exception{
        UserDepartment dp = new UserDepartmentDao().findOne(UserDepartment.class, userDepartmentId);
        p.setUserDepartment(dp);
        p.setPassword(new PassCode().encrypt(p.getPassword()));
        new PersonDao().update(p);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("User Updated"));
        
        users = new PersonDao().findAll(Person.class);
    }
    public void block(Person p) {
        System.out.println("Reached Here");
        p.setStatus(EStatus.BLOCKED);
        new PersonDao().update(p);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("User Blocked"));
        users = new PersonDao().findAll(Person.class);
    }

    public void activate(Person p) {
        System.out.println("Reached THere");
        p.setStatus(EStatus.ACTIVE);
        new PersonDao().update(p);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("User Activated"));
        users = new PersonDao().findAll(Person.class);
    }

    public void delete(Person p) {
        new PersonDao().delete(p);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("User Deleted"));

    }

    public void updateHotel() {
        try {
            new HotelConfigDao().update(hotel);
            hotel = new HotelConfigDao().findOne(HotelConfig.class, 1);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Hotel Config Updated"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemCategoryRegister() {
        try {
            new ItemCategoryDao().register(itemCategory);

            itemCategory = new ItemCategory();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Item Category Registered"));

            itemCategories = new ItemCategoryDao().findAll(ItemCategory.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItem(Item i) {
        new ItemDao().update(i);
        items = new ItemDao().findAll(Item.class);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Item Updated"));
    }

    public void itemUnitRegister() {
        try {
            new ItemUnitDao().register(itemUnit);
            itemUnits = new ItemUnitDao().findAll(ItemUnit.class);
            itemUnit = new ItemUnit();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Item Unit Registered"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Upload(FileUploadEvent event) {
        chosenImage.add(new FileUpload().Upload(event, "C:\\Users\\nizey\\OneDrive\\Documents\\NetBeansProjects\\Market\\HMS\\web\\uploads\\hotel-logo\\"));
    }

    public void registerUserDepartment() {
        try {
            userDepartment.setStatus(EStatus.ACTIVE);
            new UserDepartmentDao().register(userDepartment);

            userDepartment = new UserDepartment();

            userDepartments = new UserDepartmentDao().findAll(UserDepartment.class);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Department Registered"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userRegister() {
        try {
            UserDepartment department = new UserDepartmentDao().findOne(UserDepartment.class, userDepartmentId);
            user.setUserDepartment(department);
            user.setStatus(EStatus.ACTIVE);
            user.setUsername(user.getNames());
            user.setPassword(new PassCode().encrypt("1234"));
            new PersonDao().register(user);

            user = new Person();
            users = new PersonDao().findAll(Person.class);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("User Registered"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cashierRegister() {
        try {
            UserDepartment department = new UserDepartmentDao().findOne(UserDepartment.class, userDepartmentId);
            user.setUserDepartment(department);
            user.setStatus(EStatus.ACTIVE);
            user.setNames("Morreen");
            new PersonDao().register(user);

            user = new Person();
            users = new PersonDao().findAll(Person.class);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("User Registered"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userUpdate() {
        try {
            UserDepartment department = new UserDepartmentDao().findOne(UserDepartment.class, userDepartmentId);
            user.setUserDepartment(department);
//            user.setStatus(EStatus.ACTIVE);
            new PersonDao().update(user);

            users = new PersonDao().findAll(Person.class);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("User Updated"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemRegister() {
        try {
            ItemCategory category = new ItemCategoryDao().findOne(ItemCategory.class, itemCategoryId);
            ItemUnit itemUnit = new ItemUnitDao().findOne(ItemUnit.class, itemUnitId);

            item.setItemCategory(category);
            item.setItemUnit(itemUnit);
            new ItemDao().register(item);
            items = new ItemDao().findAll(Item.class);
            item = new Item();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Item Registered"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerRestaurant() {
        try {
            if (chosenImage.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Upload Logo"));
            } else {
                for (String x : chosenImage) {
                    restaurant.setLogo(x);
                }
                chosenImage.clear();

                restaurant.setStatus(EStatus.ACTIVE);
                new RestaurantDao().register(restaurant);
                restaurant = new Restaurant();
            }
            restaurants = new RestaurantDao().findAll(Restaurant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void blockRestaurant(Restaurant rest) {
        if (rest.getStatus().equals(EStatus.ACTIVE)) {
            rest.setStatus(EStatus.BLOCKED);
            new RestaurantDao().update(rest);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Restaurant Blocked"));
        } else {
            rest.setStatus(EStatus.ACTIVE);
            new RestaurantDao().update(rest);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Restaurant Activated"));
        }
    }

    public void registerTableGroup() {
        new TableGroupDao().register(tableGroup);
        tableGroup = new TableGroup();
        tableGroups = new TableGroupDao().findAll(TableGroup.class);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Table Group Registered"));
    }

    public void registerTableMaster() {
        Restaurant restaurant = new RestaurantDao().findOne(Restaurant.class, restaurantId);
        TableGroup group = new TableGroupDao().findOne(TableGroup.class, tableGroupId);

        System.out.println("Restaurant: " + restaurantId);
        System.out.println("Table: " + tableGroupId);

        tableMaster.setTableGroup(group);
        tableMaster.setTableStatus(ETableStatus.VACANT);
        tableMaster.setRestaurant(restaurant);

        new TableMasterDao().register(tableMaster);
        tableMaster = new TableMaster();

        tableMasters = new TableMasterDao().findAll(TableMaster.class);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Table Master Registered"));
    }

    public String navigateEditEmployee(Person person) {
        editPerson = person;
        return "employee.xhtml?faces-redirect=true";
    }

    public Person getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Person loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    
    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public HotelConfig getHotelConfig() {
        return hotelConfig;
    }

    public void setHotelConfig(HotelConfig hotelConfig) {
        this.hotelConfig = hotelConfig;
    }

    public String getUserDepartmentId() {
        return userDepartmentId;
    }

    public void setUserDepartmentId(String userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
    }

    public List<Person> getUsers() {
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    public List<UserDepartment> getUserDepartments() {
        return userDepartments;
    }

    public void setUserDepartments(List<UserDepartment> userDepartments) {
        this.userDepartments = userDepartments;
    }

    public HotelConfig getHotel() {
        return hotel;
    }

    public void setHotel(HotelConfig hotel) {
        this.hotel = hotel;
    }

    public List<String> getChosenImage() {
        return chosenImage;
    }

    public void setChosenImage(List<String> chosenImage) {
        this.chosenImage = chosenImage;
    }

    public UserDepartment getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(UserDepartment userDepartment) {
        this.userDepartment = userDepartment;
    }

    public Person getEditPerson() {
        return editPerson;
    }

    public void setEditPerson(Person editPerson) {
        this.editPerson = editPerson;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public void setTableGroup(TableGroup tableGroup) {
        this.tableGroup = tableGroup;
    }

    public TableMaster getTableMaster() {
        return tableMaster;
    }

    public void setTableMaster(TableMaster tableMaster) {
        this.tableMaster = tableMaster;
    }

    public String getTableGroupId() {
        return tableGroupId;
    }

    public void setTableGroupId(String tableGroupId) {
        this.tableGroupId = tableGroupId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public ItemUnit getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(ItemUnit itemUnit) {
        this.itemUnit = itemUnit;
    }

    public List<ItemUnit> getItemUnits() {
        return itemUnits;
    }

    public void setItemUnits(List<ItemUnit> itemUnits) {
        this.itemUnits = itemUnits;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public List<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    public String getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(String itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getItemUnitId() {
        return itemUnitId;
    }

    public void setItemUnitId(String itemUnitId) {
        this.itemUnitId = itemUnitId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
