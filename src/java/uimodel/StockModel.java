package uimodel;

import dao.ItemDao;
import dao.ItemDescriptionDao;
import dao.PurchaseDao;
import dao.VendorDao;
import domain.Account;
import domain.Item;
import domain.ItemDescription;
import domain.ItemUnit;
import domain.Purchase;
import domain.Vendor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jean de Dieu HABIMANA @2020
 */
@ManagedBean
@SessionScoped
public class StockModel {

    private Account loggedInUser = new Account();
    private Vendor vendor = new Vendor();
    private List<Vendor> vendors = new VendorDao().findAll(Vendor.class);
    private List<Purchase> purchases = new PurchaseDao().findAll(Purchase.class);
    private String vendorId = new String();
    private Purchase purchase = new Purchase();
    private ItemDescription itemDescription = new ItemDescription();
    private Item item = new Item();
    private String itemId = new String();
    private List<Item> items = new ItemDao().findAll(Item.class);
    private List<ItemDescription> itemDescriptions = new ItemDescriptionDao().findByStatus("Pending");
    private ItemUnit itemUnitChosen = new ItemUnit();
    private Date newDate = new Date();
    private List<Item> suggestedItems = new ArrayList<>();
    private String itemSearchKeyWord = new String();
    private Item itemChosen = new Item();
    private Double totalPrice;
    private Double unitPrice;
    private Double quantity;
    
    @PostConstruct
    public void init() {
        userInit();
        vendors = new VendorDao().findAll(Vendor.class);
        items = new ItemDao().findAll(Item.class);
        itemDescriptions = new ItemDescriptionDao().findByStatus("Pending");
    }

    public void userInit() {
        loggedInUser = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
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

    public void refreshItems() {
        try {
            suggestedItems = new ArrayList<>();
//            Item it = new ItemDao().findOne(Item.class, item)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void chooseItem(Item it){
        itemChosen = it;
        itemUnitChosen = it.getItemUnit();
        suggestedItems = new ArrayList<>();
        itemSearchKeyWord = itemChosen.getItemName();
    }

    public void vendorRegistration() {
        try {
            new VendorDao().register(vendor);
            vendors = new VendorDao().findAll(Vendor.class);
            vendor = new Vendor();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Vendor Registered"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseRegistration() {
        try {
            Vendor vendor = new VendorDao().findOne(Vendor.class, vendorId);

            for (ItemDescription desc : itemDescriptions) {
                purchase.setVendor(vendor);
                purchase.setItemDescription(desc);
                new PurchaseDao().register(purchase);

                desc.setStatus("Completed");
                new ItemDescriptionDao().update(desc);
            }

            purchases = new PurchaseDao().findAll(Purchase.class);
            itemDescriptions = new ItemDescriptionDao().findByStatus("Pending");

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Purchase Registered"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemDescriptionRegister() {
        try {
            
            itemDescription.setItem(itemChosen);
            itemDescription.setQuantity(quantity);
            itemDescription.setTotalPrice(totalPrice);
            itemDescription.setUnitPrice(unitPrice);
            itemDescription.setStatus("Pending");
            new ItemDescriptionDao().register(itemDescription);
            
            itemDescriptions = new ItemDescriptionDao().findByStatus("Pending");
            itemDescription = new ItemDescription();
            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Item Added"));
        } catch (Exception e) {
        }
    }
    
    public void calculateTotalPrice(){
        System.out.println("Hi There");
        System.out.println(unitPrice);
        System.out.println(quantity);
//        if(unitPrice <= 0 || unitPrice.isNaN() || unitPrice == null){
//            totalPrice = 0.0;
//        }else{
            totalPrice = unitPrice * quantity;
//        }
    }
    
    public void assignQuantity(){
        System.out.println(quantity);
    }

    public void unitListener() {
        Item ite = new ItemDao().findOne(Item.class, itemId);
        itemUnitChosen = ite.getItemUnit();
    }

    public List<Item> autoCompleteItem() {
        List<Item> list = new ItemDao().findAll(Item.class);
        return list;
    }

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Account loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(ItemDescription itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<ItemDescription> getItemDescriptions() {
        return itemDescriptions;
    }

    public void setItemDescriptions(List<ItemDescription> itemDescriptions) {
        this.itemDescriptions = itemDescriptions;
    }

    public ItemUnit getItemUnitChosen() {
        return itemUnitChosen;
    }

    public void setItemUnitChosen(ItemUnit itemUnitChosen) {
        this.itemUnitChosen = itemUnitChosen;
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(Date newDate) {
        this.newDate = newDate;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

}
