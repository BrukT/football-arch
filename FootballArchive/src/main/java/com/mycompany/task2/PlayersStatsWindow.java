/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class PlayersStatsWindow extends javax.swing.JFrame {

    private String league;
    private String year;
    private MongoManager mongo;
    
    public PlayersStatsWindow(MongoManager m, String l, String y) {
        initComponents();
        mongo = m;
        league = l; 
        year = y;  
    }
    
    // to set table layout
    private void setTableModel() {
        playerStatsTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        playerStatsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        playerStatsTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        playerStatsTable.getColumnModel().getColumn(1).setMinWidth(250);
        playerStatsTable.getColumnModel().getColumn(1).setMinWidth(200);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerStatsTable = new javax.swing.JTable();
        playerStatsCB = new javax.swing.JComboBox<>();

        setTitle("Players Stats");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        playerStatsTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        playerStatsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pos", "Player", "Goals"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
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
        playerStatsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        playerStatsTable.setRowHeight(35);
        jScrollPane1.setViewportView(playerStatsTable);

        playerStatsCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        playerStatsCB.setForeground(new java.awt.Color(0, 0, 102));
        playerStatsCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOP 20 GOAL SCORERS", "TOP 20 ASSIST MEN", "TOP 20 FOR YELLOW CARDS", "TOP 20 FOR RED CARDS" }));
        playerStatsCB.setToolTipText("");
        playerStatsCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playerStatsCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                playerStatsCBItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(playerStatsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(playerStatsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void playerStatsCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_playerStatsCBItemStateChanged
        
        if(evt.getStateChange() == ItemEvent.SELECTED) { //because you invoke this function both in selection and deselection of an item    
            String stat = playerStatsCB.getSelectedItem().toString();
                        
            if(stat.equals("TOP 20 GOAL SCORERS")) {
                DefaultTableModel model = (DefaultTableModel)playerStatsTable.getModel();
                model.setRowCount(0);
                playerStatsTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Goals");
                
                List<Document> res = mongo.top20MostGoalPlayer(league, year);
                for(int i=0; i<res.size(); ++i) {
                    Document doc = res.get(i);
                    String player = (String)doc.get("_id");
                    int goals = (int)doc.get("goalCount");
                    DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                    mod.addRow(new Object[]{Integer.toString(i+1), player, Integer.toString(goals)});
                }
                
            }
            if(stat.equals("TOP 20 ASSIST MEN")) {
                DefaultTableModel model = (DefaultTableModel)playerStatsTable.getModel();
                model.setRowCount(0);
                playerStatsTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Assists");
                
                List<Document> res = mongo.top20MostAssistPlayer(league, year);
                for(int i=1; i<res.size(); ++i) {
                    Document doc = res.get(i);
                    String player = (String)doc.get("_id");
                    int assists = (int)doc.get("assistCount");
                    DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                    mod.addRow(new Object[]{Integer.toString(i), player, Integer.toString(assists)});
                }
            }
            if(stat.equals("TOP 20 FOR YELLOW CARDS")) {
                DefaultTableModel model = (DefaultTableModel)playerStatsTable.getModel();
                model.setRowCount(0);
                playerStatsTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Yellow Cards");
                
                List<Document> res = mongo.top20YellowCardPlayer(league, year);
                for(int i=0; i<res.size(); ++i) {
                    Document doc = res.get(i);
                    String player = (String)doc.get("_id");
                    int cards = (int)doc.get("yellowCard");
                    DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                    mod.addRow(new Object[]{Integer.toString(i+1), player, Integer.toString(cards)});
                }
            }
            if(stat.equals("TOP 20 FOR RED CARDS")) {
                DefaultTableModel model = (DefaultTableModel)playerStatsTable.getModel();
                model.setRowCount(0);
                playerStatsTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Red Cards");
                
                List<Document> res = mongo.top20RedCardPlayer(league, year);
                for(int i=0; i<res.size(); ++i) {
                    Document doc = res.get(i);
                    String player = (String)doc.get("_id");
                    int cards = (int)doc.get("redCard");
                    DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                    mod.addRow(new Object[]{Integer.toString(i+1), player, Integer.toString(cards)});
                }
            }
            if(stat.equals("TOP 20 FOR AVG. VOTE")) {
                DefaultTableModel model = (DefaultTableModel)playerStatsTable.getModel();
                model.setRowCount(0);
                playerStatsTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Avg. vote");
                
                List<Document> res = mongo.top20VotePlayer(league, year, -1);
                for(int i=0; i<res.size(); ++i) {
                    Document doc = res.get(i);
                    String player = (String)doc.get("_id");
                    double vote = (double)doc.get("averageVote");
                    vote = Math.floor(vote*100)/100;
                    DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                    mod.addRow(new Object[]{Integer.toString(i+1), player, Double.toString(vote)});
                }
            }
            if(stat.equals("LOWEST 20 FOR AVG. VOTE")) {
                DefaultTableModel model = (DefaultTableModel)playerStatsTable.getModel();
                model.setRowCount(0);
                playerStatsTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue("Avg. vote");
                
                List<Document> res = mongo.top20VotePlayer(league, year, 1);
                for(int i=0; i<res.size(); ++i) {
                    Document doc = res.get(i);
                    String player = (String)doc.get("_id");
                    double vote = (double)doc.get("averageVote");
                    vote = Math.floor(vote*100)/100;
                    DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                    mod.addRow(new Object[]{Integer.toString(i+1), player, Double.toString(vote)});
                }
            }
        }
    }//GEN-LAST:event_playerStatsCBItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       
        if(league.equals("Serie A")) {
            playerStatsCB.addItem("TOP 20 FOR AVG. VOTE");
            playerStatsCB.addItem("LOWEST 20 FOR AVG. VOTE");
        }
        
        setTableModel();
        playerStatsCB.setSelectedIndex(0);
        List<Document> res = mongo.top20MostGoalPlayer(league, year);
            for(int i=0; i<res.size(); ++i) {
                Document doc = res.get(i);
                String player = (String)doc.get("_id");
                int goals = (int)doc.get("goalCount");
                DefaultTableModel mod = (DefaultTableModel) playerStatsTable.getModel();
                mod.addRow(new Object[]{Integer.toString(i+1), player, Integer.toString(goals)});
        }
        kGradientPanel1.requestFocus();
    }//GEN-LAST:event_formWindowOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JComboBox<String> playerStatsCB;
    private javax.swing.JTable playerStatsTable;
    // End of variables declaration//GEN-END:variables
}
