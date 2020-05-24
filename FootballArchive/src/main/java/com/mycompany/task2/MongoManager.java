/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

import org.bson.Document;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bson.BSON;
import org.bson.conversions.Bson;


public class MongoManager {

    private final String DB_NAME = "football";
    //private final int port;
    //private final String ip;
    private final long MAX_PLAYER_STATS = 20;
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> accountCollection;
    MongoCollection<Document> leagueCollection;
    MongoCollection<Document> rankingCollection;
    MongoCollection<Document> teamCollection;
    MongoCollection<Document> playerStatCollection;
    MongoCollection<Document> teamStatCollection;

    public MongoManager(List servers) {
        //this.port = 27017;
        //this.ip = "127.0.0.1";
        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(servers))
                        .build());
        database = mongoClient.getDatabase(DB_NAME);
        String name = database.getName();
        System.out.println("Database name is: " + name);
        accountCollection = database.getCollection("account");
        leagueCollection = database.getCollection("league");
        rankingCollection = database.getCollection("ranking");
        teamCollection = database.getCollection("team");
        playerStatCollection = database.getCollection("playerStat");
        teamStatCollection = database.getCollection("teamStat");
        
        
//        System.out.println(getTeamRankings("Serie A", "2017-2018"));
                    
    }

    MongoManager(Array servers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void quit() {
        mongoClient.close();
    }

    //ACCOUNT MANAGEMENT
    
    public boolean register(String username, String password, String favoriteLeague) {
        /* Allow registration of a user, not admin accounts, if not already existing*/
        Document doc = accountCollection.find(eq("username", username)).first();
        if (doc == null) {
            doc = new Document("username", username)
                    .append("password", password)
                    .append("favoriteLeague", favoriteLeague)
                    .append("access", 0);

            accountCollection.insertOne(doc);

            System.out.println("Account '" + username + "' successfully registered!");
            return true;
        } else {
            System.out.println("Username '" + username + "' already registered!");
            return false;
        }

    }
        
    public int login(String username, String password){
        /* trivial login function: returns
        0 if wrong username/password
        1 if login confirmed for user
        2 if login confirmed for admin        
        */
        Document doc = accountCollection.find(and(eq("username", username), eq("password", password))).first();
        if(doc != null){
            if(doc.get("admin") != null)
                return 2;
            else {
                accountCollection.updateOne(
                    new Document("username", username),  
                    new Document("$inc", new Document("access", 1)));
                return 1;
            }
        }
        else
            return 0;
    }
    
    public int removeAccount(String username, String password, String usernameToRemove){
        /* ONLY ADMIN CAN DO THIS!! -> insert admin credential plus target account
            Returns:
                -1: wrong credential
                0: no such username to remove
            1: account successfully removed
        */
        int result = -1;
        int loginResult = login(username, password);
        if(loginResult == 2){
            DeleteResult deleteResult = accountCollection.deleteOne(eq("username", usernameToRemove));
            result = (int) deleteResult.getDeletedCount();
            if(result != 0)
                System.out.println("Account '"+usernameToRemove+"' successfully removed");
            else
                System.out.println("No such username: " + usernameToRemove);
            
            
        }
        else{
            System.out.println("Wrong credentials. You need to be an admin to perform this operation.");
        }
        return result;
    }
    
    public String getFavouriteLeague(String username) {
        Document doc = accountCollection.find(eq("username", username)).first();
        String res =  (String) doc.get("favoriteLeague");
        return res;
    }
    
    //END OF ACCOUNT MANAGEMENT
    
    //----------------------------
    
//ANALYTICS
    //TOP20 METHODS
            
    public List<Document> top20VotePlayer(String league, String year, int top){
        /*
        Top ten average vote players for a given league-year combination
        top is to define if you want the highest ten vote or lowest ten vote
        top == 1   lowest ten vote
        top == -1  highest ten vote
        
        
        RETURN DOC TYPE
        {_id=Gonzalo Higuain, averageVote=7.61, team=Napoli, played=34}
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                new Document("$addFields", new Document("player", new Document("$concatArrays", Arrays.asList("$round.match.homeLineup.player", "$round.match.awayLineup.player")))),
                Aggregates.project(new Document("player", 1).append("round", 1)),
                Aggregates.unwind("$player"),
                Aggregates.project(new Document("player", 1).append("team", new Document(
                        new Document("$cond", new Document("if", new Document("$in", Arrays.asList("$player", "$round.match.homeLineup.player"))).append("then", "$round.match.homeTeam").append("else", "$round.match.awayTeam"))
                ))),
                Aggregates.match(eq("player.vote", new Document("$exists", true).append("$ne", null))),
                Aggregates.group("$player.name", Accumulators.avg("averageVote", "$player.vote"), Accumulators.first("team", "$team"), Accumulators.sum("played", 1)),
                Aggregates.sort(new Document("averageVote", top).append("_id", 1)),
                Aggregates.limit(20)                                  
        )).iterator();

        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }            
        
    public List<Document> top20MostGoalPlayer(String league, String year){
        /*
        Top ten goal scorer players for a given league-year combination
        
        
        RETURN DOC TYPE
        {_id=Andrea Belotti, goalCount=12}
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                Aggregates.unwind("$round.match.goal"),
                Aggregates.group("$round.match.goal.scorer", Accumulators.sum("goalCount", 1)),
                Aggregates.sort(new Document("goalCount", -1).append("_id", 1)),
                Aggregates.limit(20)                                  
        )).iterator();
        
        
        
        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }  
     
    
    public List<Document> top20MostAssistPlayer(String league, String year){
        /*
        Top ten assist-man players for a given league-year combination
        
        RETURN DOC TYPE
        {_id=Giacomo Bonaventura, assistCount=9}
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                Aggregates.unwind("$round.match.goal"),
                Aggregates.group("$round.match.goal.assist", Accumulators.sum("assistCount", 1)),
                Aggregates.sort(new Document("assistCount", -1).append("_id", 1)),
                Aggregates.limit(20)                                  
        )).iterator();
        
        
        
        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }
 
    public List<Document> top20RedCardPlayer(String league, String year){
        /*
        Top ten red card players for a given league-year combination
        
        RETURN DOC TYPE
        {_id=Giacomo Bonaventura, redCard=2, played=6}
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                new Document("$addFields", new Document("player", new Document("$concatArrays", Arrays.asList("$round.match.homeLineup.player", "$round.match.awayLineup.player")))),
                Aggregates.project(new Document("player", 1)),
                Aggregates.unwind("$player"),
                Aggregates.group("$player.name", Accumulators.sum("redCard", new Document("$cond", new Document("if", new Document("$eq", Arrays.asList("$player.event.event", "rosso"))).append("then", 1).append("else", 0))), Accumulators.sum("played", 1)),
                Aggregates.match(new Document("redCard", new Document("$gt", 0))),
                Aggregates.sort(new Document("redCard", -1).append("_id", 1)),
                Aggregates.limit(20)                                  
        )).iterator();

        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }
    
    public List<Document> top20YellowCardPlayer(String league, String year){
        /*
        Top ten yellow card players for a given league-year combination
        
        RETURN DOC TYPE
        {_id=Giacomo Bonaventura, yellowCard=2, played=6}
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                new Document("$addFields", new Document("player", new Document("$concatArrays", Arrays.asList("$round.match.homeLineup.player", "$round.match.awayLineup.player")))),
                Aggregates.project(new Document("player", 1)),
                Aggregates.unwind("$player"),
                Aggregates.group("$player.name", Accumulators.sum("yellowCard", new Document("$cond", new Document("if", new Document("$eq", Arrays.asList("$player.event.event", "giallo"))).append("then", 1).append("else", 0))), Accumulators.sum("played", 1)),
                Aggregates.match(new Document("yellowCard", new Document("$gt", 0))),
                Aggregates.sort(new Document("yellowCard", -1).append("_id", 1)),
                Aggregates.limit(20)                                  
        )).iterator();

        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }
    
    //END OF TOP20 METHODS
    
    //----------------------------
    
    //PLAYER STATS
    
    public Document updatePlayerStats(String league, String year, String name, int access, boolean insert) {
        /*
        update player in playerStat collection
        */
        
        
        
        /*
            First query to compute:                
                matches as starter,
                minutes played
                red/yellow cards
                vote statistics (min max avg)
         */
        Document playerStat = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                new Document("$addFields", new Document("player", new Document("$concatArrays", Arrays.asList("$round.match.homeLineup.player", "$round.match.awayLineup.player")))),
                Aggregates.project(new Document("player", 1)),
                Aggregates.unwind("$player"),
                Aggregates.match(eq("player.name", name)),
                
                Aggregates.group("$player.name", 
                        Accumulators.sum("played", new Document(
                                "$cond", new Document(
                                        "if", new Document(
                                                new Document("$or", Arrays.asList(
                                                                new Document("$eq", Arrays.asList("$player.starter", true)), 
                                                                new Document("$and", Arrays.asList(
                                                                        new Document("$eq", Arrays.asList("$player.starter", false)), 
                                                                        new Document("$ne", Arrays.asList(new Document("$type", "$player.enterTime"), "missing")))))
                                                ))).append("then", 1).append("else", 0))), 
                        
                        Accumulators.sum("playerAsStarter", new Document(
                                "$cond", new Document(
                                        "if", new Document("$eq", Arrays.asList("$player.starter", true)))
                                        .append("then", 1)
                                        .append("else", 0)
                        )),
                        Accumulators.sum("minutesPlayed", new Document(
                                "$cond", new Document("if",
                                        new Document("$eq", Arrays.asList("$player.starter", true)))
                                        .append("then", new Document(
                                                "$cond", new Document("if",
                                                        new Document("$ne", Arrays.asList(new Document("$type", "$player.leaveTime"), "missing")))
                                                        .append("then", "$player.leaveTime")
                                                        .append("else", 90))
                                        )
                                        .append("else", new Document(
                                                "$cond", new Document("if",
                                                        new Document("$and", Arrays.asList(
                                                                new Document("$eq", Arrays.asList("$player.starter", false)),
                                                                new Document("$ne", Arrays.asList(new Document("$type", "$player.enterTime"), "missing"))
                                                        )))
                                                        .append("then", new Document(
                                                                "$cond", new Document("if",
                                                                        new Document("$ne", Arrays.asList(new Document("$type", "$player.leaveTime"), "missing")))
                                                                        .append("then", new Document("$subtract", Arrays.asList("$player.leaveTime", "$player.enterTime")))
                                                                        .append("else", new Document("$subtract", Arrays.asList(90, "$player.enterTime"))))
                                                        )
                                                        .append("else", 0)))
                        )),
                        
                        Accumulators.sum("yellowCard", new Document(
                                    "$cond", new Document(
                                        "if", new Document("$eq", Arrays.asList("$player.event.event", "giallo")))
                                                .append("then", 1)
                                                .append("else", 0)
                        )),
                        
                        
                        Accumulators.sum("redCard", new Document(
                                    "$cond", new Document(
                                        "if", new Document("$eq", Arrays.asList("$player.event.event", "rosso")))
                                                .append("then", 1)
                                                .append("else", 0)
                        )),
                        
                        Accumulators.max("highestVote", "$player.vote"),
                        Accumulators.min("lowestVote", "$player.vote"),
                        Accumulators.avg("averageVote", "$player.vote")
                        
                ),     
                new Document("$addFields", new Document("year", year).append("league", league).append("player", name)),
                Aggregates.project(new Document("_id", 0))       
        )).first();
        
        /* 
            Second query to compute goals & assists 
        */
        Document tmp = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$round"),
                Aggregates.unwind("$round.match"),
                Aggregates.unwind("$round.match.goal"),
                Aggregates.match(new Document("$or", 
                        Arrays.asList(
                                new Document("round.match.goal.assist", name), 
                                new Document("round.match.goal.scorer", name)))
                ),
                
                Aggregates.group(name, 
                        Accumulators.sum("goalCount", new Document(
                                "$cond", new Document(
                                        "if", new Document("$eq", Arrays.asList("$round.match.goal.scorer", name)))
                                        .append("then", 1)
                                        .append("else", 0))), 
                        
                        Accumulators.sum("assistCount", new Document(
                                "$cond", new Document(
                                        "if", new Document("$eq", Arrays.asList("$round.match.goal.assist", name)))
                                        .append("then", 1)
                                        .append("else", 0)))  
                )                   
        )).first();
        
        double zeroVal = 0;
        if(playerStat == null)
            playerStat = new Document("played", 0)
                    .append("playerAsStarter", 0)
                    .append("minutesPlayed", 0)
                    .append("yellowCard", 0)
                    .append("redCard", 0)
                    .append("highestVote", zeroVal)
                    .append("lowestVote", zeroVal)
                    .append("averageVote", zeroVal)
                    .append("year", year)
                    .append("league", league)
                    .append("player", name);
        
        if(tmp != null)
            playerStat.append("goalCount", tmp.get("goalCount")).append("assistCount", tmp.get("assistCount"));
        else
            playerStat.append("goalCount", 0).append("assistCount", 0);
        
        //Access counter
        playerStat.append("access", access);
        
        //insert in collection if needed
        if(insert){
            /*
            Entry to be replaced
            */
            Document deleted = playerStatCollection.findOneAndDelete(
                new Document("league", league).append("year", year).append("player", name)
            );
            /*
            Insert the new one
            */
            playerStatCollection.insertOne(
                playerStat
            );   
        }
        
        //Return the just computed statistics
        return playerStat;        
    }
    
    public Document getPlayerStats(String league, String year, String team, String name){
        Document stat = playerStatCollection.find(
                new Document("league", league).append("year", year).append("player", name)
        ).first();
        
        /*
        If stat exists: increase access counter and return it
        
        Else
        If given stat does not exists: need to compute and store
            20max stats per league-year combination are kept in database
            So if 20 stats are already present a replacement policy must be used
                Replace the less accessed playerStat with the new one
                This is achieved by means of a counter (of accesses)                    
        */        
        
        if(stat != null){
            System.out.println("stat already there!");
            //Update counter (++) and return doc
            playerStatCollection.updateOne(
                new Document("league", league).append("year", year).append("player", name),  
                new Document("$inc", new Document("access", 1))
            );
            //Update counter (++) also in the roster
            incPlayerAccess(league, year, team, name);            
            return stat;
        }
        else{
            //System.out.println("stat missing!");
            //Count how many player stat already in collection 
            //for this league-year
            long nDocs = playerStatCollection.countDocuments(
                new Document("league", league).append("year", year)
            );
            
            //System.out.println("#ndocs: "+nDocs);
            
            // if less than MAX_PLAYER_STATS -> just compute and insert + return
            // else replace less used with the requested one
            if(nDocs < MAX_PLAYER_STATS){
                //System.out.println("***stat not filled, added");
                //It's a new stat, so access counter is set to 1 (just accessed)
                setPlayerAccess(league, year, team, name, 1);
                return updatePlayerStats(league, year, name, 1, true);
            }
            else{                
                //Increment counter of access for searched stats
                incPlayerAccess(league, year, team, name);  
                //System.out.println("***stat filled");
                //Retrieve the less used player stat
                Document toDelete = playerStatCollection.aggregate(Arrays.asList(
                    Aggregates.match(new Document("$and", Arrays.asList(new Document("league", league), new Document("year", year)))),
                    Aggregates.sort(new Document("access", 1).append("_id", 1)),
                    Aggregates.project(new Document("_id", 0).append("league", 1).append("year", 1).append("player", 1).append("access", 1)),
                    Aggregates.limit(1)
                )).first();
                
                /*
                candidate for insertion (the one you just searched)
                toDelete is the possible replacement if:
                    candidateAccess > toDeleteAccess
                
                */
                int toDeleteAccess = toDelete.getInteger("access");              
                int candidateAccess = getPlayerAccess(league, year, team, name);
                if(candidateAccess == 0){
                    setPlayerAccess(league, year, team, name, 1);
                    candidateAccess = 1;
                }
                
                if(candidateAccess > toDeleteAccess){
                    //System.out.println("------stat replaced");
                    //replace
                    //delete it
                    playerStatCollection.deleteOne(
                        new Document("league", toDelete.getString("league")).append("year", toDelete.getString("year")).append("player", toDelete.getString("player"))
                    );  

                    //compute stats (access set to previous value), insert, and return it
                    return updatePlayerStats(league, year, name, candidateAccess, true);  
                }
                else{
                    //Compute stats and return them. No insertions
                    //System.out.println("------stat NOT replaced");
                    return updatePlayerStats(league, year, name, candidateAccess, false);  
                }
                
                              
            }                        
        }
    }
    
    //END OF PLAYER STATS
    
    //----------------------------
    
    //UTILITY METHODS
    
    public List<String> getLeagues(){
        /*
        Get available leagues
        
        RETURN TYPE: list of strings
        */
        MongoCursor<String> cursor = leagueCollection.distinct("league", String.class).iterator();        
        List<String> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }
    
    public List<String> getSeason(String league){
        /*
        Get seasons of a given league
        
        RETURN TYPE: list of strings
        */
        MongoCursor<String> cursor = leagueCollection.distinct("year", new Document("league",league), String.class).iterator();    
        List<String> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }
        
    public List<String> getRounds(String league, String year){
        /*
        Get rounds of a given league-season
        
        RETURN TYPE: list of strings like 1,2,3,4,...,38 (ready to be printed)
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.project(new Document("_id", 0).append("roundCount", new Document("$size", "$round")))
        )).iterator();
        
        List<String> result = new ArrayList<>();
        try {
            if (cursor.hasNext()) {
                Document d = cursor.next();
                int max = d.getInteger("roundCount");
                for(int i=1; i<=max; i++)
                    result.add(Integer.toString(i));
            }
            else{
                return null;
            }
        } finally {
            cursor.close();
        }
        return result;
    }
    
    public List<Document> getMatches(String league, String year, int round){
        /*
        Get matches of a given league-season
        
        RETURN DOC TYPE:
        array of doc at 
            round.match (of that league-year-round)
        the list represent the array "match"
        */
        MongoCursor<Document> cursor = leagueCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.project(new Document("_id", 0).append("matches", 
                        new Document("$arrayElemAt", Arrays.asList("$round", round-1))
                    )                        
                )
        )).iterator();
        
        
        
        List<Document> result = new ArrayList<>();
        try {
            if (cursor.hasNext()) {
                Document d = cursor.next();
                result = ((List<Document>) ((Document) d.get("matches")).get("match"));
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public List<Document> getTeams(String league, String year){
        /*
        Get teams of a given league-season
        with both roster and team informations
        
        RETURN DOC TYPE:
        array of doc
        the list represent the array "team"
        */

        MongoCursor<Document> cursor = teamCollection.find(
                new Document("league", league).append("year", year)
        ).iterator();
        
        
        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document d = cursor.next();
                result.add(d);
            }
        } finally {
            cursor.close();
        }
        return result;

    }    
    
    public List<Document> getTeamsInfo(String league, String year){
        /*
        Get teams of a given league-season
        with teams informations
        
        RETURN DOC TYPE:
        array of doc with team info (name, address...)
        */
        MongoCursor<Document> cursor = teamCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),                
                Aggregates.project(
                    new Document("info", 1)
                )   
        )).iterator();                
        
        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document d = cursor.next();                
                result.add(d);
            }
        } finally {
            cursor.close();
        }
        return result;
    }
    
    public List<String> getTeamRoster(String league, String year, String team){
        /*
            RETURN DOC TYPE:
                {roster=[Etrit Berisha, Pierluigi Gollini, ...]} 
        */
        
        MongoCursor<Document> cursor = teamCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year).append("team", team)),                
                Aggregates.project(
                    new Document("player.name", 1)
                )   
        )).iterator();                
        
        List<String> result = new ArrayList<>();
        try {
            if (cursor.hasNext()) {
                Document d = cursor.next();  
                for(Document tmp: (List<Document>) d.get("player"))
                    result.add(tmp.getString("name"));
            }
        } finally {
            cursor.close();
        }
        return result;   
    }
    
    private int getPlayerAccess(String league, String year, String team, String player){
        Document acc = teamCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year).append("team", team)), 
                Aggregates.unwind("$player"),
                Aggregates.match(eq("player.name", player)),
                new Document("$addFields", new Document("access", "$player.access")),
                Aggregates.project(new Document("_id", 0).append("access", 1))
        )).first();
        Object o = acc.get("access");
        if(o != null)
            return acc.getInteger("access");
        else
            return 0;
    }
    
    private void incPlayerAccess(String league, String year, String team, String player){      
        teamCollection.updateOne(                
            new Document("league", league).append("year", year).append("team", team).append("player.name", player), 
            new Document("$inc", new Document("player.$.access", 1))
        );
    }
    
    private void setPlayerAccess(String league, String year, String team, String player, int value){
        teamCollection.updateOne(                
            new Document("league", league).append("year", year).append("team", team).append("player.name", player), 
            new Document("$set", new Document("player.$.access", 1))
        );
    }
    
    public Document getPlayerRole(String league, String year, String team, String name){
        /*
            RETURN DOC TYPE:
                {role=Portiere}
        
        */
        Document role = teamCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year).append("team", team)),
                Aggregates.unwind("$player"),
                Aggregates.match(eq("player.name", name)),                     
                new Document("$addFields", new Document("role", "$player.role")),
                Aggregates.project(new Document("_id", 0).append("role", 1))
        )).first();
        
        
        return role;
    }
    
    public List<Document> getTeamRankings(String league, String year){
        /*
        Get teams rankings of a given league-season
        (all teams)
        
        RETURN DOC TYPE:
        array of doc at 
            ranking (of that league-year)
        the list represent the array "ranking"
        */
          
        MongoCursor<Document> cursor = rankingCollection.find(
            new Document("league", league).append("year", year)
        ).iterator();
        
        
        List<Document> result = null;
        try {
            if (cursor.hasNext()) {
                Document d = cursor.next();
                //System.out.println(d.get("ranking"));
                return (List<Document>) d.get("ranking");
            }
        } finally {
            cursor.close();
        }
        return result;

    }
    
    //END OF UTILITY METHODS
    
    //----------------------------
    
    //TEAM STATS
    
    public Document computeTeamStats(String league, String year, String team) {

        /*
        Return one document, with the average stats of one team
        */
        
        //Declaring pipeline 
        List<? extends Bson> pipeline = Arrays.asList(
                new Document("$match", new Document("league", league).append("year", year)),
                new Document()
                        .append("$unwind", "$round"),
                new Document()
                        .append("$unwind", "$round.match"),
                new Document()
                        .append("$match", new Document()
                                .append("$or", Arrays.asList(
                                        new Document()
                                                .append("round.match.homeTeam", team),
                                        new Document()
                                                .append("round.match.awayTeam", team)
                                )
                                )
                        ),
                new Document()
                        .append("$project", new Document()
                                .append("season", 1.0)
                                .append("team", team)
                                .append("PossessoPalla", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                0.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                0.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("TiriTotali", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                1.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                1.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("TiriInPorta", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                2.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                2.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("TiriFuori", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                3.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                3.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("TiriBloccati", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                4.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                4.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("CalciDAngolo", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                5.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                5.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("Fuorigioco", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                6.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                6.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("Falli", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                7.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                7.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("CartelliniGialli", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                8.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                8.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("TiriInArea", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                9.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                9.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("TiriDaFuoriArea", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                10.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                10.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("SalvataggiDelPortiere", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                11.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                11.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("Passaggi", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                12.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                12.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("PrecisionePassaggi", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                13.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                13.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("ContrastiVinti", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                14.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                14.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                                .append("ContrastiAereiVinti", new Document()
                                        .append("$cond", new Document()
                                                .append("if", new Document()
                                                        .append("$eq", Arrays.asList(
                                                                "$round.match.homeTeam",
                                                                team
                                                        )
                                                        )
                                                )
                                                .append("then", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticHome",
                                                                15.0
                                                        )
                                                        )
                                                )
                                                .append("else", new Document()
                                                        .append("$arrayElemAt", Arrays.asList(
                                                                "$round.match.statisticAway",
                                                                15.0
                                                        )
                                                        )
                                                )
                                        )
                                )
                        ),
                new Document()
                        .append("$project", new Document()
                                .append("team", 1.0)
                                .append("PossessoPalla", "$PossessoPalla.PossessoPalla")
                                .append("TiriTotali", "$TiriTotali.TiriTotali")
                                .append("TiriInPorta", "$TiriInPorta.TiriInPorta")
                                .append("TiriFuori", "$TiriFuori.TiriFuori")
                                .append("TiriBloccati", "$TiriBloccati.TiriBloccati")
                                .append("CalciDAngolo", "$CalciDAngolo.CalciDAngolo")
                                .append("Fuorigioco", "$Fuorigioco.Fuorigioco")
                                .append("Falli", "$Falli.Falli")
                                .append("CartelliniGialli", "$CartelliniGialli.CartelliniGialli")
                                .append("TiriInArea", "$TiriInArea.TiriInArea")
                                .append("TiriDaFuoriArea", "$TiriDaFuoriArea.TiriDaFuoriArea")
                                .append("SalvataggiDelPortiere", "$SalvataggiDelPortiere.SalvataggiDelPortiere")
                                .append("Passaggi", "$Passaggi.Passaggi")
                                .append("PrecisionePassaggi", "$PrecisionePassaggi.PrecisionePassaggi")
                                .append("ContrastiVinti", "$ContrastiVinti.ContrastiVinti")
                                .append("ContrastiAereiVinti", "$ContrastiAereiVinti.ContrastiAereiVinti")
                        ),
                new Document()
                        .append("$group", new Document()
                                .append("_id", "$team")
                                .append("name", new Document("$first", "$team"))
                                .append("avgPossessoPalla", new Document()
                                        .append("$avg", "$PossessoPalla")
                                )
                                .append("avgTiriTotali", new Document()
                                        .append("$avg", "$TiriTotali")
                                )
                                .append("avgTiriInPorta", new Document()
                                        .append("$avg", "$TiriInPorta")
                                )
                                .append("avgTiriFuori", new Document()
                                        .append("$avg", "$TiriFuori")
                                )
                                .append("avgTiriBloccati", new Document()
                                        .append("$avg", "$TiriBloccati")
                                )
                                .append("avgCalciDAngolo", new Document()
                                        .append("$avg", "$CalciDAngolo")
                                )
                                .append("avgFuorigioco", new Document()
                                        .append("$avg", "$Fuorigioco")
                                )
                                .append("avgFalli", new Document()
                                        .append("$avg", "$Falli")
                                )
                                .append("avgCartelliniGialli", new Document()
                                        .append("$avg", "$CartelliniGialli")
                                )
                                .append("avgTiriInArea", new Document()
                                        .append("$avg", "$TiriInArea")
                                )
                                .append("avgTiriDaFuoriArea", new Document()
                                        .append("$avg", "$TiriDaFuoriArea")
                                )
                                .append("avgSalvataggiDelPortiere", new Document()
                                        .append("$avg", "$SalvataggiDelPortiere")
                                )
                                .append("avgPassaggi", new Document()
                                        .append("$avg", "$Passaggi")
                                )
                                .append("avgPrecisionePassaggi", new Document()
                                        .append("$avg", "$PrecisionePassaggi")
                                )
                                .append("avgContrastiVinti", new Document()
                                        .append("$avg", "$ContrastiVinti")
                                )
                                .append("avgContrastiAereiVinti", new Document()
                                        .append("$avg", "$ContrastiAereiVinti")
                                )
                        ),
                new Document("$project", new Document("_id", 0))
        );        
        
        return leagueCollection.aggregate(pipeline).first();

    }
    
    
    public Document updateTeamsStats(String league, String year){
        List<Document> teams = getTeams(league, year);
        List<Document> teamStats = new ArrayList<>();
        for(Document d : teams){
            String team = ((List<Document>)d.get("info")).get(0).getString("squadra");
            Document stat = computeTeamStats(league, year, team);
            teamStats.add(stat);
        }
        
        /*
        Remove that season entry
        */
        teamStatCollection.deleteOne(
            new Document("name", league).append("year", year)
        );                
     
        /*
        Insert the updated one
        */
        Document newStatDocument = new Document("name", league).append("year", year).append("team", teamStats);
        teamStatCollection.insertOne(
            newStatDocument
        ); 
        
        return newStatDocument;
    }
    
    
    public List<Document> getTeamsStats(String league, String year){
        /*
            RETURN DOC TYPE:
            {name=Premier League, year=2015-2016, team=[ {name:..., stat1:..., stat2:...}, {...}]}
        */
        
//        MongoCursor<Document> cursor = teamStatCollection.find(
//            new Document("name", league).append("year", year)
//        ).iterator();
        
        /*
            If stats exists (found) -> return them
            Else -> compute, insert and then return them
        */
//        if(cursor.hasNext()){
//            //System.out.println("NO COMPUTE");
//            return cursor.next();            
//        }
//        else{
//            //System.out.println("COMPUTE");
//            return updateTeamsStats(league, year);            
//        }

        List<Document> result = new ArrayList<>();
        for(Document d : getTeamsInfo(league, year)){
            result.add(computeTeamStats(league, year, ((Document) d.get("info")).getString("squadra") ));            
        }
        
        return result;        
    }
    
    
    
    public List<Document> getTeamsStatsRanking(String league, String year, String stat){
        /*
            RETURN DOC TYPE:
            {team=Document{{name=West Bromwich Albion, avgPossessoPalla=39.76315789473684}}}
        */
        
//        /* If not exists: compute stats */
//        Document found = teamStatCollection.find(
//            new Document("name", league).append("year", year)
//        ).first();        
//        if(found == null)
//            updateTeamsStats(league, year);
//        
//        MongoCursor<Document> cursor = teamStatCollection.aggregate(Arrays.asList(                                
//                Aggregates.match(eq("name", league)),
//                Aggregates.match(eq("year", year)),
//                Aggregates.unwind("$team"),
//                Aggregates.sort(new Document("team.avg" + stat, -1)),
//                Aggregates.project(
//                    new Document("team.avg" + stat, 1)
//                        .append("team.name", 1))
//        )).iterator();
//        
//        
//        
//        List<Document> result = new ArrayList<>();
//        try {
//            while (cursor.hasNext()) {
//                Document d = cursor.next();
//                result.add(d);
//            }
//        } finally {
//            cursor.close();
//        }
//        return result;

            List<Document> result = getTeamsStats(league, year);
            
            Collections.sort(result, (a, b) -> a.getDouble("avg"+stat) < b.getDouble("avg"+stat) ? 1 : a.getDouble("avg"+stat) == b.getDouble("avg"+stat) ? 0 : -1);
           
            return result;
    }
    
    //END OF TEAM STATS
    
    //----------------------------
    
    // ADMIN INSERT
    
    public void insertNewSeason(String league, String year, Document seasonDoc){
        leagueCollection.insertOne(seasonDoc);
    }
    
    public void insertNewRound(String league, String year, Document roundDoc){        
        /*
            Push the new round in the array round of that league-year doc
        */
        UpdateResult res = leagueCollection.updateOne(
            new Document("league", league).append("year", year), 
            new Document("$push", new Document("round", roundDoc))
        );
                
        /*
            Once a new round has been inserted you must update the teams
            statistics for that league-years.
        */
        //updateTeamsStats(league, year);
        /*
            Once a new round has been inserted you must update the players
            statistics for that league-years. 
            -- Please note that if some statistics are already there, we want 
               to keep the same player in the DB, as they are the most searched 
               one, especially mantaining their access counter
        */
        
        //Get all players stats
        MongoCursor cursor = playerStatCollection.find(
            new Document("league", league).append("year", year)
        ).iterator();
        
        /*
            For each player(present) you delete it, and re-compute
            Then insert with old access counter
        */
        try {
            while (cursor.hasNext()) {
                Document d = (Document) cursor.next();
                String player = d.getString("player");
                int oldAccess = d.getInteger("access");
                
                //delete it
                playerStatCollection.deleteOne(
                    new Document("league", league).append("year", year).append("player", player)
                );  
                
                //compute new stat (access set to oldAccess) and return it
                updatePlayerStats(league, year, player, oldAccess, true); 
                
            }
        } finally {
            cursor.close();
        }
    }
    
    public void insertNewRanking(String league, String year, Document rankingDoc){        
        rankingCollection.insertOne(rankingDoc);
    }
    
    public void insertNewTeams(String league, String year, Document teamArray){       
        teamCollection.insertMany((List<Document>) teamArray.get("leagues"));
    }
    
    public List<Document> top10ActiveUsers() {
    
        //doc type: {"username": "n", "access": 2}
    
        MongoCursor<Document> cursor = accountCollection.find(ne("username", "admin"))
                                        .projection(Projections.exclude("_id", "password", "favoriteLeague"))
                                        .sort(Sorts.descending("access")).limit(10).iterator();
        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
               result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
    }
    
    public List<Document> top20SearchedPlayers(String league, String year) {
        MongoCursor<Document> cursor = teamCollection.aggregate(Arrays.asList(
                Aggregates.match(new Document("league", league).append("year", year)),
                Aggregates.unwind("$player"),
                Aggregates.project(new Document ("player.access", new Document(
                        "$cond", new Document(
                            "if", new Document("$ne", Arrays.asList(new Document("$type", "$player.access"), "missing")))
                                .append("then", "$player.access")
                                .append("else", 0)
                        )         
                    ).append("player.name", 1
                    ).append("info.squadra", 1).append("_id", 0)),
                Aggregates.sort(new Document ("player.access", -1)),
                Aggregates.limit(20)
        )).iterator();   
         
        List<Document> result = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return result;
        
    }
    
    // END OF ADMIN INSERT
        
}
