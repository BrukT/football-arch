/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class TeamStatsWindow extends javax.swing.JFrame {

    private Document stats;
    
    public TeamStatsWindow(Document s, String t) {
        initComponents();
        teamName.setText(t);
        setTitle("Stats " + t);
        stats = s;
        
        setTableModel();
        populateTable();
    }
    
    private void setTableModel() {
        statsTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 20));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        statsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        statsTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        statsTable.getColumnModel().getColumn(0).setMinWidth(200);
    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) statsTable.getModel();
        
        for(int i=1; i<stats.size(); ++i) {
            String key = stats.keySet().toArray()[i].toString();
            String[] split = key.split("avg");
            
            Double tmp =  (Double) stats.get(key);
            if(tmp != null) {
                double value = Math.floor(tmp*100)/100;
                model.addRow(new Object[]{split[1], value});
            }
        }
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel2 = new keeptoo.KGradientPanel();
        teamName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        statsTable = new javax.swing.JTable();

        setResizable(false);

        kGradientPanel2.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel2.setkStartColor(new java.awt.Color(2, 4, 70));

        teamName.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        teamName.setForeground(new java.awt.Color(240, 240, 240));
        teamName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        teamName.setText("teamName");

        statsTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        statsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        statsTable.setFocusable(false);
        statsTable.setRowHeight(35);
        statsTable.setShowVerticalLines(false);
        jScrollPane2.setViewportView(statsTable);

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addComponent(teamName, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(teamName)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JTable statsTable;
    private javax.swing.JLabel teamName;
    // End of variables declaration//GEN-END:variables
}
