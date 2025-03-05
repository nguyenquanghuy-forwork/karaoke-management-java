/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
 
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 *
 * @author NQHuy
 */
public class SellSomeThings extends javax.swing.JFrame implements Runnable,ActionListener{

    /**
     * Creates new form SellSomeThings
     */
    
    
    private Detail detail;
    private Thread thread;

    private Connection conn=null, connCheck=null;
    private ResultSet rs=null, rsCheck=null;
    
    private boolean Pay=false;
    
    PreparedStatement pst,pstTem;
    String User;
    
    private static boolean jobRunning = true;
    
    private ImageIcon icon1=new ImageIcon(getClass().getResource("/ICoinForUI/phong_trong.png"));
    private ImageIcon icon2=new ImageIcon(getClass().getResource("/ICoinForUI/da_duoc_dat.png"));
    private ImageIcon icon3=new ImageIcon(getClass().getResource("/ICoinForUI/da_co_khach.png"));
    private ImageIcon icon4=new ImageIcon(getClass().getResource("/ICoinForUI/dang_chon.png"));
    
    JButton []ban=new JButton[Constants.soBanNgang*Constants.soBanDoc];
    
    public SellSomeThings(String User) {

        super("Sell SomeThings");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ICoinLoGo/sell.png")));
        this.User=User;
        initComponents();
        taoBan();
        veBan();
        conn=javaconnect.ConnectDB();
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);
        Toolkit toolkit=getToolkit();
        Dimension size;
        size = toolkit.getScreenSize();
        setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
        
        lbNhanVien.setText(User);
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
        lblDate.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
        
        Disabled();
        loadFoodWater();
        checkBill();
        checkTinhTrangRoom();
        Start();
        
        
        
    }

    private void InHoaDon(){
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
            pj.print();
        }
         catch (PrinterException ex) {
            ex.printStackTrace();
        }
        
        if (pj.printDialog()) 
        {
            try {
                pj.print();
            }
            catch (PrinterException exc){
                System.out.println(exc);
            }
        }   
    }
    
    public class BillPrintable implements Printable { 
       
        public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) throws PrinterException {    
            int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    
        
                Graphics2D g2d = (Graphics2D) graphics;                                        

                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

                try{
                    /*Draw Header*/
                    int y=20;
                    int yShift = 10;
                    int headerRectHeight=15;

                    ///////////////// Product names Get ///////////
                        String name="";
                    ///////////////// Product names Get ///////////
                    
                    ///////////////// Product price Get ///////////
                        int price=0;
                        int number=0;
                        int money=0;

                    ///////////////// Product price Get ///////////
                    // Get Bill
                    g2d.setFont(new java.awt.Font("Monospaced",java.awt.Font.PLAIN,9));
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("        Karaoke Bill Receipt         ",12,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("FoodName    Price   Number    T.Price",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
                    String sql="select * from Bill ";
                    
                    try{
                        pst=conn.prepareStatement(sql);
                        rs=pst.executeQuery();
                        while (rs.next()){
                            name=rs.getString(1);
                            price=Integer.parseInt(rs.getString(2));
                            number=Integer.parseInt(rs.getString(3));
                            money=Integer.parseInt(rs.getString(4));
                            g2d.drawString(""+name+"    "+price+"  x  "+number+"    "+money+"",10,y);y+=yShift;
                        }
                        rs.close();
                        pst.close();
                    }catch(Exception e){
                        Failed("Find Failed!");
                    }
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("Money Service          "+lbServiceMoney.getText()+"   ",10,y);y+=yShift;
                    g2d.drawString("Money Room             "+lbRoomMoney.getText()+"   ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("Total Money            "+lbTongTien.getText()+"   ",10,y);y+=yShift;
                    g2d.drawString("Take Money             "+tfTakeMoney.getText()+"   ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("Return Money           "+lbReturnMoney.getText()+"   ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("            Project CĐ CNPM          ",10,y);y+=yShift;
                    g2d.drawString("            Nguyen Quang Huy         ",10,y);y+=yShift;
                    g2d.drawString("            To Thi Bich Tuyen        ",10,y);y+=yShift;
                    g2d.drawString("            Ly Tan Loc               ",10,y);y+=yShift;
                    g2d.drawString("*************************************",10,y);y+=yShift;
                    g2d.drawString("     THANKS TO VISIT OUR KARAOKE     ",10,y);y+=yShift;
                    g2d.drawString("*************************************",10,y);y+=yShift;

                }
                catch(Exception r){
                r.printStackTrace();
                }

                result = PAGE_EXISTS;    
            }    
        return result;    
        }
    }
    public PageFormat getPageFormat(PrinterJob pj){
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();    

        double middleHeight =8.0;  
        double headerHeight = 2.0;                  
        double footerHeight = 2.0;                  
        double width = convert_CM_To_PPI(8);     
        double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
        paper.setSize(width, height);
        paper.setImageableArea(                    
            0,
            10,
            width,            
            height - convert_CM_To_PPI(1)
        );   

        
        pf.setOrientation(PageFormat.PORTRAIT);          
        pf.setPaper(paper); 
        

        return pf;
    }
    
    protected static double convert_CM_To_PPI(double cm) {            
        return toPPI(cm * 0.393600787);            
    }
 
    protected static double toPPI(double inch) {            
        return inch * 72d;            
    }

    private void loadDataTableHoaDon(String sqlBH){
        
        
        String[] arry={"Room","Name","Number","Price","Total Money","Time Buy"};
        DefaultTableModel model=new DefaultTableModel(arry,0);
        tableHoaDon.setModel(model);

        String sql="select * from BanHang order by NameFoodWater";
            try{
                pst=conn.prepareStatement(sqlBH);
                rs=pst.executeQuery();
                while((rs.next())){
                    Vector vector=new Vector();
                    vector.add(rs.getString("Room").trim());
                    vector.add(rs.getString("NameFoodWater").trim());
                    vector.add(rs.getString("Number").trim());
                    vector.add(rs.getString("Price").trim());
                    vector.add(rs.getString("Money").trim());
                    vector.add(rs.getString("Time").trim());
                    model.addRow(vector);
                }
                tableHoaDon.setModel(model);
                pst.close();
                rs.close();;
            }
            catch(Exception ex){
                Failed("Load Data Of Table BanHang - Unknown Error");
            }
            
            
    }
    
    private int getHours(String s){
        String []array=s.replace(":"," ").split("\\s");
        
        return Integer.parseInt(array[0]);
    }
    
    private int getMinute(String s){
        String []array=s.replace(":"," ").split("\\s");
        
        return Integer.parseInt(array[1]);
    }
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }
    
    private void Disabled(){
        cbxNuoc.setEnabled(false);
        tfSoLuong.setEnabled(false);
        btnAdd.setEnabled(false);
    }
    
    private void taoBan(){
        int count=1;
        JButton oldButton=new JButton();
        oldButton.setBounds(0,0,0,0);
        for(int i = 0; i < Constants.soBanDoc; i++){
            for(int j = 0; j < Constants.soBanNgang; j++){
                
                JButton button = new JButton(""+count,icon1);
                button.setHorizontalTextPosition(JButton.CENTER);
                button.setVerticalTextPosition(JButton.BOTTOM);
                
                button.setBounds(oldButton.getX()+oldButton.getWidth(), oldButton.getY(), Constants.Button_Width, Constants.Button_Height);
                button.addActionListener(this);
                
                oldButton.setBounds(button.getX(),button.getY() , button.getWidth()+Constants.distance, button.getHeight()+Constants.distance);
                
                ban[count-1]=button;
                count++;
            }
            oldButton.setBounds(0, oldButton.getY()+oldButton.getHeight(), 0, 0);
        }
    }
    
    private void veBan(){
        for (JButton jButton : ban) {
            PanelBan.add(jButton);
        }
    }
    
    private void checkBill(){
        if(tableHoaDon.getRowCount()==0){
            btnPay.setEnabled(false);
            tfTakeMoney.setEnabled(false);
        }
        else {
            btnPay.setEnabled(false);
            tfTakeMoney.setEnabled(true);
        }
    }
    
    private void Start(){
        if(thread==null){
            thread= new Thread(this);
            thread.start();
        }
    }

    private void loadFoodWater(){
        cbLoaiNuoc.removeAllItems();
        cbLoaiNuoc.addItem("Food");
        cbLoaiNuoc.addItem("Water");
        cbLoaiNuoc.addItem("Service");
    }
    
    private void Update(){
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
    }
    
    private void tinhTongTien(){
        
        btnPay.setEnabled(false);
        String s2="",s3="";
        
        String sqlBK="SELECT * FROM BookRoom WHERE RoomCode="+lbBan.getText();
        try{
            pst=conn.prepareStatement(sqlBK);
            rs=pst.executeQuery();
            while(rs.next()){
                s2=rs.getString("Time");
                s3=rs.getString("RoomPrice");
            }
        }catch(SQLException ex){
            Failed("Unknown Error At Get Time And RoomPrice Of RoomCode");
        }
        
        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm:ss");
        long diff1 = 0;
        try {
            java.util.Date date1 = df.parse(lblTime.getText());
            java.util.Date date2 = df.parse(s2);
            diff1 = (date1.getTime() - date2.getTime())/1000;
            if (diff1<0){
                long diff2=60*60*24*1000-(date2.getTime())+date1.getTime();
                diff1=diff2/1000;
            }
            
        } catch (ParseException ex) {
            Failed("...This Room Empty...");
        }
        
        int roomPrice=Integer.parseInt(s3);
        double hour=(double)diff1/3600;
        int TienKaraoke=(int)(hour*roomPrice);
        
        lbRoomMoney.setText(String.valueOf(TienKaraoke));
       
        lbTongTien.setText("0 VND");
        int TongGia=0;
        int soluong=0;
        
        
        //Lấy tổng tiền các dịch vụ đã sữ dụng của phòng
        String sqlPay="SELECT * FROM BanHang WHERE Room="+lbBan.getText();
        try{
            pst=conn.prepareStatement(sqlPay);
            rs=pst.executeQuery();
            while(rs.next()){
                
                String s1=rs.getString("Money").toString().trim();
                soluong=Integer.parseInt(s1);
                TongGia=TongGia+soluong;
            }
            
        }catch(Exception ex){
            Failed("Get Totel Money Of Room Unknown Error");
        }
        lbServiceMoney.setText(String.valueOf(TongGia));
        lbTongTien.setText(String.valueOf(TongGia+TienKaraoke));
    }
    
    private void checkTinhTrangRoom(){
        try {
            for (JButton jButton : ban) {
                String sql="SELECT * FROM BookRoom WHERE RoomCode="+jButton.getText();
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    jButton.setIcon(icon3);  
                }
                else{ 
                    jButton.setIcon(icon1);
                    jButton.setEnabled(false);
                }
                pst.close();
                rs.close();
            }
        }
        catch (SQLException ex) {
            Failed("Check Status Room Error");
        }
    }
    
    private SellSomeThings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        PanelBan = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cbxNuoc = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        cbLoaiNuoc = new javax.swing.JComboBox<>();
        tfSoLuong = new javax.swing.JTextField();
        txtPrice = new javax.swing.JLabel();
        txtTotalMoney = new javax.swing.JLabel();
        lbGia = new javax.swing.JLabel();
        lbThanhTien = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lbNhanVien = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lbBan = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnPay = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lbReturnMoney = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tfTakeMoney = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbRoomMoney = new javax.swing.JLabel();
        lbServiceMoney = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Room Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Sitka Banner", 0, 18))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        javax.swing.GroupLayout PanelBanLayout = new javax.swing.GroupLayout(PanelBan);
        PanelBan.setLayout(PanelBanLayout);
        PanelBanLayout.setHorizontalGroup(
            PanelBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        PanelBanLayout.setVerticalGroup(
            PanelBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(PanelBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-home-50.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinForUI/da_co_khach.png"))); // NOI18N
        jLabel3.setText("Have Customer");
        jLabel3.setToolTipText("");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel1.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinForUI/phong_trong.png"))); // NOI18N
        jLabel1.setText("Empty Room ");
        jLabel1.setToolTipText("");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinForUI/da_duoc_dat.png"))); // NOI18N
        jLabel2.setText("Booked Room");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel4.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinForUI/dang_chon.png"))); // NOI18N
        jLabel4.setText("Choosing");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(19, 19, 19)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 0, 0))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cbxNuoc.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        cbxNuoc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbxNuocPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel5.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel5.setText("Purchase Quantity");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-buy-50.png"))); // NOI18N
        btnAdd.setToolTipText("Buy");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        cbLoaiNuoc.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        cbLoaiNuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Water" }));
        cbLoaiNuoc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbLoaiNuocPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        tfSoLuong.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        tfSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSoLuongKeyReleased(evt);
            }
        });

        txtPrice.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        txtPrice.setText("Price:");

        txtTotalMoney.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        txtTotalMoney.setText("Total Money:");

        lbGia.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        lbGia.setText("0 VNĐ");

        lbThanhTien.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        lbThanhTien.setText("0 VNĐ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbLoaiNuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbGia)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(txtTotalMoney)
                        .addGap(18, 18, 18)
                        .addComponent(lbThanhTien)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalMoney)
                    .addComponent(txtPrice)
                    .addComponent(lbGia)
                    .addComponent(lbThanhTien))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tableHoaDon.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Purchase Quantity", "Total Money"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Long.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableHoaDon);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel6.setFont(new java.awt.Font("Sitka Banner", 1, 18)); // NOI18N
        jLabel6.setText("Room Number:");

        jLabel7.setFont(new java.awt.Font("Sitka Banner", 0, 18)); // NOI18N
        jLabel7.setText("Day:");

        lblDate.setFont(new java.awt.Font("Sitka Banner", 0, 18)); // NOI18N
        lblDate.setText("..................................");

        lbNhanVien.setFont(new java.awt.Font("Sitka Banner", 0, 18)); // NOI18N
        lbNhanVien.setText("..................................");

        lblTime.setFont(new java.awt.Font("Sitka Banner", 0, 18)); // NOI18N
        lblTime.setText("..................................");

        lbBan.setFont(new java.awt.Font("Sitka Banner", 1, 18)); // NOI18N
        lbBan.setText("..............................");

        jLabel10.setFont(new java.awt.Font("Sitka Banner", 0, 18)); // NOI18N
        jLabel10.setText("Time:");

        jLabel8.setFont(new java.awt.Font("Sitka Banner", 0, 18)); // NOI18N
        jLabel8.setText("Employee Name:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTime)
                            .addComponent(lblDate)
                            .addComponent(lbNhanVien)
                            .addComponent(lbBan))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbBan)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbNhanVien)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDate)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTime)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-pay-64.png"))); // NOI18N
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-print-64.png"))); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        lbReturnMoney.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        lbReturnMoney.setText("0 VNĐ");

        jLabel15.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel15.setText("Return Money:");

        tfTakeMoney.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        tfTakeMoney.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfTakeMoneyKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel17.setText("_____________________________");

        jLabel12.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel12.setText("Total Money: ");

        jLabel14.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel14.setText("Take Money: ");

        lbTongTien.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        lbTongTien.setText("0 VNĐ");

        jLabel16.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel16.setText("Room Money:");

        jLabel18.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel18.setText("Service Money:");

        lbRoomMoney.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        lbRoomMoney.setText("0 VNĐ");

        lbServiceMoney.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        lbServiceMoney.setText("0 VNĐ");

        jLabel19.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel19.setText("_____________________________");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbRoomMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(29, 29, 29)
                                .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbReturnMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfTakeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbServiceMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbServiceMoney))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbRoomMoney))
                .addGap(5, 5, 5)
                .addComponent(jLabel19)
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTakeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(5, 5, 5)
                .addComponent(jLabel17)
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lbReturnMoney))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPay, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Home ob=new Home(User);
        ob.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbLoaiNuocPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLoaiNuocPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbxNuoc.removeAllItems();
        String sql = "SELECT * FROM FoodWaterManagement WHERE Type='"+cbLoaiNuoc.getSelectedItem().toString()+"'";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                this.cbxNuoc.addItem(rs.getString("Name").trim());
            }
            pst.close();
            rs.close();
        }  
        catch (Exception e) {  
            Failed("Load Type Food Service Water Unknown Error");
        }
        if(cbxNuoc.getItemCount()==0){
            cbxNuoc.setEnabled(false);
            tfSoLuong.setEnabled(false);
            lbGia.setText("0 VNĐ");
            tfSoLuong.setText("");
            lbThanhTien.setText("0 VNĐ");
        }
        else {
            cbxNuoc.setEnabled(true);
            lbGia.setText("0 VNĐ");
            tfSoLuong.setText("");
            lbThanhTien.setText("0 VNĐ");
        }
    }//GEN-LAST:event_cbLoaiNuocPopupMenuWillBecomeInvisible

    private void cbxNuocPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxNuocPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        String sql = "SELECT * FROM FoodWaterManagement WHERE Name='"+cbxNuoc.getSelectedItem()+"'";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                lbGia.setText(rs.getString("Price").trim());
                tfSoLuong.setEnabled(true);
            }
            pst.close();
            rs.close();
        }  
        catch (Exception e) {  
            Failed("Load Food Water Service Unknown Error");
        }
    }//GEN-LAST:event_cbxNuocPopupMenuWillBecomeInvisible

    
    private double convertedToNumbers(String s){
        String number="";
        String []array=s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Double.parseDouble(number);
    }
    
    private void tfSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSoLuongKeyReleased
        // TODO add your handling code here:
        tfSoLuong.setText((tfSoLuong.getText()));
        if(tfSoLuong.getText().equals("")){
            lbThanhTien.setText("0");
            btnAdd.setEnabled(false);
        }
        else{
            int soluong=Integer.parseInt(tfSoLuong.getText());
            int TongGia=Integer.parseInt(lbGia.getText());
            lbThanhTien.setText(String.valueOf(soluong*TongGia));
            btnAdd.setEnabled(true);
        }
    }//GEN-LAST:event_tfSoLuongKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
                
        String sql="SELECT * FROM BanHang WHERE Room="+lbBan.getText();
        
        String sqlInsert="INSERT INTO BanHang (Room,NameFoodWater,Price,Number,Money,Time) VALUES(?,?,?,?,?,?)";
        
        try {
            pst=conn.prepareStatement(sqlInsert);
            pst.setString(1,lbBan.getText());
            pst.setString(2,(String)cbxNuoc.getSelectedItem());
            pst.setString(3,lbGia.getText());
            pst.setString(4,tfSoLuong.getText());
            pst.setString(5,lbThanhTien.getText());
            pst.setString(6,lblTime.getText());
            pst.execute();
            pst.close();
            Completed("Buy Service Completed");
            Disabled();
            loadDataTableHoaDon(sql);
            checkBill();
        } catch (SQLException ex) {
            Failed("Buy Service Failed");
        }
        tinhTongTien();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tfTakeMoneyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTakeMoneyKeyReleased
        // TODO add your handling code here:
        btnPay.setEnabled(false);
        btnPrint.setEnabled(false);
        int TongTien=0,TakeMoney=0,ReturnMoney=0;
        if(tfTakeMoney.getText().equals("")){
            lbReturnMoney.setText("0");
            btnPay.setEnabled(false);
        }
        else{
                TongTien=Integer.parseInt(lbTongTien.getText());
                TakeMoney=Integer.parseInt(tfTakeMoney.getText());
                ReturnMoney=TakeMoney-TongTien;
                lbReturnMoney.setText(String.valueOf(ReturnMoney));
            if (ReturnMoney<0){
                btnPay.setEnabled(false);
            }
            else{
                btnPay.setEnabled(true);
            }
        }
    }//GEN-LAST:event_tfTakeMoneyKeyReleased
    
    private void Failed(String str1){
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_failed.png"));
        JOptionPane.showMessageDialog(null, str1,"...Warning...",JOptionPane.DEFAULT_OPTION, icon);
    }
    
    private void Completed(String str1){
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_completed.png"));
        JOptionPane.showMessageDialog(null, str1, "....Notification...",JOptionPane.DEFAULT_OPTION, icon);
    }
    
    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        // TODO add your handling code here:
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_question.png"));
        int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to pay?", "...Confirm...",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

        if (input==0)
        {        
            //Dùng để xóa Bill tạm của khách hàng trước
            deleteBill();

            // dùng để lấy Tên Khách Hàng
            String CustomerName="";
            String sql="select * from BookRoom where RoomCode='"+lbBan.getText()+"'";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if (rs.next()){
                    CustomerName=rs.getString(2);
                }
                pst.close();
                rs.close();

            }catch(Exception e){
                Failed("Unknown Customer Name!");
            }

            //Ghi Bill với các thông tin: tên khách hàng, số phòng, tổng tiền, tiền nhận, tiền trả lại, tên nhân viên, ngày, thời gian
            String sqlHoaDon="INSERT INTO BillInformation (CustomerName,Room,TotalMoney,TakeMoney,ReturnMoney,EmployeeName,Day,Time) VALUES(?,?,?,?,?,?,?,?)";
            try {
                pst=conn.prepareStatement(sqlHoaDon);
                pst.setString(1,CustomerName);
                pst.setString(2,lbBan.getText());
                pst.setString(3,lbTongTien.getText());
                pst.setString(4,tfTakeMoney.getText());
                pst.setString(5,lbReturnMoney.getText());
                pst.setString(6,lbNhanVien.getText());
                pst.setString(7,lblDate.getText());
                pst.setString(8,lblTime.getText());
                pst.execute();
                pst.close();
            } catch (SQLException ex) {
                Failed("Cannot Create Bill");
            }
            

            //Ghi thông tin chi tiết các dịch vụ mà khách hàng đã sử dụng
            addBill();

            //Xóa các dịch vụ trong bảng BanHang với Số Phòng Room
            deleteBanHang(lbBan.getText());
            deleteBookRoom(lbBan.getText());
            
            UpdateStatusRoom("Empty",lbBan.getText());
            
            btnPrint.setEnabled(true);
        }  
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        InHoaDon();
        Refresh();
    }//GEN-LAST:event_btnPrintActionPerformed

    private void UpdateStatusRoom(String status,String RoomCode){
        try{
                String sql="UPDATE RoomInformation SET Status = ? WHERE RoomCode = ?";
                pst=conn.prepareStatement(sql);
                pst.setString(1,status);
                pst.setString(2,RoomCode);
                pst.executeUpdate();
                pst.close();
             }catch(Exception e){
                Failed("Unknown Error - Update Status Room After Pay");
             }
    }
    
    private void deleteBookRoom(String Room){
        try{
            String sql="delete from BookRoom where RoomCode=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,Room);
            pst.execute();
            pst.close();
        }catch(Exception e){
           Failed("Delete Table BookRoom Eror");
        }
    }
    
    private void deleteBill(){
        String sqlDelete="DELETE FROM Bill";
        try {
            pst=conn.prepareStatement(sqlDelete);
            pst.execute();
            pst.close();
        } catch (SQLException ex) {
            Failed("Unknown Delete Error Bill");
        }
    }
    
    private void deleteBanHang(String Room){
        try{
            String sql="delete from BanHang where Room=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,Room);
            pst.execute();
            pst.close();
        }catch(Exception e){
           Failed("Delete Table BanHang Eror");
        }
    }
    
    private void addBill() {
        String sql="SELECT * FROM BanHang WHERE Room="+lbBan.getText();
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String sqlInsert="INSERT INTO Bill (Name,Price,Number,Money) VALUES(?,?,?,?)";
                try {
                    pstTem=conn.prepareStatement(sqlInsert);
                    pstTem.setString(1,rs.getString("NameFoodWater"));
                    pstTem.setString(2,rs.getString("Price"));
                    pstTem.setString(3,rs.getString("Number"));
                    int price=Integer.parseInt(rs.getString("Price"));
                    int number=Integer.parseInt(rs.getString("Number"));
                    pstTem.setString(4,String.valueOf(price*number));
                    
                    pstTem.execute();
                    pstTem.close();
                } catch (SQLException ex) {
                    Failed("Write Bill Error");
                }
            }
            rs.close();
            pst.close();
        }
        catch (Exception e) {
            Failed("Add Bill Error");
        }
    }
    
    private void Refresh(){
        Pay=false;
        lbBan.setText("0");
        tfSoLuong.setText("");
        lbGia.setText("0 VNĐ");
        lbThanhTien.setText("0 VNĐ");
        lbTongTien.setText("0 VNĐ");
        tfTakeMoney.setText("");
        lbReturnMoney.setText("0 VNĐ");
        btnPay.setEnabled(false);
        btnPrint.setEnabled(false);
        Disabled();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SellSomeThings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SellSomeThings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SellSomeThings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SellSomeThings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SellSomeThings().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBan;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox<String> cbLoaiNuoc;
    private javax.swing.JComboBox<String> cbxNuoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBan;
    private javax.swing.JLabel lbGia;
    private javax.swing.JLabel lbNhanVien;
    private javax.swing.JLabel lbReturnMoney;
    private javax.swing.JLabel lbRoomMoney;
    private javax.swing.JLabel lbServiceMoney;
    private javax.swing.JLabel lbThanhTien;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfTakeMoney;
    private javax.swing.JLabel txtPrice;
    private javax.swing.JLabel txtTotalMoney;
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void run(){
        while(true){
        Update();  
            try{
                Thread.sleep(1);  
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Refresh();
        String sqlBH="SELECT * FROM BanHang WHERE Room="+((JButton)e.getSource()).getText()+"";
        loadDataTableHoaDon(sqlBH);
        checkTinhTrangRoom();
        ((JButton)e.getSource()).setIcon(icon4);
        lbBan.setText(((JButton)e.getSource()).getText());
        
        tinhTongTien();
        checkBill();
    }
}
