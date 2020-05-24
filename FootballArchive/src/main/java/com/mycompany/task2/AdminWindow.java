/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import com.mongodb.client.MongoCollection;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class AdminWindow extends javax.swing.JFrame {

    private MongoManager mongo;
    private String command;
    
    private boolean firstOpen = true; //to not invoke item change for rounds and season
    private boolean newLeague = false; //to not invoke item change for rounds and season (because whe you change the league those 2 CBs are reloaded)
   
    public AdminWindow(MongoManager m) {
        initComponents();
        mongo = m;
    }
    
    private void showError(String error) {
        errorMessage.setText(error);
        errorDialog.pack();
        errorDialog.setLocationRelativeTo(null);
        errorDialog.setVisible(true);
    }
    
    private void showMessage(String msg) {
        successMessage.setText(msg);
        successDialog.pack();
        successDialog.setLocationRelativeTo(null);
        successDialog.setVisible(true);
    }

    // to set table layout
    private void setUsersTableModel() {
        usersTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        usersTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        usersTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        usersTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        usersTable.getColumnModel().getColumn(1).setMinWidth(250);
    }
    
    private void setPlayersTableModel() {
        playersTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        playersTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        playersTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        playersTable.getColumnModel().getColumn(1).setMinWidth(250);
        playersTable.getColumnModel().getColumn(2).setMinWidth(200);
    }
    
    private void populateComboBox() {   
        String league = leagueCB.getSelectedItem().toString();
        
        List<String> seasons = mongo.getSeason(league);      
        seasonCB.removeAllItems();
        for(int i=(seasons.size() - 1); i>=0; --i) {
            seasonCB.addItem(seasons.get(i));
        }
        seasonCB.setSelectedItem(seasons.size() - 1);
    }
    
     private void populatePlayersTable() {       
        DefaultTableModel mod = (DefaultTableModel) playersTable.getModel();
        mod.setRowCount(0);
        
        String league = leagueCB.getSelectedItem().toString();
        String year = seasonCB.getSelectedItem().toString();
        
        List<Document> docs = mongo.top20SearchedPlayers(league, year);
        for(int i=0; i<docs.size(); ++i) {
            Document doc = docs.get(i);
            Document player = doc.get("player", Document.class);
            String name = player.get("name").toString();
            Integer access = (Integer)player.get("access");
            Document info = doc.get("info",Document.class);
            String team = info.get("squadra").toString();
            
            mod.addRow(new Object[]{Integer.toString(i+1), " " + name, " " + team, access});
        }
     }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        successDialog = new javax.swing.JDialog();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        successButton = new javax.swing.JButton();
        successMessage = new javax.swing.JLabel();
        errorDialog = new javax.swing.JDialog();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        errorButton = new javax.swing.JButton();
        errorMessage = new javax.swing.JLabel();
        removeAccountDialog = new javax.swing.JDialog();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        passwordAdmin = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        adminID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        usernameRemove = new javax.swing.JTextField();
        sumbitRemoveButton = new javax.swing.JButton();
        insertDialog = new javax.swing.JDialog();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        jLabel4 = new javax.swing.JLabel();
        fileTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fileSearchButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        insertSubmitButton = new javax.swing.JButton();
        insertYearTF = new javax.swing.JTextField();
        insertLeagueCB = new javax.swing.JComboBox<>();
        topPlayersDialog = new javax.swing.JDialog();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playersTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        leagueCB = new javax.swing.JComboBox<>();
        seasonCB = new javax.swing.JComboBox<>();
        topUsersDialog = new javax.swing.JDialog();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        usernameLabel = new javax.swing.JLabel();
        removeAccountButton = new javax.swing.JButton();
        insertSeasonButton = new javax.swing.JButton();
        insertResultsButton = new javax.swing.JButton();
        topActiveUsersButton = new javax.swing.JButton();
        topSearchedPlayersButton = new javax.swing.JButton();
        insertRankingButton = new javax.swing.JButton();
        insertTeamsButton = new javax.swing.JButton();

        successDialog.setTitle("Successfull Operation");
        successDialog.setMinimumSize(new java.awt.Dimension(400, 300));

        kGradientPanel3.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel3.setkStartColor(new java.awt.Color(2, 4, 70));

        successButton.setBackground(new java.awt.Color(255, 255, 255));
        successButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        successButton.setForeground(new java.awt.Color(0, 0, 102));
        successButton.setText("OK");
        successButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        successButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                successButtonMouseClicked(evt);
            }
        });

        successMessage.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        successMessage.setForeground(new java.awt.Color(240, 240, 240));
        successMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        successMessage.setText("jLabel4");

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addComponent(successMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(successButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(successMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(successButton)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout successDialogLayout = new javax.swing.GroupLayout(successDialog.getContentPane());
        successDialog.getContentPane().setLayout(successDialogLayout);
        successDialogLayout.setHorizontalGroup(
            successDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(successDialogLayout.createSequentialGroup()
                .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        successDialogLayout.setVerticalGroup(
            successDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        errorDialog.setTitle("Error");
        errorDialog.setMinimumSize(new java.awt.Dimension(400, 300));
        errorDialog.setResizable(false);

        kGradientPanel4.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel4.setkStartColor(new java.awt.Color(2, 4, 70));

        errorButton.setBackground(new java.awt.Color(255, 255, 255));
        errorButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        errorButton.setForeground(new java.awt.Color(0, 0, 102));
        errorButton.setText("OK");
        errorButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        errorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                errorButtonMouseClicked(evt);
            }
        });

        errorMessage.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        errorMessage.setForeground(new java.awt.Color(255, 0, 0));
        errorMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorMessage.setText("jLabel4");

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(errorButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(errorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(errorButton)
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout errorDialogLayout = new javax.swing.GroupLayout(errorDialog.getContentPane());
        errorDialog.getContentPane().setLayout(errorDialogLayout);
        errorDialogLayout.setHorizontalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        errorDialogLayout.setVerticalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        kGradientPanel2.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel2.setkStartColor(new java.awt.Color(2, 4, 70));

        passwordAdmin.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Admin password");

        adminID.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Admin ID");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Username to remove");

        usernameRemove.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N

        sumbitRemoveButton.setBackground(new java.awt.Color(255, 255, 255));
        sumbitRemoveButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        sumbitRemoveButton.setForeground(new java.awt.Color(0, 0, 102));
        sumbitRemoveButton.setText("Submit");
        sumbitRemoveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sumbitRemoveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sumbitRemoveButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(passwordAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(adminID, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(usernameRemove)))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(sumbitRemoveButton)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(usernameRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(sumbitRemoveButton)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout removeAccountDialogLayout = new javax.swing.GroupLayout(removeAccountDialog.getContentPane());
        removeAccountDialog.getContentPane().setLayout(removeAccountDialogLayout);
        removeAccountDialogLayout.setHorizontalGroup(
            removeAccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        removeAccountDialogLayout.setVerticalGroup(
            removeAccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        kGradientPanel5.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel5.setkStartColor(new java.awt.Color(2, 4, 70));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Select the league");

        fileTF.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("Insert season year");

        fileSearchButton.setBackground(new java.awt.Color(255, 255, 255));
        fileSearchButton.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        fileSearchButton.setForeground(new java.awt.Color(0, 0, 102));
        fileSearchButton.setText("Search");
        fileSearchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        fileSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileSearchButtonMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Select JSON file");

        insertSubmitButton.setBackground(new java.awt.Color(255, 255, 255));
        insertSubmitButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        insertSubmitButton.setForeground(new java.awt.Color(0, 0, 102));
        insertSubmitButton.setText("Submit");
        insertSubmitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        insertSubmitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertSubmitButtonMouseClicked(evt);
            }
        });

        insertYearTF.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N

        insertLeagueCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        insertLeagueCB.setForeground(new java.awt.Color(0, 0, 102));
        insertLeagueCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Serie A", "Premier League", "La Liga", "Bundesliga" }));

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(fileSearchButton)
                .addGap(25, 25, 25))
            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(insertSubmitButton))
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(fileTF, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(insertLeagueCB, javax.swing.GroupLayout.Alignment.LEADING, 0, 213, Short.MAX_VALUE)
                                .addComponent(insertYearTF, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(insertLeagueCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(insertYearTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fileSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fileTF))
                .addGap(40, 40, 40)
                .addComponent(insertSubmitButton)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout insertDialogLayout = new javax.swing.GroupLayout(insertDialog.getContentPane());
        insertDialog.getContentPane().setLayout(insertDialogLayout);
        insertDialogLayout.setHorizontalGroup(
            insertDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertDialogLayout.createSequentialGroup()
                .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        insertDialogLayout.setVerticalGroup(
            insertDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        topPlayersDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                topPlayersDialogWindowOpened(evt);
            }
        });

        kGradientPanel6.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel6.setkStartColor(new java.awt.Color(2, 4, 70));

        playersTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        playersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pos", "Player", "Team", "Researches"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playersTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        playersTable.setRowHeight(35);
        jScrollPane1.setViewportView(playersTable);

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("List of the 20 most searched players");

        leagueCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        leagueCB.setForeground(new java.awt.Color(0, 0, 102));
        leagueCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Serie A", "Premier League", "La Liga", "Bundesliga" }));
        leagueCB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        leagueCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                leagueCBItemStateChanged(evt);
            }
        });

        seasonCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        seasonCB.setForeground(new java.awt.Color(0, 0, 102));
        seasonCB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        seasonCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                seasonCBItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel6Layout = new javax.swing.GroupLayout(kGradientPanel6);
        kGradientPanel6.setLayout(kGradientPanel6Layout);
        kGradientPanel6Layout.setHorizontalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(kGradientPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addGroup(kGradientPanel6Layout.createSequentialGroup()
                        .addComponent(leagueCB, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(seasonCB, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        kGradientPanel6Layout.setVerticalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel7)
                .addGap(30, 30, 30)
                .addGroup(kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(leagueCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seasonCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout topPlayersDialogLayout = new javax.swing.GroupLayout(topPlayersDialog.getContentPane());
        topPlayersDialog.getContentPane().setLayout(topPlayersDialogLayout);
        topPlayersDialogLayout.setHorizontalGroup(
            topPlayersDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        topPlayersDialogLayout.setVerticalGroup(
            topPlayersDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        topUsersDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                topUsersDialogWindowOpened(evt);
            }
        });

        kGradientPanel7.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel7.setkStartColor(new java.awt.Color(2, 4, 70));

        usersTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pos", "Username", "Accesses"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        usersTable.setRowHeight(35);
        jScrollPane2.setViewportView(usersTable);

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("List of the 10 most active users");

        javax.swing.GroupLayout kGradientPanel7Layout = new javax.swing.GroupLayout(kGradientPanel7);
        kGradientPanel7.setLayout(kGradientPanel7Layout);
        kGradientPanel7Layout.setHorizontalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        kGradientPanel7Layout.setVerticalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel8)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout topUsersDialogLayout = new javax.swing.GroupLayout(topUsersDialog.getContentPane());
        topUsersDialog.getContentPane().setLayout(topUsersDialogLayout);
        topUsersDialogLayout.setHorizontalGroup(
            topUsersDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        topUsersDialogLayout.setVerticalGroup(
            topUsersDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topUsersDialogLayout.createSequentialGroup()
                .addComponent(kGradientPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        setTitle("Football Archive - Admin");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        usernameLabel.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(240, 240, 240));
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel.setText("Football Archive Admin");

        removeAccountButton.setBackground(new java.awt.Color(255, 255, 255));
        removeAccountButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        removeAccountButton.setForeground(new java.awt.Color(0, 0, 102));
        removeAccountButton.setText("Remove account");
        removeAccountButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        removeAccountButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeAccountButtonMouseClicked(evt);
            }
        });
        removeAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAccountButtonActionPerformed(evt);
            }
        });

        insertSeasonButton.setBackground(new java.awt.Color(255, 255, 255));
        insertSeasonButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        insertSeasonButton.setForeground(new java.awt.Color(0, 0, 102));
        insertSeasonButton.setText("Insert new season");
        insertSeasonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        insertSeasonButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertSeasonButtonMouseClicked(evt);
            }
        });

        insertResultsButton.setBackground(new java.awt.Color(255, 255, 255));
        insertResultsButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        insertResultsButton.setForeground(new java.awt.Color(0, 0, 102));
        insertResultsButton.setText("Insert new round");
        insertResultsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        insertResultsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertResultsButtonMouseClicked(evt);
            }
        });

        topActiveUsersButton.setBackground(new java.awt.Color(255, 255, 255));
        topActiveUsersButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        topActiveUsersButton.setForeground(new java.awt.Color(0, 0, 102));
        topActiveUsersButton.setText("Top 10 active users");
        topActiveUsersButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        topActiveUsersButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topActiveUsersButtonMouseClicked(evt);
            }
        });

        topSearchedPlayersButton.setBackground(new java.awt.Color(255, 255, 255));
        topSearchedPlayersButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        topSearchedPlayersButton.setForeground(new java.awt.Color(0, 0, 102));
        topSearchedPlayersButton.setText("Top 20 searched players");
        topSearchedPlayersButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        topSearchedPlayersButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topSearchedPlayersButtonMouseClicked(evt);
            }
        });

        insertRankingButton.setBackground(new java.awt.Color(255, 255, 255));
        insertRankingButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        insertRankingButton.setForeground(new java.awt.Color(0, 0, 102));
        insertRankingButton.setText("Insert season ranking");
        insertRankingButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        insertRankingButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertRankingButtonMouseClicked(evt);
            }
        });

        insertTeamsButton.setBackground(new java.awt.Color(255, 255, 255));
        insertTeamsButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        insertTeamsButton.setForeground(new java.awt.Color(0, 0, 102));
        insertTeamsButton.setText("Insert season teams");
        insertTeamsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        insertTeamsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertTeamsButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(insertSeasonButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(topSearchedPlayersButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(insertRankingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(topActiveUsersButton, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                            .addComponent(insertResultsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(insertTeamsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(33, 33, 33))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(removeAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topSearchedPlayersButton)
                    .addComponent(topActiveUsersButton))
                .addGap(69, 69, 69)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertSeasonButton)
                    .addComponent(insertTeamsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertResultsButton)
                    .addComponent(insertRankingButton))
                .addGap(69, 69, 69)
                .addComponent(removeAccountButton)
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void removeAccountButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeAccountButtonMouseClicked
        removeAccountDialog.pack();
        removeAccountDialog.setLocationRelativeTo(null);
        removeAccountDialog.setTitle("Remove account");
        usernameRemove.setText("");
        adminID.setText("");
        passwordAdmin.setText("");
        removeAccountDialog.setVisible(true);
        usernameRemove.requestFocus();
    }//GEN-LAST:event_removeAccountButtonMouseClicked

    private void sumbitRemoveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sumbitRemoveButtonMouseClicked
        if(usernameRemove.getText().equals("")) {
            showError("ERROR: Insert the username to remove !");
            return;
        }      
        if(usernameRemove.getText().equals("admin")) {
            showError("ERROR: You can't remove the admin account!");
            return;
        }
        if(adminID.getText().equals("")) {
            showError("ERROR: Insert admin ID !");
            return;
        }
        if(passwordAdmin.getText().equals("")) {
            showError("ERROR: Insert admin password !");
            return;
        }
        
        int res = mongo.removeAccount(adminID.getText(), passwordAdmin.getText(), usernameRemove.getText());
        if(res == -1) {
            showError("ERROR: Admin ID and/or password wrong !");
            return;  
        }
        if(res == 0) {
            showError("ERROR: Username inserted doesn't exist !");
            return; 
        }
        if (res == 1) {
            showMessage("Account " + usernameRemove.getText() + " successfully removed.");
            usernameRemove.setText("");
            adminID.setText("");
            passwordAdmin.setText("");
            removeAccountDialog.setVisible(false);
        }
    }//GEN-LAST:event_sumbitRemoveButtonMouseClicked

    private void successButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_successButtonMouseClicked
        successDialog.setVisible(false);
    }//GEN-LAST:event_successButtonMouseClicked

    private void errorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_errorButtonMouseClicked
        errorDialog.setVisible(false);
    }//GEN-LAST:event_errorButtonMouseClicked

    private void insertSeasonButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertSeasonButtonMouseClicked
        insertDialog.pack();
        insertDialog.setLocationRelativeTo(null);
        insertDialog.setTitle("Insert new season");
        insertYearTF.setText("");
        fileTF.setText("");
        insertDialog.setVisible(true);
        kGradientPanel5.requestFocus();
        command = "newSeason";
    }//GEN-LAST:event_insertSeasonButtonMouseClicked

    private void fileSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileSearchButtonMouseClicked
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        if(chooser.getSelectedFile() != null) {
            String filename = chooser.getSelectedFile().getAbsolutePath();
            fileTF.setText(filename);
        }
    }
    
    private String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    
    }//GEN-LAST:event_fileSearchButtonMouseClicked

    private void insertSubmitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertSubmitButtonMouseClicked
        String league = insertLeagueCB.getSelectedItem().toString();
        String year = insertYearTF.getText();
        
        if(league.equals("")) {
            showError("ERROR: Select the league!");
            return;
        }
        if(year.equals("")) {
            showError("ERROR: Insert the year of the season!");
            return;
        }
        
        try {
            String s = readFile(fileTF.getText());
            Document doc = Document.parse(s);
            if(command.equals("newSeason")) 
                mongo.insertNewSeason(league, year, doc);
                           
            if(command.equals("newRound"))
                mongo.insertNewRound(league, year, doc);
            
            if(command.equals("newTeams"))
                mongo.insertNewTeams(league, year, doc);
            
            if(command.equals("newRanking"))
                mongo.insertNewRanking(league, year, doc);
        }
        catch (IOException e) {
            showError("Error openening file!");
            return;
        }
        
        insertDialog.setVisible(false);
        insertYearTF.setText("");
        fileTF.setText("");
        
        if(command.equals("newSeason")) 
            showMessage("New season inserted correctly");
        if(command.equals("newRound"))
            showMessage("New round inserted correctly");
        if(command.equals("newRanking"))
            showMessage("New ranking inserted correctly");
        if(command.equals("newTeams"))
            showMessage("New teams inserted correctly");
    }//GEN-LAST:event_insertSubmitButtonMouseClicked

    private void insertResultsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertResultsButtonMouseClicked
        insertDialog.pack();
        insertDialog.setLocationRelativeTo(null);
        insertDialog.setTitle("Insert new round");
        insertYearTF.setText("");
        fileTF.setText("");
        insertDialog.setVisible(true);
        kGradientPanel5.requestFocus();
        command = "newRound";
    }//GEN-LAST:event_insertResultsButtonMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Frame[] frames = JFrame.getFrames();
        for(int i=0; i<frames.length; ++i) {    
            frames[i].setVisible(false);
        }
        new LoginWindow(mongo).setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        kGradientPanel1.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void topSearchedPlayersButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topSearchedPlayersButtonMouseClicked
        topPlayersDialog.pack();
        topPlayersDialog.setLocationRelativeTo(null);
        topPlayersDialog.setTitle("Top 20 searched players");
        topPlayersDialog.setVisible(true);
        kGradientPanel6.requestFocus();
        
        populateComboBox();
        populatePlayersTable();
        firstOpen = false;
    }//GEN-LAST:event_topSearchedPlayersButtonMouseClicked

    private void topActiveUsersButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topActiveUsersButtonMouseClicked
        topUsersDialog.pack();
        topUsersDialog.setLocationRelativeTo(null);
        topUsersDialog.setTitle("Top 20 active users");
        topUsersDialog.setVisible(true);
    }//GEN-LAST:event_topActiveUsersButtonMouseClicked

    private void topPlayersDialogWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_topPlayersDialogWindowOpened
        setPlayersTableModel();
    }//GEN-LAST:event_topPlayersDialogWindowOpened

    private void topUsersDialogWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_topUsersDialogWindowOpened
        setUsersTableModel();
        List<Document> docs = mongo.top10ActiveUsers();
        for(int i=0; i<docs.size(); ++i) {
            Document doc = docs.get(i);
            String username = doc.get("username").toString();
            Integer access = (Integer)doc.get("access");
            DefaultTableModel mod = (DefaultTableModel) usersTable.getModel();
            mod.addRow(new Object[]{Integer.toString(i+1), " " + username, access});
        }
    }//GEN-LAST:event_topUsersDialogWindowOpened

    private void leagueCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_leagueCBItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) { //because you invoke this function both in selection and deselection of an item
            newLeague = true;
            populateComboBox();
            populatePlayersTable();
            newLeague = false;
        }
    }//GEN-LAST:event_leagueCBItemStateChanged

    private void seasonCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_seasonCBItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED && !firstOpen &&!newLeague) { //because you invoke this function both in selection and deselection of an item
            populatePlayersTable();
        }
    }//GEN-LAST:event_seasonCBItemStateChanged

    private void insertRankingButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertRankingButtonMouseClicked
        insertDialog.pack();
        insertDialog.setLocationRelativeTo(null);
        insertDialog.setTitle("Insert season ranking");
        insertYearTF.setText("");
        fileTF.setText("");
        insertDialog.setVisible(true);
        kGradientPanel5.requestFocus();
        command = "newRanking";
    }//GEN-LAST:event_insertRankingButtonMouseClicked

    private void insertTeamsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertTeamsButtonMouseClicked
        insertDialog.pack();
        insertDialog.setLocationRelativeTo(null);
        insertDialog.setTitle("Insert season teams");
        insertYearTF.setText("");
        fileTF.setText("");
        insertDialog.setVisible(true);
        kGradientPanel5.requestFocus();
        command = "newTeams";
    }//GEN-LAST:event_insertTeamsButtonMouseClicked

    private void removeAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAccountButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeAccountButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adminID;
    private javax.swing.JButton errorButton;
    private javax.swing.JDialog errorDialog;
    private javax.swing.JLabel errorMessage;
    private javax.swing.JButton fileSearchButton;
    private javax.swing.JTextField fileTF;
    private javax.swing.JDialog insertDialog;
    private javax.swing.JComboBox<String> insertLeagueCB;
    private javax.swing.JButton insertRankingButton;
    private javax.swing.JButton insertResultsButton;
    private javax.swing.JButton insertSeasonButton;
    private javax.swing.JButton insertSubmitButton;
    private javax.swing.JButton insertTeamsButton;
    private javax.swing.JTextField insertYearTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private javax.swing.JComboBox<String> leagueCB;
    private javax.swing.JPasswordField passwordAdmin;
    private javax.swing.JTable playersTable;
    private javax.swing.JButton removeAccountButton;
    private javax.swing.JDialog removeAccountDialog;
    private javax.swing.JComboBox<String> seasonCB;
    private javax.swing.JButton successButton;
    private javax.swing.JDialog successDialog;
    private javax.swing.JLabel successMessage;
    private javax.swing.JButton sumbitRemoveButton;
    private javax.swing.JButton topActiveUsersButton;
    private javax.swing.JDialog topPlayersDialog;
    private javax.swing.JButton topSearchedPlayersButton;
    private javax.swing.JDialog topUsersDialog;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameRemove;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables
}
