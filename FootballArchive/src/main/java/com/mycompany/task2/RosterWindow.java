/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class RosterWindow extends javax.swing.JFrame {

    private List<Document> players;
    
    public RosterWindow(List<Document> p, String t) {
        initComponents();
        
        setTitle(t);
        teamName.setText(t);
        players = p;
        
        setTableModel();
        populateTable();
    }
    
    private void setTableModel() {
        rosterTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        rosterTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        rosterTable.getColumnModel().getColumn(2).setMinWidth(200);
        rosterTable.getColumnModel().getColumn(3).setMinWidth(150);
        rosterTable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) rosterTable.getModel();

        for(int i=0; i<players.size(); ++i) {
            Document player = players.get(i);
            Integer number = (Integer)player.get("number");
            String role = player.get("role").toString();
            String name = player.get("name").toString();
            String nationality = player.get("nationality").toString();
            String birth = player.get("birth").toString();
            String [] split = birth.split("T");
            
            model.addRow(new Object[]{number, role, name, nationality, split[0]});        
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel3 = new keeptoo.KGradientPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        rosterTable = new javax.swing.JTable();
        teamName = new javax.swing.JLabel();

        setResizable(false);

        kGradientPanel3.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel3.setkStartColor(new java.awt.Color(2, 4, 70));

        rosterTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        rosterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Role", "Name", "Nationality", "Birth date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rosterTable.setRowHeight(35);
        rosterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rosterTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(rosterTable);

        teamName.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        teamName.setForeground(new java.awt.Color(240, 240, 240));
        teamName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teamName.setText("teamName");

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(teamName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(teamName)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rosterTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rosterTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rosterTableMouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane3;
    private keeptoo.KGradientPanel kGradientPanel3;
    private javax.swing.JTable rosterTable;
    private javax.swing.JLabel teamName;
    // End of variables declaration//GEN-END:variables
}
