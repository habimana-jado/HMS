
package uimodel;

import dao.ItemDao;
import dao.ItemDescriptionDao;
import dao.PurchaseDao;
import dao.VendorDao;
import domain.Account;
import domain.Item;
import domain.ItemDescription;
import domain.Purchase;
import domain.Vendor;
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
    private List<ItemDescription> itemDescriptions = new ItemDescriptionDao().findAll(ItemDescription.class);
    
    @PostConstruct
    public void init(){
        userInit();
        vendors = new VendorDao().findAll(Vendor.class);
        items = new ItemDao().findAll(Item.class);
        itemDescriptions = new ItemDescriptionDao().findAll(ItemDescription.class);
    }
    
    public void userInit() {
        loggedInUser = (Account) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    }

    public void vendorRegistration(){
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
    
    public void purchaseRegistration(){
        try {
            Vendor vendor = new VendorDao().findOne(Vendor.class, vendorId);
            purchase.setVendor(vendor);
            new PurchaseDao().register(purchase);
            
            purchases = new PurchaseDao().findAll(Purchase.class);
            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Purchase Registered"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemDescriptionRegister(){
        try {
            Item item = new ItemDao().findOne(Item.class, itemId);
            itemDescription.setItem(item);
            new ItemDescriptionDao().register(itemDescription);
                       
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Item Added"));
        } catch (Exception e) {
        }
    }
    
    public List<Item> autoCompleteItem(){
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
    
}
