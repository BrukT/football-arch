/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;


public class ResultsWindow extends javax.swing.JFrame {

    private MongoManager mongo;
    private String username;
    private boolean firstOpen = true; //to not invoke item change for rounds and season
    private boolean newLeague = false; //to not invoke item change for rounds and season (because whe you change the league those 2 CBs are reloaded)
    private boolean newSeason = false;  //to not invoke item change for rounds when you change the season
    private List<Document> matches;
    
    public ResultsWindow(MongoManager m, String u) {
        initComponents();
        mongo = m;
        username = u;
        usernameLabel.setText(username);
        kGradientPanel1.requestFocus();
        
        String favLeague = mongo.getFavouriteLeague(username);
        leagueCB.setSelectedItem(favLeague);
        if(leagueCB.getSelectedItem().toString().equals("Bundesliga")) { //to use the entire table even if the matches are 9
                resultsTable.setRowHeight(39);
        }   
        
        populateComboBoxes();
        setTableModel();
        populateTable(); 
        firstOpen = false; 
      
    }
    
    private void populateComboBoxes() {
        
        String league = leagueCB.getSelectedItem().toString();
        
        List<String> seasons = mongo.getSeason(league);  
        
        List tmp = new ArrayList<>();
        for(int i=(seasons.size() - 1); i>=0; --i) {
            tmp.add(seasons.get(i));
        }
        Collections.sort(tmp);
        
        seasonCB.removeAllItems();
        for(int i=(tmp.size() - 1); i>=0; --i) {
            seasonCB.addItem(tmp.get(i).toString());
        }
        
        seasonCB.setSelectedItem(seasons.size() - 1);
                  
        populateRounds();
    }
    
    private void populateRounds() {
        String league = leagueCB.getSelectedItem().toString();
        
        List<String> seasons = mongo.getSeason(league);
        List<String> rounds = new ArrayList<>();
        for(int i=0; i<seasons.size(); ++i) {
            if(seasons.get(i).equals(seasonCB.getSelectedItem().toString())) {
                rounds = mongo.getRounds(league, seasons.get(i));
                break;
            }
        }
        
        roundsCB.removeAllItems();
        for(int i=(rounds.size() - 1); i>=0; --i) {
            roundsCB.addItem("Round " + rounds.get(i));
        }
        roundsCB.setSelectedItem(rounds.size() - 1);
    }
    
    
    // to set table layout
    private void setTableModel() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        resultsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        resultsTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        resultsTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        resultsTable.getColumnModel().getColumn(2).setMaxWidth(150);
        resultsTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
    }
    
    private void populateTable() {       
        DefaultTableModel mod = (DefaultTableModel) resultsTable.getModel();
        mod.setRowCount(0);
        
        String league = leagueCB.getSelectedItem().toString();
        String year = seasonCB.getSelectedItem().toString();
        String round = roundsCB.getSelectedItem().toString();
        
        String[] splitRes = round.split(" ");
        int numRound = Integer.parseInt(splitRes[1]);
                
        matches = mongo.getMatches(league, year, numRound);
        for(int i=0; i<matches.size(); ++i) {
            Document doc = matches.get(i);
                String homeTeam = (String)doc.get("homeTeam");
                String awayTeam = (String)doc.get("awayTeam");
                int homeResult = (int)doc.get("homeResult");
                int awayResult = (int)doc.get("awayResult");
                String result = Integer.toString(homeResult) + ":" + Integer.toString(awayResult);
                
                String fullDate = (String)doc.get("date");
                String[] split = fullDate.split("T");
                String day = split[0];
                String[] split2 = split[1].split(":");
                String hour = split2[0] + ":" + split2[1];
                
                mod.addRow(new Object[]{day + " " + hour, homeTeam, result, awayTeam});
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
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        roundsCB = new javax.swing.JComboBox<>();
        leagueCB = new javax.swing.JComboBox<>();
        seasonCB = new javax.swing.JComboBox<>();
        standingsButton = new javax.swing.JButton();
        playerStatsButton = new javax.swing.JButton();
        teamsStatsButton = new javax.swing.JButton();
        teamsButton = new javax.swing.JButton();
        playerPerformanceButton = new javax.swing.JButton();
        comparePlayersButton = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();

        setTitle("Football Archive");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        resultsTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        resultsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resultsTable.setFocusable(false);
        resultsTable.setRowHeight(35);
        resultsTable.setShowVerticalLines(false);
        resultsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(resultsTable);

        roundsCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        roundsCB.setForeground(new java.awt.Color(0, 0, 102));
        roundsCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roundsCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                roundsCBItemStateChanged(evt);
            }
        });

        leagueCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        leagueCB.setForeground(new java.awt.Color(0, 0, 102));
        leagueCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Serie A", "Premier League", "La Liga", "Bundesliga" }));
        leagueCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        leagueCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                leagueCBItemStateChanged(evt);
            }
        });

        seasonCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        seasonCB.setForeground(new java.awt.Color(0, 0, 102));
        seasonCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        seasonCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                seasonCBItemStateChanged(evt);
            }
        });

        standingsButton.setBackground(new java.awt.Color(255, 255, 255));
        standingsButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        standingsButton.setForeground(new java.awt.Color(0, 0, 102));
        standingsButton.setText("Standings");
        standingsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        standingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                standingsButtonMouseClicked(evt);
            }
        });

        playerStatsButton.setBackground(new java.awt.Color(255, 255, 255));
        playerStatsButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        playerStatsButton.setForeground(new java.awt.Color(0, 0, 102));
        playerStatsButton.setText("Players Stats");
        playerStatsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playerStatsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playerStatsButtonMouseClicked(evt);
            }
        });

        teamsStatsButton.setBackground(new java.awt.Color(255, 255, 255));
        teamsStatsButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        teamsStatsButton.setForeground(new java.awt.Color(0, 0, 102));
        teamsStatsButton.setText("Teams Stats");
        teamsStatsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teamsStatsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                teamsStatsButtonMouseClicked(evt);
            }
        });

        teamsButton.setBackground(new java.awt.Color(255, 255, 255));
        teamsButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        teamsButton.setForeground(new java.awt.Color(0, 0, 102));
        teamsButton.setText("Teams");
        teamsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teamsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                teamsButtonMouseClicked(evt);
            }
        });

        playerPerformanceButton.setBackground(new java.awt.Color(255, 255, 255));
        playerPerformanceButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        playerPerformanceButton.setForeground(new java.awt.Color(0, 0, 102));
        playerPerformanceButton.setText("Player Stats");
        playerPerformanceButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playerPerformanceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playerPerformanceButtonMouseClicked(evt);
            }
        });

        comparePlayersButton.setBackground(new java.awt.Color(255, 255, 255));
        comparePlayersButton.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        comparePlayersButton.setForeground(new java.awt.Color(0, 0, 102));
        comparePlayersButton.setText("Compare players");
        comparePlayersButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comparePlayersButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comparePlayersButtonMouseClicked(evt);
            }
        });

        usernameLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 102));
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(leagueCB, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(seasonCB, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(standingsButton)
                .addGap(1, 1, 1)
                .addComponent(playerStatsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(teamsStatsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(teamsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(playerPerformanceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comparePlayersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(roundsCB, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(leagueCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(seasonCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(standingsButton)
                    .addComponent(playerStatsButton)
                    .addComponent(teamsStatsButton)
                    .addComponent(teamsButton)
                    .addComponent(playerPerformanceButton)
                    .addComponent(comparePlayersButton))
                .addGap(30, 30, 30)
                .addComponent(roundsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
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

    private void comparePlayersButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comparePlayersButtonMouseClicked
        ComparePlayersWindow cpw = new ComparePlayersWindow(mongo, leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString());
        cpw.setVisible(true);
    }//GEN-LAST:event_comparePlayersButtonMouseClicked

    private void playerPerformanceButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerPerformanceButtonMouseClicked
        PlayerPerformanceWindow ppw = new PlayerPerformanceWindow(mongo, leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString());
        ppw.setVisible(true);
    }//GEN-LAST:event_playerPerformanceButtonMouseClicked

    private void teamsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teamsButtonMouseClicked
        TeamsWindow tw = new TeamsWindow(mongo, leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString());
        tw.setVisible(true);
    }//GEN-LAST:event_teamsButtonMouseClicked

    private void teamsStatsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_teamsStatsButtonMouseClicked
        TeamsStatsWindow tsw = new TeamsStatsWindow(mongo, leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString());
        tsw.setVisible(true);
    }//GEN-LAST:event_teamsStatsButtonMouseClicked

    private void playerStatsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerStatsButtonMouseClicked
        PlayersStatsWindow psw = new PlayersStatsWindow(mongo, leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString());
        psw.setVisible(true);
    }//GEN-LAST:event_playerStatsButtonMouseClicked

    private void standingsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_standingsButtonMouseClicked
        if(mongo.getTeamRankings(leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString())==null) 
            return;
        StandingsWindow sw = new StandingsWindow(mongo, leagueCB.getSelectedItem().toString(), seasonCB.getSelectedItem().toString());
        sw.setVisible(true);
    }//GEN-LAST:event_standingsButtonMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
               
        Frame[] frames = JFrame.getFrames();
        for(int i=0; i<frames.length; ++i) {    
            frames[i].setVisible(false);
        }
        new LoginWindow(mongo).setVisible(true);
        
    }//GEN-LAST:event_formWindowClosing

    private void seasonCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_seasonCBItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED && !firstOpen &&!newLeague) { //because you invoke this function both in selection and deselection of an item    
            newSeason = true;
            populateRounds();
            populateTable(); 
            newSeason = false;
        }
    }//GEN-LAST:event_seasonCBItemStateChanged

    private void roundsCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_roundsCBItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED && !firstOpen && !newLeague && !newSeason) { //because you invoke this function both in selection and deselection of an item           
            populateTable();
        }
    }//GEN-LAST:event_roundsCBItemStateChanged

    private void leagueCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_leagueCBItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED && !firstOpen) { //because you invoke this function both in selection and deselection of an item           
            resultsTable.setRowHeight(35);
            if(leagueCB.getSelectedItem().toString().equals("Bundesliga")) { //to use the entire table even if the matches are 9
                resultsTable.setRowHeight(39);
            }   
            newLeague = true;
            populateComboBoxes();
            populateTable();
            newLeague = false;
        }
    }//GEN-LAST:event_leagueCBItemStateChanged

    private void resultsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTableMouseClicked
        int row = resultsTable.rowAtPoint(evt.getPoint());
        String date = resultsTable.getModel().getValueAt(row, 0).toString();
        String result = resultsTable.getModel().getValueAt(row, 2).toString();
        new MatchWindow(leagueCB.getSelectedItem().toString(),
                seasonCB.getSelectedItem().toString(),
                roundsCB.getSelectedItem().toString(),
                matches.get(row),
                date, 
                result).setVisible(true);      
    }//GEN-LAST:event_resultsTableMouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton comparePlayersButton;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JComboBox<String> leagueCB;
    private javax.swing.JButton playerPerformanceButton;
    private javax.swing.JButton playerStatsButton;
    private javax.swing.JTable resultsTable;
    private javax.swing.JComboBox<String> roundsCB;
    private javax.swing.JComboBox<String> seasonCB;
    private javax.swing.JButton standingsButton;
    private javax.swing.JButton teamsButton;
    private javax.swing.JButton teamsStatsButton;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
