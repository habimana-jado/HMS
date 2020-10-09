package uimodel;

import dao.HotelConfigDao;
import dao.ItemDao;
import dao.PaymentDao;
import dao.PersonDao;
import dao.TableGroupDao;
import dao.TableMasterDao;
import dao.TableTransactionDao;
import dao.UserDepartmentDao;
import domain.HotelConfig;
import domain.Item;
import domain.Payment;
import domain.Person;
import domain.TableGroup;
import domain.TableMaster;
import domain.TableTransaction;
import domain.UserDepartment;
import enums.EPaymentMode;
import enums.ETableStatus;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CashierModel {

    private Person loggedInUser = new Person();
    private HotelConfig hotel = new HotelConfigDao().findOne(HotelConfig.class, Long.parseLong("1"));
    private Item item = new Item();
    private List<Item> items = new ItemDao().findAll(Item.class);
    private TableTransaction tableTransaction = new TableTransaction();
    private String personId = new String();
    private Payment payment = new Payment();
    private List<TableGroup> tableGroups = new TableGroupDao().findAll(TableGroup.class);
    private List<TableMaster> tableMasters = new TableMasterDao().findByType("Table");
    private List<TableMaster> roomMasters = new TableMasterDao().findByType("Room");
    private List<TableMaster> vipRoomMasters = new TableMasterDao().findByType("VipRoom");
    private List<TableTransaction> tableTransactions = new ArrayList<>();
    private TableMaster chosenTableMaster = new TableMaster();
    private List<TableTransaction> tableTransactions1 = new ArrayList<>();
    private List<TableMaster> vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
    private List<TableMaster> billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
    private List<TableMaster> fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
    private List<TableMaster> vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
    private List<TableMaster> billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
    private List<TableMaster> fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
    private List<TableMaster> vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
    private List<TableMaster> billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
    private List<TableMaster> fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
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
    private Long availableTable;
    private Long billedTable;
    private Long occupiedTable;
    private Date chosenDate = new Date();
    private String payerNumber = new String();
    private String paymentMode = new String();

    @PostConstruct
    public void init() {
        userInit();
        tableMasters = new TableMasterDao().findByType("Table");
        roomMasters = new TableMasterDao().findByType("Room");
        vipRoomMasters = new TableMasterDao().findByType("VipRoom");
        tableGroups = new TableGroupDao().findAll(TableGroup.class);
        vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
        billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
        fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
        vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
        billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
        fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
        vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
        billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
        fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
        waiters = new PersonDao().findByDepartment(waitery);
        dailyCollection = new TableTransactionDao().findTotalByDate(new Date());
        dailyBilled = new TableTransactionDao().findTotalByDateAndTableStatus(new Date(), "Billed");
        availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
        billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
        occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);
    }

    public void userInit() {
        loggedInUser = (Person) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    }

    public void populateTableTransactions(TableMaster tableMaster) {
        chosenTableMaster = tableMaster;
        tableTransactions = new ArrayList<>();
    }

    public void populateSentTableTransactions(TableMaster tableMaster) {
//        totalBilledBeverage = new TableTransactionDao().findTotalByTableAndStatus(tableMaster, "Sent", "Beverage");
//        totalBilledFoods = new TableTransactionDao().findTotalByTableAndStatus(tableMaster, "Sent", "Food");

        chosenTableMaster = tableMaster;
        tableTransactions = new TableTransactionDao().findByTableAndStatus(tableMaster, "Sent");
    }

    public static String title[] = new String[]{"Product", "Qty", "Rate", "Amount"};

    public static String kotTitle[] = new String[]{"Product", "Qty"};

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss a");
        return sdf.format(cal.getTime());
    }

    public void printBill() {

        final PrinterJob job = PrinterJob.getPrinterJob();

        Printable contentToPrint = new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                Graphics2D g = (Graphics2D) graphics;
                g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g.setFont(new Font("Monospaced", Font.BOLD, 10));

                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                } //Only one page

                Double totalPrice = totalBilledFoods + totalBilledBeverage;
                int y = 20;
//                g.drawString("Vat No.: " + hotel.getVatNo(), 90, y);
//                g.drawString("ST No.: " + hotel.getStreetNo(), 90, y + 10);
//                g.drawString("" + hotel.getPhone(), 110, y + 20);
//                g.drawString("" + chosenTableMaster.getRestaurant().getName(), 50, y + 34);
//                g.drawString("" + chosenTableMaster.getRestaurant().getSlogan(), 30, y + 44);
//                g.drawString("" + hotel.getHotelName(), 60, y + 55);
//
//                y = 65;
//
//                g.drawString("Bill Date    " + now(), 5, y + 20);
//                g.drawString("Table No.: " + chosenTableMaster.getTableNo(), 5, y + 30);
//                g.drawString("Waiter: " + chosenTableMaster.getPerson().getNames(), 5, y + 40);
//                g.drawLine(3, y + 45, 200, y + 45);

                g.drawString(title[0], 3, y + 60);
                g.drawString(title[1], 80, y + 60);
                g.drawString(title[2], 110, y + 60);
                g.drawString(title[3], 160, y + 60);
//            g.drawString(title[2], 30, y+50);
//                g.drawLine(3, y + 55, 200, y + 55);

                int cH = 0;
                int i = 0;
                DecimalFormat df = new DecimalFormat("###,###");

                for (TableTransaction tt : tableTransactions) {
                    String id = i + "";
                    String prod = tt.getItem().getItemName();
                    String quant = tt.getQuantity() + "";
                    String rate = df.format(tt.getItem().getUnitRate()) + "";
                    String price = df.format(tt.getTotalPrice()) + "";

                    cH = (y + 80) + (10 * i);

                    g.drawString(prod, 3, cH);
                    g.drawString(quant, 80, cH);
                    g.drawString(rate, 110, cH);
                    g.drawString(price, 160, cH);

                    i++;

                }

//                g.drawString("SubTotal(Food): " + df.format(totalBilledFoods), 60, cH + 15);
//                g.drawString("SubTotal(Drinks): " + df.format(totalBilledBeverage), 60, cH + 25);
//                g.drawString("Bill Amount: RWF"+df.format(totalPrice), 60, cH+35);
//                g.drawString("Payable: RWF" + df.format(totalPrice), 60, cH + 55);
                g.drawString("Thank You", 60, cH + 25);

//                g.drawString("Bill Generated By: " + loggedInUser.getPerson().getNames(), 10, cH + 95);
                g.drawString("Welcome Again", 60, cH + 110);
                return PAGE_EXISTS;
            }
        };

        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);

        Paper pPaper = pageFormat.getPaper();
        pPaper.setImageableArea(0, 0, pPaper.getWidth(), pPaper.getHeight() - 2);
        pageFormat.setPaper(pPaper);

        job.setPrintable(contentToPrint, pageFormat);

        try {
            job.print();

        } catch (PrinterException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printKotBill2() {

        final PrinterJob job = PrinterJob.getPrinterJob();

        Printable contentToPrint = new Printable() {
            @Override

            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                Graphics2D g = (Graphics2D) graphics;
                g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g.setFont(new Font("Monospaced", 1, 10));
                if (pageIndex > 0) {
                    return 1;
                } else {
                    int y = 0;
//                    g.drawString("Vat No.: " + hotel.getVatNo(), 90, y);
//                    g.drawString("ST No.: " + hotel.getStreetNo(), 90, y + 10);
//                    g.drawString("" + hotel.getPhone(), 110, y + 20);
//                    g.drawString("" + chosenTableMaster.getRestaurant().getName(), 50, y + 34);
                    g.drawString("" + chosenTableMaster.getRestaurant().getSlogan(), 30, y + 44);
                    g.drawString("" + hotel.getHotelName(), 60, y + 55);

                    y = 65;

                    g.drawString("Date    " + now(), 5, y + 20);
                    g.drawString("Table No.: " + chosenTableMaster.getTableNo(), 5, y + 30);
                    g.drawString("Waiter: " + chosenTableMaster.getPerson().getNames(), 5, y + 40);
//                g.drawLine(3, y + 45, 200, y + 45);
//                    g.drawString(RestaurantModel.now(), 5, y + 20);
                    g.drawLine(10, y + 50, 200, y + 50);
//                    g.drawString(RestaurantModel.title[0], 0, y + 50);
//                    g.drawString(RestaurantModel.title[1], 30, y + 50);

                    g.drawString(title[0], 3, y + 60);
                    g.drawString(title[1], 150, y + 60);
//                    g.drawString(title[2], 110, y + 60);
//                    g.drawString(title[3], 160, y + 60);
//                    g.drawLine(10, y + 40, 180, y + 40);
                    int cH = 0;
                    int i = 0;

                    Double totalPrice = 0.0;
                    Double beveragePrice = 0.0;
                    Double foodPrice = 0.0;
                    DecimalFormat df = new DecimalFormat("###,###");

                    for (TableTransaction tt : tableTransactions) {
                        String id = i + "";
                        String prod = tt.getItem().getItemName();
                        String quant = tt.getQuantity() + "";
//                        String rate = df.format(tt.getItem().getUnitRate()) + "";
//                        String price = df.format(tt.getTotalPrice()) + "";

                        cH = y + 80 + 10 * i;

                        g.drawString(prod, 3, cH);
                        g.drawString(quant, 150, cH);
//                        g.drawString(rate, 110, cH);
//                        g.drawString(price, 160, cH);

                        i++;
                    }

//                    g.drawString("SubTotal(Food): " + df.format(foodPrice), 60, cH + 15);
//                    g.drawString("SubTotal(Drinks): " + df.format(beveragePrice), 60, cH + 25);
//
////                g.drawString("Bill Amount: RWF"+df.format(totalPrice), 60, cH+35);
//                    g.drawString("Payable: RWF" + df.format(foodPrice + beveragePrice), 60, cH + 55);
//
                    g.drawString("Thank You", 60, cH + 25);

//                    g.drawString("Bill Generated By: " + loggedInUser.getPerson().getNames(), 10, cH + 35);
//                    g.drawString("Welcome Again", 60, cH + 50);
//                    g.drawLine(10, y + 40, 180, y + 40);
//                    g.drawString("Thank You", 10, cH + 30);
                    return 0;
                }
            }

//            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//                Graphics2D g = (Graphics2D) graphics;
//                g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
//                g.setFont(new Font("Monospaced", Font.BOLD, 10));
//
//                if (pageIndex > 0) {
//                    return NO_SUCH_PAGE;
//                } //Only one page
//
//                Double totalPrice = totalBilledFoods + totalBilledBeverage;
//                int y = 20;
//                g.drawString("Vat No.: " + hotel.getVatNo(), 90, y);
//                g.drawString("ST No.: " + hotel.getStreetNo(), 90, y + 10);
//                g.drawString("" + hotel.getPhone(), 110, y + 20);
//                g.drawString("" + chosenTableMaster.getRestaurant().getName(), 50, y + 34);
//                g.drawString("" + chosenTableMaster.getRestaurant().getSlogan(), 30, y + 44);
//                g.drawString("" + hotel.getHotelName(), 60, y + 55);
//
//                y = 65;
//
//                g.drawString("Bill Date    " + now(), 5, y + 20);
//                g.drawString("Table No.: " + chosenTableMaster.getTableNo(), 5, y + 30);
//                g.drawString("Waiter: " + chosenTableMaster.getPerson().getNames(), 5, y + 40);
////                g.drawLine(3, y + 45, 200, y + 45);
//
//                g.drawString(title[0], 3, y + 60);
//                g.drawString(title[1], 80, y + 60);
//                g.drawString(title[2], 110, y + 60);
//                g.drawString(title[3], 160, y + 60);
////            g.drawString(title[2], 30, y+50);
////                g.drawLine(3, y + 55, 200, y + 55);
//
//                int cH = 0;
//                int i = 0;
//                DecimalFormat df = new DecimalFormat("###,###");
//
//                for (TableTransaction tt : tableTransactions) {
//                    String id = i + "";
//                    String prod = tt.getItem().getItemName();
//                    String quant = tt.getQuantity() + "";
//                    String rate = df.format(tt.getItem().getUnitRate()) + "";
//                    String price = df.format(tt.getTotalPrice()) + "";
//
//                    cH = (y + 80) + (10 * i);
//
//                    g.drawString(prod, 3, cH);
//                    g.drawString(quant, 80, cH);
//                    g.drawString(rate, 110, cH);
//                    g.drawString(price, 160, cH);
//
//                    i++;
//
//                }
//
//                g.drawString("SubTotal(Food): " + df.format(totalBilledFoods), 60, cH + 15);
//                g.drawString("SubTotal(Drinks): " + df.format(totalBilledBeverage), 60, cH + 25);
//
////                g.drawString("Bill Amount: RWF"+df.format(totalPrice), 60, cH+35);
//                g.drawString("Payable: RWF" + df.format(totalPrice), 60, cH + 55);
//
//                g.drawString("Thank You", 60, cH + 85);
//
//                g.drawString("Bill Generated By: " + loggedInUser.getPerson().getNames(), 10, cH + 95);
//
//                g.drawString("Welcome Again", 60, cH + 110);
//                return PAGE_EXISTS;
//            }
        };

        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);

        Paper pPaper = pageFormat.getPaper();
        pPaper.setImageableArea(0, 0, pPaper.getWidth(), pPaper.getHeight() - 2);
        pageFormat.setPaper(pPaper);

        job.setPrintable(contentToPrint, pageFormat);

        try {
            job.print();

        } catch (PrinterException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printKotBill() {

        final PrinterJob job = PrinterJob.getPrinterJob();

        Printable contentToPrint = new Printable() {
            @Override

            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                Graphics2D g = (Graphics2D) graphics;
                g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g.setFont(new Font("Monospaced", 1, 10));
                if (pageIndex > 0) {
                    return 1;
                } else {
                    int y = 20;
                    g.drawString("Vat No.: " + hotel.getVatNo(), 90, y);
                    g.drawString("ST No.: " + hotel.getStreetNo(), 90, y + 10);
                    g.drawString("" + hotel.getPhone(), 110, y + 20);
                    g.drawString("" + chosenTableMaster.getRestaurant().getName(), 50, y + 34);
                    g.drawString("" + chosenTableMaster.getRestaurant().getSlogan(), 30, y + 44);
                    g.drawString("" + hotel.getHotelName(), 60, y + 55);

                    y = 65;

                    g.drawString("Bill Date    " + now(), 5, y + 20);
                    g.drawString("Table No.: " + chosenTableMaster.getTableNo(), 5, y + 30);
                    g.drawString("Waiter: " + chosenTableMaster.getPerson().getNames(), 5, y + 40);
//                g.drawLine(3, y + 45, 200, y + 45);
//                    g.drawString(RestaurantModel.now(), 5, y + 20);
                    g.drawLine(10, y + 50, 200, y + 50);
//                    g.drawString(RestaurantModel.title[0], 0, y + 50);
//                    g.drawString(RestaurantModel.title[1], 30, y + 50);

                    g.drawString(title[0], 3, y + 60);
                    g.drawString(title[1], 80, y + 60);
                    g.drawString(title[2], 110, y + 60);
                    g.drawString(title[3], 160, y + 60);
//                    g.drawLine(10, y + 40, 180, y + 40);
                    int cH = 0;
                    int i = 0;

                    Double totalPrice = 0.0;
                    Double beveragePrice = 0.0;
                    Double foodPrice = 0.0;
                    DecimalFormat df = new DecimalFormat("###,###");

                    for (TableTransaction tt : tableTransactions) {
                        String id = i + "";
                        String prod = tt.getItem().getItemName();
                        String quant = tt.getQuantity() + "";
                        String rate = df.format(tt.getItem().getUnitRate()) + "";
                        String price = df.format(tt.getTotalPrice()) + "";

                        if (tt.getItem().getMenuType().equalsIgnoreCase("Food")) {
                            foodPrice = foodPrice + tt.getQuantity() * (tt.getItem().getUnitRate());
                        } else {
                            beveragePrice = beveragePrice + tt.getQuantity() * (tt.getItem().getUnitRate());
                        }
                        cH = y + 80 + 10 * i;

                        g.drawString(prod, 0, cH);
                        g.drawString(quant, 80, cH);
                        g.drawString(rate, 110, cH);
                        g.drawString(price, 160, cH);

                        i++;
                    }

                    g.drawString("SubTotal(Food): " + df.format(foodPrice), 60, cH + 15);
                    g.drawString("SubTotal(Drinks): " + df.format(beveragePrice), 60, cH + 25);

//                g.drawString("Bill Amount: RWF"+df.format(totalPrice), 60, cH+35);
                    g.drawString("Payable: RWF" + df.format(foodPrice + beveragePrice), 60, cH + 55);
//
                    g.drawString("Thank You", 60, cH + 85);

                    g.drawString("Bill Generated By: " + loggedInUser.getNames(), 10, cH + 95);

                    g.drawString("Welcome Again", 60, cH + 110);
//                    g.drawLine(10, y + 40, 180, y + 40);
//                    g.drawString("Thank You", 10, cH + 30);
                    return 0;
                }
            }

//            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//                Graphics2D g = (Graphics2D) graphics;
//                g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
//                g.setFont(new Font("Monospaced", Font.BOLD, 10));
//
//                if (pageIndex > 0) {
//                    return NO_SUCH_PAGE;
//                } //Only one page
//
//                Double totalPrice = totalBilledFoods + totalBilledBeverage;
//                int y = 20;
//                g.drawString("Vat No.: " + hotel.getVatNo(), 90, y);
//                g.drawString("ST No.: " + hotel.getStreetNo(), 90, y + 10);
//                g.drawString("" + hotel.getPhone(), 110, y + 20);
//                g.drawString("" + chosenTableMaster.getRestaurant().getName(), 50, y + 34);
//                g.drawString("" + chosenTableMaster.getRestaurant().getSlogan(), 30, y + 44);
//                g.drawString("" + hotel.getHotelName(), 60, y + 55);
//
//                y = 65;
//
//                g.drawString("Bill Date    " + now(), 5, y + 20);
//                g.drawString("Table No.: " + chosenTableMaster.getTableNo(), 5, y + 30);
//                g.drawString("Waiter: " + chosenTableMaster.getPerson().getNames(), 5, y + 40);
////                g.drawLine(3, y + 45, 200, y + 45);
//
//                g.drawString(title[0], 3, y + 60);
//                g.drawString(title[1], 80, y + 60);
//                g.drawString(title[2], 110, y + 60);
//                g.drawString(title[3], 160, y + 60);
////            g.drawString(title[2], 30, y+50);
////                g.drawLine(3, y + 55, 200, y + 55);
//
//                int cH = 0;
//                int i = 0;
//                DecimalFormat df = new DecimalFormat("###,###");
//
//                for (TableTransaction tt : tableTransactions) {
//                    String id = i + "";
//                    String prod = tt.getItem().getItemName();
//                    String quant = tt.getQuantity() + "";
//                    String rate = df.format(tt.getItem().getUnitRate()) + "";
//                    String price = df.format(tt.getTotalPrice()) + "";
//
//                    cH = (y + 80) + (10 * i);
//
//                    g.drawString(prod, 3, cH);
//                    g.drawString(quant, 80, cH);
//                    g.drawString(rate, 110, cH);
//                    g.drawString(price, 160, cH);
//
//                    i++;
//
//                }
//
//                g.drawString("SubTotal(Food): " + df.format(totalBilledFoods), 60, cH + 15);
//                g.drawString("SubTotal(Drinks): " + df.format(totalBilledBeverage), 60, cH + 25);
//
////                g.drawString("Bill Amount: RWF"+df.format(totalPrice), 60, cH+35);
//                g.drawString("Payable: RWF" + df.format(totalPrice), 60, cH + 55);
//
//                g.drawString("Thank You", 60, cH + 85);
//
//                g.drawString("Bill Generated By: " + loggedInUser.getPerson().getNames(), 10, cH + 95);
//
//                g.drawString("Welcome Again", 60, cH + 110);
//                return PAGE_EXISTS;
//            }
        };

        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);

        Paper pPaper = pageFormat.getPaper();
        pPaper.setImageableArea(0, 0, pPaper.getWidth(), pPaper.getHeight() - 2);
        pageFormat.setPaper(pPaper);

        job.setPrintable(contentToPrint, pageFormat);

        try {
            job.print();

        } catch (PrinterException e) {
            System.err.println(e.getMessage());
        }
    }

    public void populateBilledTableTransactions(TableMaster tableMaster) {

        totalBilledBeverage = new TableTransactionDao().findTotalByTableAndStatus(tableMaster, "Sent", "Beverage");
        totalBilledFoods = new TableTransactionDao().findTotalByTableAndStatus(tableMaster, "Sent", "Food");

        tableMaster.setTableStatus(ETableStatus.BILLED);
        new TableMasterDao().update(tableMaster);

        chosenTableMaster = tableMaster;
        tableTransactions = new TableTransactionDao().findByTableAndStatus(tableMaster, "Sent");

        tableMasters = new TableMasterDao().findByType("Table");

        roomMasters = new TableMasterDao().findByType("Room");
        vipRoomMasters = new TableMasterDao().findByType("VipRoom");

//        availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
//        billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
//        occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);
//
//        vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
//        billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
//        fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
//
//        vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
//        billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
//        fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
//
//        vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
//        billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
//        fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
    }

    public void removeUnsavedTransactions() {
        new TableTransactionDao().findByStatus("Pending").forEach((t) -> {
            new TableTransactionDao().delete(t);
        });
        tableMasters = new TableMasterDao().findByType("Table");

        roomMasters = new TableMasterDao().findByType("Room");
        vipRoomMasters = new TableMasterDao().findByType("VipRoom");

//        vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
//        fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
//
//        vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
//        fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
//
//        vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
//        fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
        availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
        billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
        occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);
    }

    public void updateBillingTables() {
        tableMasters = new TableMasterDao().findByType("Table");

        roomMasters = new TableMasterDao().findByType("Room");
        vipRoomMasters = new TableMasterDao().findByType("VipRoom");

//        fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
//        billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
//
//        billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
//        fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
//
//        billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
//        fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
        availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
        billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
        occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);
    }

    public void updatePaymentTables() {
        vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
        billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");

        vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
        billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");

        vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
        billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");

        availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
        billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
        occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);
    }

    public void registerTransaction() {
        try {
            if (itemChosen == null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Choose Item"));
            } else if (waiterId.isEmpty() || waiterId == null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Choose Waiter"));
            } else {
                tableTransaction.setTransactionDate(new Date());
                tableTransaction.setStatus("Pending");
//            tableTransaction.setPerson(new PersonDao().findOne(Person.class, waiterId));
                tableTransaction.setTableMaster(chosenTableMaster);
                tableTransaction.setItem(itemChosen);
                tableTransaction.setTotalPrice(tableTransaction.getQuantity() * itemChosen.getUnitRate());
                new TableTransactionDao().register(tableTransaction);

                chosenTableMaster.setPerson(new PersonDao().findOne(Person.class, waiterId));
                new TableMasterDao().update(chosenTableMaster);

                tableTransactions = new TableTransactionDao().findByTableAndOrStatus(chosenTableMaster, "Pending", "Sent");

                tableTransaction = new TableTransaction();

                dailyCollection = new TableTransactionDao().findTotalByDate(new Date());
                dailyBilled = new TableTransactionDao().findTotalByDateAndTableStatus(new Date(), "Billed");

                availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
                billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
                occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);

                itemChosen = new Item();
                itemSearchKeyWord = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(TableTransaction transaction) {
        try {
            tableTransactions = new TableTransactionDao().findByTableAndOrStatus(chosenTableMaster, "Pending", "Sent");
            if (tableTransactions.size() == 1) {
                TableMaster tm = transaction.getTableMaster();
                tm.setTableStatus(ETableStatus.VACANT);
                new TableMasterDao().update(tm);
            }
            new TableTransactionDao().delete(transaction);

            tableTransactions = new TableTransactionDao().findByTableAndOrStatus(chosenTableMaster, "Pending", "Sent");

            tableTransaction = new TableTransaction();

            dailyCollection = new TableTransactionDao().findTotalByDate(new Date());
            dailyBilled = new TableTransactionDao().findTotalByDateAndTableStatus(new Date(), "Billed");

            availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
            billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
            occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void test(TableTransaction trans) {
        System.out.println(trans.getItem().getItemName() + "----" + trans.getQuantity());
    }

    public void completeTransaction() {
        try {
            TableMaster master = new TableMaster();

            for (TableTransaction table : tableTransactions) {
                System.out.println("KOT: " + table.getKotRemarks());
                table.setStatus("Sent");
                new TableTransactionDao().update(table);

                master = table.getTableMaster();
                master.setTableStatus(ETableStatus.FULL);
                tableDao.update(master);
            }

            tableTransactions = new TableTransactionDao().findByTableAndStatus(chosenTableMaster, "Sent");
            tableMasters = new TableMasterDao().findByType("Table");

            roomMasters = new TableMasterDao().findByType("Room");
            vipRoomMasters = new TableMasterDao().findByType("VipRoom");

//            vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
//            billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
//            fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
//
//            vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
//            billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
//            fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
//
//            vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
//            billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
//            fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
            dailyCollection = new TableTransactionDao().findTotalByDate(new Date());
            dailyBilled = new TableTransactionDao().findTotalByDateAndTableStatus(new Date(), "Billed");

            availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
            billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
            occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Transaction Saved"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeTransactionAndPrint() {
        try {
            TableMaster master = new TableMaster();

            for (TableTransaction table : tableTransactions) {
                System.out.println("KOT: " + table.getKotRemarks());
                table.setStatus("Sent");
                new TableTransactionDao().update(table);

                master = table.getTableMaster();
                master.setTableStatus(ETableStatus.FULL);
                tableDao.update(master);
            }

            tableTransactions = new TableTransactionDao().findByTableAndStatus(chosenTableMaster, "Sent");

            vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
            billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
            fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");

            vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
            billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
            fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");

            vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
            billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
            fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");

            dailyCollection = new TableTransactionDao().findTotalByDate(new Date());
            dailyBilled = new TableTransactionDao().findTotalByDateAndTableStatus(new Date(), "Billed");

            availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
            billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
            occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);

            printKotBill2();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Transaction Saved"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payTransaction() {
        try {
            TableMaster master = new TableMaster();

            for (TableTransaction table : tableTransactions) {
                table.setStatus("Completed");
                new TableTransactionDao().update(table);

                master = table.getTableMaster();
                master.setTableStatus(ETableStatus.VACANT);
                tableDao.update(master);

                payment.setTableTransaction(table);

                if (paymentMode.equalsIgnoreCase("CASH")) {
                    payment.setPaymentMode(EPaymentMode.CASH);
                } else if (paymentMode.equalsIgnoreCase("CARD")) {
                    payment.setPaymentMode(EPaymentMode.CARD);
                } else if (paymentMode.equalsIgnoreCase("CREDIT")) {
                    payment.setPaymentMode(EPaymentMode.CREDIT);
                } else if (paymentMode.equalsIgnoreCase("NC")) {
                    payment.setPaymentMode(EPaymentMode.NC);
                } else if (paymentMode.equalsIgnoreCase("MOBILEMONEY")) {
                    payment.setPaymentMode(EPaymentMode.MOBILEMONEY);
                } else if (paymentMode.equalsIgnoreCase("POSTTOROOM")) {
                    payment.setPaymentMode(EPaymentMode.POSTTOROOM);
                }

//                switch (paymentMode) {
//                    case "CASH":
//                        payment.setPaymentMode(EPaymentMode.CASH);
//                        break;
//
//                    case "CARD":
//                        payment.setPaymentMode(EPaymentMode.CARD);
//                        break;
//
//                    case "CREDIT":
//                        payment.setPaymentMode(EPaymentMode.CREDIT);
//                        break;
//
//                    case "NC":
//                        payment.setPaymentMode(EPaymentMode.NC);
//                        break;
//
//                    case "MOBILEMONEY":
//                        payment.setPaymentMode(EPaymentMode.MOBILEMONEY);
//                        break;
//
//                    case "POSTTOROOM":
//                        payment.setPaymentMode(EPaymentMode.POSTTOROOM);
//                        break;
//
//                    default:
//                        payment.setPaymentMode(EPaymentMode.CASH);
//                        break;
//
//                }
//                payment.setMobileNumber(payerNumber);
                payment.setPaymentDate(new Date());
                new PaymentDao().register(payment);
                payment = new Payment();

            }

            tableTransactions = new TableTransactionDao().findByTableAndStatus(chosenTableMaster, "Completed");

            tableMasters = new TableMasterDao().findByType("Table");

            roomMasters = new TableMasterDao().findByType("Room");
            vipRoomMasters = new TableMasterDao().findByType("VipRoom");

//            vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
//            billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
//            fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");
//
//            vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
//            billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
//            fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");
//
//            vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
//            billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
//            fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");
            availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
            billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
            occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Payment Saved"));
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

    public void refreshTables() {

        vacantTableMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Table");
        billedTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Table");
        fullTableTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Table");

        vacantRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "Room");
        billedRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "Room");
        fullRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "Room");

        vacantVipRoomMasters = new TableMasterDao().findByStatusAndType(ETableStatus.VACANT, "VipRoom");
        billedVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.BILLED, "Sent", "VipRoom");
        fullVipRoomTransactions = new TableTransactionDao().findByTableStatusAndType(ETableStatus.FULL, "Sent", "VipRoom");

        dailyCollection = new TableTransactionDao().findTotalByDate(new Date());
        dailyBilled = new TableTransactionDao().findTotalByDateAndTableStatus(new Date(), "Billed");

        availableTable = new TableMasterDao().findTotalByStatus(ETableStatus.VACANT);
        billedTable = new TableMasterDao().findTotalByStatus(ETableStatus.BILLED);
        occupiedTable = new TableMasterDao().findTotalByStatus(ETableStatus.FULL);

    }

    public void chooseItem(Item it) {
        itemChosen = it;
        suggestedItems = new ArrayList<>();
        itemSearchKeyWord = itemChosen.getItemName();
    }

    public Person getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Person loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public HotelConfig getHotel() {
        return hotel;
    }

    public void setHotel(HotelConfig hotel) {
        this.hotel = hotel;
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

    public UserDepartment getWaitery() {
        return waitery;
    }

    public void setWaitery(UserDepartment waitery) {
        this.waitery = waitery;
    }

    public List<Person> getWaiters() {
        return waiters;
    }

    public void setWaiters(List<Person> waiters) {
        this.waiters = waiters;
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

    public Long getAvailableTable() {
        return availableTable;
    }

    public void setAvailableTable(Long availableTable) {
        this.availableTable = availableTable;
    }

    public Long getBilledTable() {
        return billedTable;
    }

    public void setBilledTable(Long billedTable) {
        this.billedTable = billedTable;
    }

    public Long getOccupiedTable() {
        return occupiedTable;
    }

    public void setOccupiedTable(Long occupiedTable) {
        this.occupiedTable = occupiedTable;
    }

    public Date getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getPayerNumber() {
        return payerNumber;
    }

    public void setPayerNumber(String payerNumber) {
        this.payerNumber = payerNumber;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public static String[] getTitle() {
        return title;
    }

    public static void setTitle(String[] title) {
        CashierModel.title = title;
    }

    public static String[] getKotTitle() {
        return kotTitle;
    }

    public static void setKotTitle(String[] kotTitle) {
        CashierModel.kotTitle = kotTitle;
    }

    public List<TableMaster> getVacantRoomMasters() {
        return vacantRoomMasters;
    }

    public void setVacantRoomMasters(List<TableMaster> vacantRoomMasters) {
        this.vacantRoomMasters = vacantRoomMasters;
    }

    public List<TableMaster> getBilledRoomTransactions() {
        return billedRoomTransactions;
    }

    public void setBilledRoomTransactions(List<TableMaster> billedRoomTransactions) {
        this.billedRoomTransactions = billedRoomTransactions;
    }

    public List<TableMaster> getFullRoomTransactions() {
        return fullRoomTransactions;
    }

    public void setFullRoomTransactions(List<TableMaster> fullRoomTransactions) {
        this.fullRoomTransactions = fullRoomTransactions;
    }

    public List<TableMaster> getVacantVipRoomMasters() {
        return vacantVipRoomMasters;
    }

    public void setVacantVipRoomMasters(List<TableMaster> vacantVipRoomMasters) {
        this.vacantVipRoomMasters = vacantVipRoomMasters;
    }

    public List<TableMaster> getBilledVipRoomTransactions() {
        return billedVipRoomTransactions;
    }

    public void setBilledVipRoomTransactions(List<TableMaster> billedVipRoomTransactions) {
        this.billedVipRoomTransactions = billedVipRoomTransactions;
    }

    public List<TableMaster> getFullVipRoomTransactions() {
        return fullVipRoomTransactions;
    }

    public void setFullVipRoomTransactions(List<TableMaster> fullVipRoomTransactions) {
        this.fullVipRoomTransactions = fullVipRoomTransactions;
    }

    public List<TableMaster> getRoomMasters() {
        return roomMasters;
    }

    public void setRoomMasters(List<TableMaster> roomMasters) {
        this.roomMasters = roomMasters;
    }

    public List<TableMaster> getVipRoomMasters() {
        return vipRoomMasters;
    }

    public void setVipRoomMasters(List<TableMaster> vipRoomMasters) {
        this.vipRoomMasters = vipRoomMasters;
    }

}
