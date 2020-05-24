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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class MatchWindow extends javax.swing.JFrame {

    private Document match;
   
    public MatchWindow(String league, String year, String round, Document m, String date, String result) {
        initComponents();
        
        match = m;
        
        seasonDayLabel.setText(league + " " + year + " - " + round);
        dateLabel.setText(date);
        resultLabel.setText(result);
        homeTeamLabel.setText(match.get("homeTeam").toString());
        awayTeamLabel.setText(match.get("awayTeam").toString());
        setTitle(homeTeamLabel.getText() + " - " + awayTeamLabel.getText());
        refereeLabel.setText("Referee: " + match.get("referee").toString());
        satdiumLabel.setText("Stadium: " + match.get("stadium").toString() + " - Attendance: "+ match.get("attendance").toString());     
        
        setTableModel(homeLineUpTable, homeTeamLabel.getText());
        setTableModel(awayLineUpTable, awayTeamLabel.getText());
        setStatsTable();
        populateGoalsTable();
        populateLineUps();
        setCoach();
        populateStatsTable();
        if(!league.equals("Serie A")) {
            homeLineUpTable.removeColumn(homeLineUpTable.getColumnModel().getColumn(6));
            awayLineUpTable.removeColumn(awayLineUpTable.getColumnModel().getColumn(6));            
        }           
    }
    
        // to set table layout
    private void setTableModel(JTable table, String team) {
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setMinWidth(220);
        table.getColumnModel().getColumn(1).setHeaderValue(team);
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
    }
    
    private void setStatsTable() {
        statsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        statsTable.getColumnModel().getColumn(0).setHeaderValue(homeTeamLabel.getText());
        statsTable.getColumnModel().getColumn(2).setHeaderValue(awayTeamLabel.getText());
        statsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        statsTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        statsTable.getColumnModel().getColumn(1).setMinWidth(200);
        statsTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
    }
    
    private void populateGoalsTable() {
        DefaultTableModel mod = (DefaultTableModel) goalsTable.getModel();
        
        List<Document> goals = (ArrayList)match.get("goal");
        if(goals == null)
            return;
        
        for(int i=0; i<goals.size(); ++i) {
            Document goal = goals.get(i);
            String partialResult = goal.get("homePartial").toString() + " - " + goal.get("awayPartial").toString();
            String minute = goal.get("minute").toString();
            String scorer = goal.get("scorer").toString();
            String kind = goal.get("kind").toString();
            String assist = goal.get("assist").toString();
            
            if(assist.equals(""))
                mod.addRow(new Object[]{" " + partialResult + "     " + minute + "'  " + scorer + " (" + kind + ")"});
            else
                mod.addRow(new Object[]{" " + partialResult + "     " + minute + "'  " + scorer + " (" + kind + ")  -  Assist: " + assist  });
        }    
    }
    
    private void populateLineUps() {
        DefaultTableModel homeModel = (DefaultTableModel) homeLineUpTable.getModel();
        DefaultTableModel awayModel = (DefaultTableModel) awayLineUpTable.getModel();
        
        Document homeLineUp = (Document)match.get("homeLineup");
        List<Document> homePlayers = (ArrayList)homeLineUp.get("player");
                     
        Document awayLineUp = (Document)match.get("awayLineup");
        List<Document> awayPlayers = (ArrayList)awayLineUp.get("player");
        
        insertStarterPlayers(homePlayers, homeModel);
        insertStarterPlayers(awayPlayers, awayModel);


    }
    
    private void insertStarterPlayers(List<Document> list, DefaultTableModel model) {
        
        for(int i=0; i<list.size(); ++i) { 
            
            if(i==11) {
                model.addRow(new Object[]{null, null, null, null, null, null, null});
            }
            
            Document homePlayer = list.get(i);

            String number = homePlayer.get("number").toString();
            String name = homePlayer.get("name").toString();
            
            Document event = (Document)homePlayer.get("event");
            String eventType;
            String yellowTime = "";
            String redTime = "";
            if(event != null) {
                eventType = event.get("event").toString();
                if(eventType.equals("giallo"))
                    yellowTime = event.get("time").toString()+ "'";
                if(eventType.equals("rosso") || eventType.equals("Giallo-rosso"))
                    redTime = event.get("time").toString()+ "'";
            }          
            
            String leaveTime = "";
            String enterTime = ""; 
            
            Integer enter = (Integer) homePlayer.get("enterTime");
            if (enter != null) 
                enterTime = enter.toString() + "'";
            
            Integer leave = (Integer) homePlayer.get("leaveTime");
            if (leave != null)
                leaveTime = leave.toString() +  "'";
            
            Double vote = (Double)homePlayer.get("vote");
                
            model.addRow(new Object[]{number, name, yellowTime, redTime, enterTime, leaveTime, vote});     
        }
    }
    
    private void setCoach() {
        String coach = (String)match.get("homeCoach");
        String[] split = coach.split(": ");     
        homeCoachLabel.setText(" Coach: " + split[1]);
        
        coach = (String)match.get("awayCoach");
        split = coach.split(": ");     
        awayCoachLabel.setText(" Coach: " + split[1]);    
    }
    
    private void populateStatsTable() {
        DefaultTableModel model = (DefaultTableModel) statsTable.getModel();
        
        List<Document> homeStats = (ArrayList)match.get("statisticHome");
        List<Document> awayStats = (ArrayList)match.get("statisticAway");
        
        if(homeStats == null || awayStats == null) 
            return;
        
        for(int i=0; i<homeStats.size(); ++i) {
            Document hDoc = homeStats.get(i);
            String stat = hDoc.keySet().toArray()[0].toString();
            String hValue = hDoc.get(stat).toString();
            
            Document aDoc = awayStats.get(i);
            String aValue = aDoc.get(stat).toString();
            
            model.addRow(new Object[]{hValue, stat, aValue});     
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        seasonDayLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        homeTeamLabel = new javax.swing.JLabel();
        resultLabel = new javax.swing.JLabel();
        awayTeamLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        goalsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        homeLineUpTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        awayLineUpTable = new javax.swing.JTable();
        satdiumLabel = new javax.swing.JLabel();
        refereeLabel = new javax.swing.JLabel();
        homeCoachLabel = new javax.swing.JLabel();
        awayCoachLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        statsTable = new javax.swing.JTable();

        setResizable(false);

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        seasonDayLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        seasonDayLabel.setForeground(new java.awt.Color(240, 240, 240));
        seasonDayLabel.setText("jLabel1");

        dateLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(240, 240, 240));
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateLabel.setText("jLabel2");

        homeTeamLabel.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        homeTeamLabel.setForeground(new java.awt.Color(240, 240, 240));
        homeTeamLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homeTeamLabel.setText("jLabel3");

        resultLabel.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        resultLabel.setForeground(new java.awt.Color(240, 240, 240));
        resultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultLabel.setText("jLabel4");

        awayTeamLabel.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        awayTeamLabel.setForeground(new java.awt.Color(240, 240, 240));
        awayTeamLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        awayTeamLabel.setText("jLabel6");

        goalsTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        goalsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        goalsTable.setRowHeight(30);
        jScrollPane1.setViewportView(goalsTable);

        homeLineUpTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        homeLineUpTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "YC", "RC", "IN", "OUT", "V"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        homeLineUpTable.setRowHeight(25);
        jScrollPane2.setViewportView(homeLineUpTable);

        awayLineUpTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        awayLineUpTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "YC", "RC", "IN", "OUT", "V"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        awayLineUpTable.setRowHeight(25);
        jScrollPane3.setViewportView(awayLineUpTable);

        satdiumLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        satdiumLabel.setForeground(new java.awt.Color(240, 240, 240));
        satdiumLabel.setText("jLabel7");

        refereeLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        refereeLabel.setForeground(new java.awt.Color(240, 240, 240));
        refereeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        refereeLabel.setText("jLabel8");

        homeCoachLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        homeCoachLabel.setForeground(new java.awt.Color(240, 240, 240));
        homeCoachLabel.setText("jLabel1");

        awayCoachLabel.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        awayCoachLabel.setForeground(new java.awt.Color(240, 240, 240));
        awayCoachLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        awayCoachLabel.setText("jLabel2");

        statsTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        statsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        statsTable.setRowHeight(25);
        jScrollPane4.setViewportView(statsTable);

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kGradientPanel1Layout.createSequentialGroup()
                                    .addComponent(satdiumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(refereeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addComponent(homeCoachLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(awayCoachLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(seasonDayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(homeTeamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                                .addComponent(awayTeamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)))))
                .addContainerGap())
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seasonDayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(homeTeamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(awayTeamLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refereeLabel)
                    .addComponent(satdiumLabel))
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(homeCoachLabel)
                    .addComponent(awayCoachLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel awayCoachLabel;
    private javax.swing.JTable awayLineUpTable;
    private javax.swing.JLabel awayTeamLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTable goalsTable;
    private javax.swing.JLabel homeCoachLabel;
    private javax.swing.JTable homeLineUpTable;
    private javax.swing.JLabel homeTeamLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel refereeLabel;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JLabel satdiumLabel;
    private javax.swing.JLabel seasonDayLabel;
    private javax.swing.JTable statsTable;
    // End of variables declaration//GEN-END:variables
}
