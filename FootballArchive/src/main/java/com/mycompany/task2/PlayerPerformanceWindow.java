/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;


public class PlayerPerformanceWindow extends javax.swing.JFrame {

    private MongoManager mongo;
    private String league;
    private String year;
    private boolean firstOpen = true;
    
    public PlayerPerformanceWindow(MongoManager m, String l, String y) {
        initComponents();
        
        mongo = m;
        league = l;
        year = y;

        populateTeams();
        setStats();
  
    }
   
    private void populateTeams() {
        List<Document> teams = mongo.getTeams(league, year);
        for(int i=0; i< teams.size(); ++i) {       
            Document teamDoc = teams.get(i);
            String name = teamDoc.get("team").toString();
            teamsCB.addItem(name);
            if(i==0)
                populatePlayers(name);
        }     
        firstOpen = false;
        kGradientPanel1.requestFocus();
    }
    
    private void populatePlayers(String team) {
        playersCB.removeAllItems();
        List<String> players = mongo.getTeamRoster(league, year, team);
        for(int j=0; j<(players.size()-1); ++j) 
            playersCB.addItem(players.get(j));    
    }
    
    private void setStats() {
        String player = playersCB.getSelectedItem().toString();
        Document doc = mongo.getPlayerStats(league, year, teamsCB.getSelectedItem().toString(), player);
        String role = mongo.getPlayerRole(league, year, teamsCB.getSelectedItem().toString(), player).get("role").toString();
                
        Integer matchesValue = (Integer)doc.get("played");
        Integer startingMatchesValue = (Integer)doc.get("playerAsStarter");
        Integer minutesPlayedValue = (Integer)doc.get("minutesPlayed");
        Integer goalsValue = (Integer)doc.get("goalCount");
        Integer minutesGoalsValue = 0;
        if(goalsValue!=0)
            minutesGoalsValue =  minutesPlayedValue/goalsValue;
        Integer assistsValue = (Integer)doc.get("assistCount");
        Integer yellowCardsValue = (Integer)doc.get("yellowCard");
        Integer redCardsValue = (Integer)doc.get("redCard");

        if(league.equals("Serie A")) {
            Double highestVoteValue = (Double)doc.get("highestVote");
            Double lowestVoteValue = (Double)doc.get("lowestVote");
            if(doc.get("averageVote") == null) { //player who hasn't any vote
                averageVote.setText("0");
                highestVote.setText("0");
                lowestVote.setText("0");
            }
            else {
                double avg = (double)doc.get("averageVote");
                Double averageVoteValue = Math.floor(avg*100)/100;
                averageVote.setText(averageVoteValue.toString());
                highestVote.setText(highestVoteValue.toString());
                lowestVote.setText(lowestVoteValue.toString());
            }   
        }
        else {
            highestVote.setText("N.A.");
            lowestVote.setText("N.A.");
            averageVote.setText("N.A.");
        }
        
        matches.setText(matchesValue.toString());
        startingMatches.setText(startingMatchesValue.toString());
        minutesPlayed.setText(minutesPlayedValue.toString());
        goals.setText(goalsValue.toString());
        minutesGoals.setText(minutesGoalsValue.toString());
        assists.setText(assistsValue.toString());
        yellowCards.setText(yellowCardsValue.toString());
        redCards.setText(redCardsValue.toString());
        
        if(role.equals("Portiere")) {
            goalsLB.setVisible(false);
            goals.setVisible(false);
            minutesGoalsLB.setVisible(false);
            minutesGoals.setVisible(false);
        }
        else {
            goalsLB.setVisible(true);
            goals.setVisible(true);
            minutesGoalsLB.setVisible(true);
            minutesGoals.setVisible(true);
        }
           
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        teamsCB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        playersCB = new javax.swing.JComboBox<>();
        goalsLB = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        goals = new javax.swing.JLabel();
        matches = new javax.swing.JLabel();
        minutesPlayed = new javax.swing.JLabel();
        yellowCards = new javax.swing.JLabel();
        minutesGoalsLB = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        assistsLB = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        minutesGoals = new javax.swing.JLabel();
        startingMatches = new javax.swing.JLabel();
        assists = new javax.swing.JLabel();
        redCards = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        highestVote = new javax.swing.JLabel();
        lowestVote = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        averageVote = new javax.swing.JLabel();

        setTitle("Player Stats");
        setResizable(false);

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Select team");

        teamsCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        teamsCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        teamsCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                teamsCBItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Select player");

        playersCB.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        playersCB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playersCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                playersCBItemStateChanged(evt);
            }
        });

        goalsLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        goalsLB.setForeground(new java.awt.Color(240, 240, 240));
        goalsLB.setText("Goals");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Matches");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("Minutes played");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 240, 240));
        jLabel10.setText("Yellow Cards");

        goals.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        goals.setForeground(new java.awt.Color(255, 255, 102));
        goals.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goals.setText("10");

        matches.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        matches.setForeground(new java.awt.Color(255, 255, 102));
        matches.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matches.setText("10");

        minutesPlayed.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        minutesPlayed.setForeground(new java.awt.Color(255, 255, 102));
        minutesPlayed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minutesPlayed.setText("10");

        yellowCards.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        yellowCards.setForeground(new java.awt.Color(255, 255, 102));
        yellowCards.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yellowCards.setText("10");

        minutesGoalsLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        minutesGoalsLB.setForeground(new java.awt.Color(240, 240, 240));
        minutesGoalsLB.setText("Minutes / Goals");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Starting matches");

        assistsLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        assistsLB.setForeground(new java.awt.Color(240, 240, 240));
        assistsLB.setText("Assists");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(240, 240, 240));
        jLabel12.setText("Red Cards");

        minutesGoals.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        minutesGoals.setForeground(new java.awt.Color(255, 255, 102));
        minutesGoals.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minutesGoals.setText("10");

        startingMatches.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        startingMatches.setForeground(new java.awt.Color(255, 255, 102));
        startingMatches.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startingMatches.setText("10");

        assists.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        assists.setForeground(new java.awt.Color(255, 255, 102));
        assists.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assists.setText("10");

        redCards.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        redCards.setForeground(new java.awt.Color(255, 255, 102));
        redCards.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        redCards.setText("10");

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(240, 240, 240));
        jLabel19.setText("Highest vote");

        highestVote.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        highestVote.setForeground(new java.awt.Color(255, 255, 102));
        highestVote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highestVote.setText("10");

        lowestVote.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lowestVote.setForeground(new java.awt.Color(255, 255, 102));
        lowestVote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lowestVote.setText("10");

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(240, 240, 240));
        jLabel22.setText("Lowest vote");

        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(240, 240, 240));
        jLabel23.setText("Average vote");

        averageVote.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        averageVote.setForeground(new java.awt.Color(255, 255, 102));
        averageVote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageVote.setText("10");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playersCB, 0, 203, Short.MAX_VALUE)
                    .addComponent(teamsCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(goalsLB)
                    .addComponent(jLabel19))
                .addGap(36, 36, 36)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(goals, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(matches, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minutesPlayed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yellowCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(highestVote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(averageVote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(80, 80, 80)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(assistsLB)
                    .addComponent(jLabel4)
                    .addComponent(minutesGoalsLB)
                    .addComponent(jLabel22))
                .addGap(35, 35, 35)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(minutesGoals, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(startingMatches, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(assists, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(redCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lowestVote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamsCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(playersCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(goalsLB)
                            .addComponent(goals))
                        .addGap(36, 36, 36)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(matches))
                        .addGap(36, 36, 36)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(minutesPlayed))
                        .addGap(36, 36, 36)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(yellowCards)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minutesGoalsLB)
                            .addComponent(minutesGoals))
                        .addGap(36, 36, 36)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(startingMatches))
                        .addGap(36, 36, 36)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assistsLB)
                            .addComponent(assists))
                        .addGap(36, 36, 36)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(redCards))))
                .addGap(36, 36, 36)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(highestVote)
                    .addComponent(jLabel22)
                    .addComponent(lowestVote))
                .addGap(36, 36, 36)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(averageVote))
                .addContainerGap(41, Short.MAX_VALUE))
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

    private void teamsCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_teamsCBItemStateChanged
         if(evt.getStateChange() == ItemEvent.SELECTED) {
             String name = teamsCB.getSelectedItem().toString();
             populatePlayers(name);
         }
    }//GEN-LAST:event_teamsCBItemStateChanged

    private void playersCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_playersCBItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED &&!firstOpen) {
            setStats();        
        }
    }//GEN-LAST:event_playersCBItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assists;
    private javax.swing.JLabel assistsLB;
    private javax.swing.JLabel averageVote;
    private javax.swing.JLabel goals;
    private javax.swing.JLabel goalsLB;
    private javax.swing.JLabel highestVote;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lowestVote;
    private javax.swing.JLabel matches;
    private javax.swing.JLabel minutesGoals;
    private javax.swing.JLabel minutesGoalsLB;
    private javax.swing.JLabel minutesPlayed;
    private javax.swing.JComboBox<String> playersCB;
    private javax.swing.JLabel redCards;
    private javax.swing.JLabel startingMatches;
    private javax.swing.JComboBox<String> teamsCB;
    private javax.swing.JLabel yellowCards;
    // End of variables declaration//GEN-END:variables
}
