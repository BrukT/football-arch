/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import org.bson.Document;


public class ComparePlayersWindow extends javax.swing.JFrame {

    private MongoManager mongo;
    private String league;
    private String year;
    private boolean firstOpen = true;
    
    public ComparePlayersWindow(MongoManager m, String l, String y) {
        initComponents();
        
        mongo = m;
        league = l;
        year = y;
        
        populateTeams();

        setStats(1);
        setStats(2);
    }
    
    private void populateTeams() {
        List<Document> teams = mongo.getTeams(league, year);
        for(int i=0; i< teams.size(); ++i) {       
            Document teamDoc = teams.get(i);
            String name = teamDoc.get("team").toString();
            teamsCB1.addItem(name);
            teamsCB2.addItem(name);        
            if(i==0) 
                populatePlayers(name, playersCB1);
            if(i==1) 
                populatePlayers(name, playersCB2);    
        }  
        teamsCB2.setSelectedIndex(1);
        firstOpen = false;
        kGradientPanel1.requestFocus();
    }

    private void populatePlayers(String team, JComboBox<String> cb) {
        cb.removeAllItems();
        List<String> players = mongo.getTeamRoster(league, year, team);
        for(int j=0; j<(players.size()-1); ++j) 
            cb.addItem(players.get(j));     
    }
    
    private void setStats(int column) {
        if(column == 1) {
            String player = playersCB1.getSelectedItem().toString();
            Document doc = mongo.getPlayerStats(league, year, teamsCB1.getSelectedItem().toString(), player);
            
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
            
            String role = mongo.getPlayerRole(league, year, teamsCB1.getSelectedItem().toString(), player).get("role").toString();
            if(role.equals("Portiere")) 
                role = "Goalkeeper";
            if(role.equals("Difesa"))
                role = "Defender";
            if (role.equals("Centrocampo"))
                role = "Midfielder";
            if (role.equals("Attacco"))
                role = "Striker";
            
            if(league.equals("Serie A")) {
                if(doc.get("averageVote") == null)  //player who hasn't any vote
                    averageVote1.setText("0.0");
                else {
                    double avg = (double)doc.get("averageVote");
                    Double averageVoteValue = Math.floor(avg*100)/100;
                    averageVote1.setText(averageVoteValue.toString());
                }   
            }
            else {
                averageVote1.setText("N.A.");
            }
            
            role1.setText(role);
            matches1.setText(matchesValue.toString());
            startingMatches1.setText(startingMatchesValue.toString());
            goals1.setText(goalsValue.toString());
            minutesGoals1.setText(minutesGoalsValue.toString());
            assists1.setText(assistsValue.toString());
            yellowCards1.setText(yellowCardsValue.toString());
            redCards1.setText(redCardsValue.toString());     
        }
        
        if (column == 2) {
            String player = playersCB2.getSelectedItem().toString();
            Document doc = mongo.getPlayerStats(league, year, teamsCB2.getSelectedItem().toString(), player);
            
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
            
            String role = mongo.getPlayerRole(league, year, teamsCB2.getSelectedItem().toString(), player).get("role").toString();
            if(role.equals("Portiere")) 
                role = "Goalkeeper";
            if(role.equals("Difesa"))
                role = "Defender";
            if (role.equals("Centrocampo"))
                role = "Midfielder";
            if (role.equals("Attacco"))
                role = "Striker";
            
            if(league.equals("Serie A")) {
                if(doc.get("averageVote") == null)  //player who hasn't any vote
                    averageVote2.setText("0");
                else {
                    double avg = (double)doc.get("averageVote");
                    Double averageVoteValue = Math.floor(avg*100)/100;
                    averageVote2.setText(averageVoteValue.toString());
                }   
            }
            else {
                averageVote2.setText("N.A.");
            }
        
            role2.setText(role);
            matches2.setText(matchesValue.toString());
            startingMatches2.setText(startingMatchesValue.toString());
            goals2.setText(goalsValue.toString());
            minutesGoals2.setText(minutesGoalsValue.toString());
            assists2.setText(assistsValue.toString());
            yellowCards2.setText(yellowCardsValue.toString());
            redCards2.setText(redCardsValue.toString());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        teamsCB1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        playersCB1 = new javax.swing.JComboBox<>();
        role1 = new javax.swing.JLabel();
        startingMatches1 = new javax.swing.JLabel();
        goals1 = new javax.swing.JLabel();
        minutesGoals1 = new javax.swing.JLabel();
        assists1 = new javax.swing.JLabel();
        yellowCards1 = new javax.swing.JLabel();
        redCards1 = new javax.swing.JLabel();
        averageVote1 = new javax.swing.JLabel();
        teamsCB2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        playersCB2 = new javax.swing.JComboBox<>();
        goalsLB = new javax.swing.JLabel();
        minutesGoalsLB = new javax.swing.JLabel();
        matchesLB = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        averageVote2 = new javax.swing.JLabel();
        redCards2 = new javax.swing.JLabel();
        yellowCards2 = new javax.swing.JLabel();
        assists2 = new javax.swing.JLabel();
        minutesGoals2 = new javax.swing.JLabel();
        goals2 = new javax.swing.JLabel();
        role2 = new javax.swing.JLabel();
        startingMatches2 = new javax.swing.JLabel();
        roleLB = new javax.swing.JLabel();
        matches1 = new javax.swing.JLabel();
        matches2 = new javax.swing.JLabel();

        setTitle("Compare Players");
        setResizable(false);

        kGradientPanel1.setkEndColor(new java.awt.Color(41, 41, 253));
        kGradientPanel1.setkStartColor(new java.awt.Color(2, 4, 70));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("Select team");

        teamsCB1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        teamsCB1.setForeground(new java.awt.Color(0, 0, 102));
        teamsCB1.setMaximumSize(new java.awt.Dimension(43, 32));
        teamsCB1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                teamsCB1ItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("Select player");

        playersCB1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        playersCB1.setForeground(new java.awt.Color(0, 0, 102));
        playersCB1.setMaximumSize(new java.awt.Dimension(43, 32));
        playersCB1.setName(""); // NOI18N
        playersCB1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                playersCB1ItemStateChanged(evt);
            }
        });

        role1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        role1.setForeground(new java.awt.Color(255, 255, 102));
        role1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        role1.setText("10");

        startingMatches1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        startingMatches1.setForeground(new java.awt.Color(255, 255, 102));
        startingMatches1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startingMatches1.setText("10");

        goals1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        goals1.setForeground(new java.awt.Color(255, 255, 102));
        goals1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goals1.setText("10");

        minutesGoals1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        minutesGoals1.setForeground(new java.awt.Color(255, 255, 102));
        minutesGoals1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minutesGoals1.setText("10");

        assists1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        assists1.setForeground(new java.awt.Color(255, 255, 102));
        assists1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assists1.setText("10");

        yellowCards1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        yellowCards1.setForeground(new java.awt.Color(255, 255, 102));
        yellowCards1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yellowCards1.setText("10");

        redCards1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        redCards1.setForeground(new java.awt.Color(255, 255, 102));
        redCards1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        redCards1.setText("10");

        averageVote1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        averageVote1.setForeground(new java.awt.Color(255, 255, 102));
        averageVote1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageVote1.setText("10");

        teamsCB2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        teamsCB2.setForeground(new java.awt.Color(0, 0, 102));
        teamsCB2.setMaximumSize(new java.awt.Dimension(43, 32));
        teamsCB2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                teamsCB2ItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("Select team");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Select player");

        playersCB2.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        playersCB2.setForeground(new java.awt.Color(0, 0, 102));
        playersCB2.setMaximumSize(new java.awt.Dimension(43, 32));
        playersCB2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                playersCB2ItemStateChanged(evt);
            }
        });

        goalsLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        goalsLB.setForeground(new java.awt.Color(240, 240, 240));
        goalsLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goalsLB.setText("Matches");

        minutesGoalsLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        minutesGoalsLB.setForeground(new java.awt.Color(240, 240, 240));
        minutesGoalsLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minutesGoalsLB.setText("Starting matches");

        matchesLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        matchesLB.setForeground(new java.awt.Color(240, 240, 240));
        matchesLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matchesLB.setText("Goals");

        jLabel28.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(240, 240, 240));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Minutes/Goals");

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(240, 240, 240));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Assists");

        jLabel32.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(240, 240, 240));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Yellow cards");

        jLabel33.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(240, 240, 240));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Red cards");

        jLabel36.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(240, 240, 240));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Avg.vote");

        averageVote2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        averageVote2.setForeground(new java.awt.Color(255, 255, 102));
        averageVote2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageVote2.setText("10");

        redCards2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        redCards2.setForeground(new java.awt.Color(255, 255, 102));
        redCards2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        redCards2.setText("10");

        yellowCards2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        yellowCards2.setForeground(new java.awt.Color(255, 255, 102));
        yellowCards2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yellowCards2.setText("10");

        assists2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        assists2.setForeground(new java.awt.Color(255, 255, 102));
        assists2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assists2.setText("10");

        minutesGoals2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        minutesGoals2.setForeground(new java.awt.Color(255, 255, 102));
        minutesGoals2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minutesGoals2.setText("10");

        goals2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        goals2.setForeground(new java.awt.Color(255, 255, 102));
        goals2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goals2.setText("10");

        role2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        role2.setForeground(new java.awt.Color(255, 255, 102));
        role2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        role2.setText("10");

        startingMatches2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        startingMatches2.setForeground(new java.awt.Color(255, 255, 102));
        startingMatches2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startingMatches2.setText("10");

        roleLB.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        roleLB.setForeground(new java.awt.Color(240, 240, 240));
        roleLB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleLB.setText("Role");

        matches1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        matches1.setForeground(new java.awt.Color(255, 255, 102));
        matches1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matches1.setText("10");

        matches2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        matches2.setForeground(new java.awt.Color(255, 255, 102));
        matches2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matches2.setText("10");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                            .addGap(160, 160, 160)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(startingMatches1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(matches1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(averageVote1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(assists1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(minutesGoals1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                            .addComponent(goals1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(yellowCards1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(redCards1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(role1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(goalsLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minutesGoalsLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(matchesLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roleLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(goals2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startingMatches2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(minutesGoals2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(assists2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yellowCards2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(redCards2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(averageVote2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(matches2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(role2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(teamsCB1, 0, 203, Short.MAX_VALUE)
                    .addComponent(playersCB1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(29, 29, 29)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playersCB2, 0, 203, Short.MAX_VALUE)
                    .addComponent(teamsCB2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(teamsCB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(playersCB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(teamsCB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(playersCB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(86, 86, 86)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roleLB)
                    .addComponent(role1)
                    .addComponent(role2))
                .addGap(30, 30, 30)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startingMatches1)
                            .addComponent(minutesGoalsLB, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startingMatches2))
                        .addGap(30, 30, 30)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(goals1)
                            .addComponent(matchesLB)
                            .addComponent(goals2))
                        .addGap(30, 30, 30)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minutesGoals1)
                            .addComponent(jLabel28))
                        .addGap(30, 30, 30)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(assists1)
                            .addComponent(jLabel21))
                        .addGap(30, 30, 30)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yellowCards1)
                            .addComponent(jLabel32))
                        .addGap(30, 30, 30)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(redCards1)
                            .addComponent(jLabel33))
                        .addGap(30, 30, 30)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(averageVote1)
                            .addComponent(jLabel36)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(minutesGoals2)
                        .addGap(30, 30, 30)
                        .addComponent(assists2)
                        .addGap(30, 30, 30)
                        .addComponent(yellowCards2)
                        .addGap(30, 30, 30)
                        .addComponent(redCards2)
                        .addGap(30, 30, 30)
                        .addComponent(averageVote2))
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(goalsLB)
                        .addComponent(matches1)
                        .addComponent(matches2)))
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void teamsCB1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_teamsCB1ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
             String name = teamsCB1.getSelectedItem().toString();
             populatePlayers(name, playersCB1);
         }
    }//GEN-LAST:event_teamsCB1ItemStateChanged

    private void teamsCB2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_teamsCB2ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
             String name = teamsCB2.getSelectedItem().toString();
             populatePlayers(name, playersCB2);
         }
    }//GEN-LAST:event_teamsCB2ItemStateChanged

    private void playersCB1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_playersCB1ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED &&!firstOpen) 
            setStats(1);
    }//GEN-LAST:event_playersCB1ItemStateChanged

    private void playersCB2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_playersCB2ItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED &&!firstOpen) 
            setStats(2);       
        
    }//GEN-LAST:event_playersCB2ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assists1;
    private javax.swing.JLabel assists2;
    private javax.swing.JLabel averageVote1;
    private javax.swing.JLabel averageVote2;
    private javax.swing.JLabel goals1;
    private javax.swing.JLabel goals2;
    private javax.swing.JLabel goalsLB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel matches1;
    private javax.swing.JLabel matches2;
    private javax.swing.JLabel matchesLB;
    private javax.swing.JLabel minutesGoals1;
    private javax.swing.JLabel minutesGoals2;
    private javax.swing.JLabel minutesGoalsLB;
    private javax.swing.JComboBox<String> playersCB1;
    private javax.swing.JComboBox<String> playersCB2;
    private javax.swing.JLabel redCards1;
    private javax.swing.JLabel redCards2;
    private javax.swing.JLabel role1;
    private javax.swing.JLabel role2;
    private javax.swing.JLabel roleLB;
    private javax.swing.JLabel startingMatches1;
    private javax.swing.JLabel startingMatches2;
    private javax.swing.JComboBox<String> teamsCB1;
    private javax.swing.JComboBox<String> teamsCB2;
    private javax.swing.JLabel yellowCards1;
    private javax.swing.JLabel yellowCards2;
    // End of variables declaration//GEN-END:variables
}
