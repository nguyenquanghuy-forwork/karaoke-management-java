/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author NQHuy
 */
public class BookRoom extends javax.swing.JFrame {

    /**
     * Creates new form BookRoom
     */
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst;
    String User;
    
    private Detail detail;
    
    private boolean add=false, change=false;
    public BookRoom(String User) {
        super("Book Room");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ICoinLoGo/bookroom.png")));
        this.User=User;
        initComponents();
        conn=javaconnect.ConnectDB();
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);
        Toolkit toolkit=getToolkit();
        Dimension size;
        size = toolkit.getScreenSize();
        setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
        loadDataWaitingList();
        loadDataRoomInformation();
        loadDataBookRoomList();
        reset();
    }
    
    private void Failed(String str1){
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_failed.png"));
        JOptionPane.showMessageDialog(null, str1,"...Warning...",JOptionPane.DEFAULT_OPTION, icon);
    }
    
    private void Completed(String str1){
        ImageIcon icon = new ImageIcon(getClass().getResource("/IconForm/icons8_completed.png"));
        JOptionPane.showMessageDialog(null, str1, "....Notification...",JOptionPane.DEFAULT_OPTION, icon);
    }
    
    private void reset(){
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSave.setEnabled(false);
        customerName.setText("");
        customerPhone.setText("");
        idCard.setText("");
        ((JTextField)tfDate.getDateEditor().getUiComponent()).setText("");
        tfPrepay.setText("");
        tfNote.setText("");
        tfFind.setText("");
    }

    private void loadDataWaitingList(){
        String[] arry={"Customer Name","Phone","ID Card"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            waitingList.setModel(model);
        String sql="select * from WaitingList order by CustomerName, Day";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                while(rs.next()){
                    Vector vector=new Vector();
                    vector.add(rs.getString("CustomerName").trim());
                    vector.add(rs.getString("Phone").trim());
                    vector.add(rs.getString("IDCard").trim());
                    model.addRow(vector);
                }
                waitingList.setModel(model);
                rs.close();
                pst.close();
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
    }
    
    private void loadDataBookRoomList(){
        String[] arry={"ID Card","Customer Name","Phone","PrePay","Room Name","Room Price","Time"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            tableBookRoom.setModel(model);
        String sql="select * from BookRoom order by RoomCode, CustomerName";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                while(rs.next()){
                    Vector vector=new Vector();
                    vector.add(rs.getString("IDCard").trim());
                    vector.add(rs.getString("CustomerName").trim());
                    vector.add(rs.getString("Phone").trim());
                    vector.add(rs.getString("PrePay").trim());
                    vector.add(rs.getString("RoomName").trim());
                    vector.add(rs.getString("RoomPrice").trim());
                    vector.add(rs.getString("Time").trim());
                    model.addRow(vector);
                }
                tableBookRoom.setModel(model);
                rs.close();
                pst.close();
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
    }
    
    private void loadDataRoomInformation(){
        String[] arry={"Room Code","Room Name","Type","Price","Status"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            availableRoom.setModel(model);
        String sql="select * from RoomInformation order by Status, Name";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                while((rs.next())){
                    Vector vector=new Vector();
                    vector.add(rs.getString("RoomCode").trim());
                    vector.add(rs.getString("Name").trim());
                    vector.add(rs.getString("Type").trim());
                    vector.add(rs.getString("Price").trim());
                    vector.add(rs.getString("Status").trim());
                    model.addRow(vector);
                }
                availableRoom.setModel(model);
                rs.close();
                pst.close();
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
    }
    
    
    private BookRoom() {
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBookRoom = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        availableRoom = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        waitingList = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnBook = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        RoomNumber = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        CustomerIDCard = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        tfNote = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        customerName = new javax.swing.JTextField();
        tfPrepay = new javax.swing.JTextField();
        tfDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        customerPhone = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tfFind = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        idCard = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tableBookRoom.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        tableBookRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Name", "Phone", "Room Number", "Time", "Day", "Prepay", "Note"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableBookRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBookRoomMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBookRoom);

        availableRoom.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        availableRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Code", "Name", "Type", "Price"
            }
        ));
        availableRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                availableRoomMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(availableRoom);

        waitingList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        waitingList.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        waitingList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Phone", "Gender", "ID Card "
            }
        ));
        waitingList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                waitingListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(waitingList);

        jLabel17.setFont(new java.awt.Font("Sitka Banner", 2, 15)); // NOI18N
        jLabel17.setText("List of Booked Rooms ");

        jLabel18.setFont(new java.awt.Font("Sitka Banner", 2, 15)); // NOI18N
        jLabel18.setText("Waiting List");

        jLabel19.setFont(new java.awt.Font("Sitka Banner", 2, 15)); // NOI18N
        jLabel19.setText("Available Room List ");

        btnBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-down-50.png"))); // NOI18N
        btnBook.setToolTipText("Book Room");
        btnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel20.setText("Customer ID Card:");

        RoomNumber.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        RoomNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoomNumberActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel21.setText("Room Number:");

        CustomerIDCard.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        CustomerIDCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomerIDCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(RoomNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CustomerIDCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnBook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel21))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(492, 492, 492))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CustomerIDCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addGap(1, 1, 1)
                        .addComponent(RoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBook)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tfNote.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel2.setText("Phone:");

        customerName.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        customerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerNameActionPerformed(evt);
            }
        });

        tfPrepay.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel6.setText("Prepay:");

        customerPhone.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        customerPhone.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                customerPhoneInputMethodTextChanged(evt);
            }
        });
        customerPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                customerPhoneKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel7.setText("Note:");

        jLabel1.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel1.setText("Customer Name:");

        jLabel5.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel5.setText("Day:");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-collaborator-male-20.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-phone-20.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-valentines-day-20.png"))); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-pay-wall-20.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-note-20.png"))); // NOI18N

        tfFind.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        tfFind.setToolTipText("Find Someone");
        tfFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFindActionPerformed(evt);
            }
        });

        btnFind.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-find-and-replace-32.png"))); // NOI18N
        btnFind.setToolTipText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-add-male-user-50.png"))); // NOI18N
        btnAdd.setToolTipText("Add Customer");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-save-50.png"))); // NOI18N
        btnSave.setToolTipText("Save Customer");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-edit-profile-50.png"))); // NOI18N
        btnEdit.setToolTipText("Edit Customer");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-cancel-subscription-50.png"))); // NOI18N
        btnCancel.setToolTipText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-delete-male-user-50.png"))); // NOI18N
        btnDelete.setToolTipText("Remove Customer");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Sitka Banner", 1, 18)); // NOI18N
        jLabel16.setText("Customer Information");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-room-20.png"))); // NOI18N

        idCard.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        idCard.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                idCardInputMethodTextChanged(evt);
            }
        });
        idCard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idCardKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Sitka Banner", 0, 15)); // NOI18N
        jLabel3.setText("ID:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(71, 71, 71))
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfDate, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(tfPrepay)
                            .addComponent(tfNote))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addGap(76, 76, 76)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(customerName, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addComponent(customerPhone)
                                        .addComponent(idCard, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                    .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel1)
                            .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(customerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addComponent(idCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(tfDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfPrepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfFind, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(49, 49, 49))
        );

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IcoinUI/icons8-home-50.png"))); // NOI18N
        btnHome.setToolTipText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnHome)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        Home ob=new Home(User);
        ob.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void customerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerNameActionPerformed

    private void tfFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFindActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void customerPhoneInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_customerPhoneInputMethodTextChanged
        // TODO add your handling code here:
       
    }//GEN-LAST:event_customerPhoneInputMethodTextChanged

    private void customerPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerPhoneKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_customerPhoneKeyPressed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if(customerName.getText().equals("")){
            Failed( "- Customer Name Need To Enter -");
        }else
         if(customerPhone.getText().equals("")){
            Failed("- Customer Phone Need To Enter -");  
        }else 
        if(customerPhone.getText().length()>11 || customerPhone.getText().length()<10){
            Failed("- Wrong Customer Phone -");   
        }else
         if(idCard.getText().equals("")){
            Failed("- ID Card Need To Enter -");
        }else
         if(((JTextField)tfDate.getDateEditor().getUiComponent()).getText().equals("")){
            Failed( "- Date Need Choosen -");
        }else
         if(tfPrepay.getText().equals("")){
            Failed("- Prepay Status Need To Enter -");
        }
        else{
                try{
                   String sql="Insert into WaitingList (CustomerName, Phone, IDCard, Day, PrePay, Note) values (?,?,?,?,?,?)";
                   pst=conn.prepareStatement(sql);
                   pst.setString(1,customerName.getText());
                   pst.setString(2,customerPhone.getText());
                   pst.setString(3,idCard.getText());
                   pst.setString(4,((JTextField)tfDate.getDateEditor().getUiComponent()).getText());
                   pst.setString(5,tfPrepay.getText());
                   pst.setString(6,tfNote.getText());
                   pst.execute();
                   Completed("Customer --> Waiting List");
                   rs.close();
                   pst.close();
                   loadDataWaitingList();
                   reset();
                }catch(Exception e){
                    Failed("Insert Into WaitingList Unknown Error");
                }    
         }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(customerName.getText().equals("")){
            Failed( "- Customer Name Need To Enter -");
        }else
         if(customerPhone.getText().equals("")){
            Failed("- Customer Phone Need To Enter -");  
        }else 
        if(customerPhone.getText().length()>11 || customerPhone.getText().length()<10){
            Failed("- Wrong Customer Phone -");   
        }else
         if(idCard.getText().equals("")){
            Failed("- ID Card Need To Enter -");
        }else
         if(((JTextField)tfDate.getDateEditor().getUiComponent()).getText().equals("")){
            Failed( "- Date Need Choosen -");
        }else
         if(tfPrepay.getText().equals("")){
            Failed("- Prepay Status Need To Enter -");
        }
         else{
                try{
                    String sql="select * from WaitingList where Phone='"+customerPhone.getText()+"'";
                    pst=conn.prepareStatement(sql);
                    rs=pst.executeQuery();
                    if ((rs.getString(2)).equals(customerPhone.getText())){
                        try {           
                            PreparedStatement st = conn.prepareStatement("UPDATE WaitingList SET CustomerName=?, IDCard = ?,Day = ?, PrePay = ?, Note = ? WHERE Phone = ?");
                            st.setString(1, customerName.getText());
                            st.setString(2,idCard.getText());
                            st.setString(3,((JTextField)tfDate.getDateEditor().getUiComponent()).getText());
                            st.setString(4,tfPrepay.getText());
                            st.setString(5,tfNote.getText());
                            st.setString(6,customerPhone.getText());
                            st.executeUpdate();
                            st.close();
                            Completed("Update Completed!");
                            loadDataWaitingList();
                            reset();
                        }
                        catch (Exception e ) {
                            Failed("Update Failed");      
                        }           
                    }else{
                        Failed("Incorrect Phone Number For Update - Please Try Again!");
                    }
                    rs.close();
                    pst.close();
            
                }catch(Exception e){
                    Failed("Update Failed - Not Contain Phone Number");
                }
            
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(customerName.getText().equals("")){
            Failed("- Customer Name Need To Enter -");
        }else
         if(customerPhone.getText().equals("")){
            Failed("- Customer Phone Need To Enter -");  
        }else 
        if(customerPhone.getText().length()>11 || customerPhone.getText().length()<10){
            Failed("- Wrong Customer Phone -");   
        }else
         if(idCard.getText().equals("")){
            Failed("- ID Card Need To Enter -");
        }else
         if(((JTextField)tfDate.getDateEditor().getUiComponent()).getText().equals("")){
            Failed("- Date Need Choosen -");
        }else
         if(tfPrepay.getText().equals("")){
            Failed("- Prepay Status Need To Enter -");
        }
         else{
             try{
                String sql="delete from WaitingList where IDCard=?";
                pst=conn.prepareStatement(sql);
                pst.setString(1,idCard.getText());
                pst.execute();
                pst.close();
                Completed("Delete Customer From Waiting List Completed!");
                loadDataWaitingList();
                reset();
            }catch(Exception e){
                Failed("Delete Customer From Waiting List Error!");
            }
             
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        btnAdd.setEnabled(true);
        btnDelete.setEnabled(true);
        btnSave.setEnabled(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tableBookRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBookRoomMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tableBookRoomMouseClicked

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        String sql="select * from WaitingList where IDCard='"+tfFind.getText()+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if (rs.next()){
                customerName.setText(rs.getString(1));
                customerPhone.setText(rs.getString(2));
                idCard.setText(rs.getString(3));
                ((JTextField)tfDate.getDateEditor().getUiComponent()).setText(rs.getString(4).toString());
                tfPrepay.setText(rs.getString(5));
            }
            else{
                Failed("Not Found With Phone!");
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            Failed("Find Failed!");
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void RoomNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoomNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RoomNumberActionPerformed

    private void CustomerIDCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomerIDCardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CustomerIDCardActionPerformed

    private void idCardInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_idCardInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_idCardInputMethodTextChanged

    private void idCardKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idCardKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idCardKeyPressed

    private void waitingListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_waitingListMouseClicked
        // TODO add your handling code here:
        int index=waitingList.getSelectedRow();
        TableModel model=waitingList.getModel();
        String value=model.getValueAt(index,2).toString();
        CustomerIDCard.setText(value);
        String sql="select * from WaitingList where IDCard='"+value+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if (rs.next()){
                customerName.setText(rs.getString(1));
                customerPhone.setText(rs.getString(2));
                idCard.setText(rs.getString(3));
                ((JTextField)tfDate.getDateEditor().getUiComponent()).setText(rs.getString(4).toString());
                tfPrepay.setText(rs.getString(5));
                tfNote.setText(rs.getString(6));
            }
            else{
                Failed("Not Found With Code Food - Water!");
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            Failed("Find Failed!");
        }
    }//GEN-LAST:event_waitingListMouseClicked

    private void availableRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableRoomMouseClicked
        // TODO add your handling code here:
        int index=availableRoom.getSelectedRow();
        TableModel model=availableRoom.getModel();
        String value=model.getValueAt(index,0).toString();
        RoomNumber.setText(value);
    }//GEN-LAST:event_availableRoomMouseClicked

    private void btnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookActionPerformed
        // TODO add your handling code here:
        String sqlWL="select * from WaitingList where IDCard='"+CustomerIDCard.getText()+"'";
        String value1 = null,value2 = null,value3 = null,value4 = null,value5 = null,value6 = null,value7 = null,value8 = null, value9=null, value10=null;
        value10=String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
        try{
            pst=conn.prepareStatement(sqlWL);
            rs=pst.executeQuery();
            if (rs.next()){
                value1=rs.getString(3);
                value2=rs.getString(1);
                value3=rs.getString(2);
                value4=rs.getString(5);
                value5=rs.getString(6);
            }
            else{
                Failed("Not Found Customer!");
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            Failed("Failed!");
        }
        
        String sqlRI="select * from RoomInformation where RoomCode='"+RoomNumber.getText()+"'";
        try{
            pst=conn.prepareStatement(sqlRI);
            rs=pst.executeQuery();
            if (rs.next()){
                value6=rs.getString(1);
                value7=rs.getString(2);
                value8=rs.getString(4);
                value9=rs.getString(5);
            }
            else{
                Failed("Not Found Room!");
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            Failed("Failed!");
        }
        
         
        if (value1.equals("")){
                Failed("Error - Unknown Customer ID Card ");
            }
            else if (value2.equals("")){
                Failed("Error - Unknown Customer Name ");
            }
            else if (value3.equals("")){
                Failed("Error - Unknown Customer Phone ");
            }
            else if (value4.equals("")){
                Failed("Error - Unknown Customer Status PrePay ");
            }
            else if (value6.equals("")){
                Failed("Error - Unknown Room Code ");
            }
            else if (value7.equals("")){
                Failed("Error - Unknown Room Name ");
            }
            else if (value8.equals("")){
                Failed("Error - Unknown Room Price ");
            }
            else if (!value9.equals("Empty")){
                Failed("Error - Room Has Been Booked ");
            }
            else{
                try{
                    String sql="Insert into BookRoom (IDCard, CustomerName, Phone, PrePay, Note, RoomCode, RoomName, RoomPrice, Time) values (?,?,?,?,?,?,?,?,?)";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,value1);
                    pst.setString(2,value2);
                    pst.setString(3,value3);
                    pst.setString(4,value4);
                    pst.setString(5,value5);
                    pst.setString(6,value6);
                    pst.setString(7,value7);
                    pst.setString(8,value8);
                    pst.setString(9,value10);
                    pst.execute();
                    pst.close();
                    Completed("Book Room Completed!");
                    
                    UpdateStatusRoom("Booked",value6);
                    DeleteCustomerFromWaitingList(value1);
                    loadDataBookRoomList();
                    
                }catch(Exception e){
                    Failed(" - Book Room Error - ");
                }
        }
    }//GEN-LAST:event_btnBookActionPerformed

    private void DeleteCustomerFromWaitingList(String IDCard){
        try{
            String sql="delete from WaitingList where IDCard=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,IDCard);
            pst.execute();
            pst.close();
            loadDataWaitingList();
            reset();
        }catch(Exception e){
            Failed("Delete Failed!");
        }
    }
    
    private void UpdateStatusRoom(String status,String RoomCode){
        try{
                String sql="UPDATE RoomInformation SET Status = ? WHERE RoomCode = ?";
                pst=conn.prepareStatement(sql);
                pst.setString(1,status);
                pst.setString(2,RoomCode);
                pst.executeUpdate();
                pst.close();
                loadDataRoomInformation();
                reset();
                
             }catch(Exception e){
                Failed("Update Room Failed ");
             }
    }
    
    private String CheckStatusRoom(){
        String sqlRI="select * from RoomInformation where RoomCode='"+RoomNumber.getText()+"'";
        String value="Error";
        try{
            pst=conn.prepareStatement(sqlRI);
            rs=pst.executeQuery();
            if (rs.next()){
                value = rs.getString(5);
            }
            rs.close();
            pst.close();
        }catch(Exception e){
            return "Error";
        }
        return value;
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
            java.util.logging.Logger.getLogger(BookRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CustomerIDCard;
    private javax.swing.JTextField RoomNumber;
    private javax.swing.JTable availableRoom;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField customerName;
    private javax.swing.JTextField customerPhone;
    private javax.swing.JTextField idCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableBookRoom;
    private com.toedter.calendar.JDateChooser tfDate;
    private javax.swing.JTextField tfFind;
    private javax.swing.JTextField tfNote;
    private javax.swing.JTextField tfPrepay;
    private javax.swing.JTable waitingList;
    // End of variables declaration//GEN-END:variables
}
