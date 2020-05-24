/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class TeamsWindow extends javax.swing.JFrame {

    private String league;
    private String year;
    private MongoManager mongo;
    private List<Document> teams;
    private List<Document> stats;
    private boolean statsCalculated = false;
    
    public TeamsWindow(MongoManager m, String l, String y) {
        initComponents();
        league = l;
        year = y;
        mongo = m;
        
        seasonLabel.setText(league + " " + year);
        
        setTableModel();
        populateTable();
    }
    
    private void setTableModel() {
        teamsTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        teamsTable.getColumnModel().getColumn(0).setMinWidth(150);
        teamsTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        teamsTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        teamsTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );

    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) teamsTable.getModel();

        teams = mongo.getTeams(league, year);
                
        for(int i=0; i<teams.size(); ++i) {
            Document teamDoc = teams.get(i);
            String name = teamDoc.get("team").toString();
            
            model.addRow(new Object[]{name, "Info", "Roster", "Stats"});     
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        teamsTable = new javax.swing.JTable();
        seasonLabel = new javax.swing.JLabel();

        setTitle("Teams");
        setResizable(false);

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        teamsTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        teamsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        teamsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teamsTable.setRowHeight(35);
        teamsTable.setShowVerticalLines(false);
        teamsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                teamsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(teamsTable);

        seasonLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        seasonLabel.setForeground(new java.awt.Color(240, 240, 240));
        seasonLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        seasonLabel.setText("league_season");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(seasonLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(seasonLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
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

    private void teamsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teamsTableMouseClicked
        int row = teamsTable.rowAtPoint(evt.getPoint());
        int col = teamsTable.columnAtPoint(evt.getPoint());
        
        if(col == 1){ //info
            Document teamDoc = teams.get(row);
            Document info = teamDoc.get("info", Document.class);
            String team = teamsTable.getModel().getValueAt(row, 0).toString();
            new TeamInfoWindow(info, team).setVisible(true);
           
        }
        if(col == 2) { //roster
            Document teamDoc = teams.get(row);
            List<Document> players = (ArrayList)teamDoc.get("player");
            String team = teamsTable.getModel().getValueAt(row, 0).toString() + " " + year;
            new RosterWindow(players, team).setVisible(true);
        }
        
        if (col == 3) { //stats
            if(!statsCalculated) {
                stats = mongo.getTeamsStats(league, year);
                statsCalculated = true;
            }
            Document statsTeam = stats.get(row);
            String team = teamsTable.getModel().getValueAt(row, 0).toString() + " " + year;
            new TeamStatsWindow(statsTeam, team).setVisible(true);            
        }        
        
    }//GEN-LAST:event_teamsTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel seasonLabel;
    private javax.swing.JTable teamsTable;
    // End of variables declaration//GEN-END:variables
}
