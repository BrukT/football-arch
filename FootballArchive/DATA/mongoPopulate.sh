#!/bin/bash

mongoimport --db football --collection account account.json --jsonArray
mongoimport --db football --collection ranking RANKING_ita.json --jsonArray
mongoimport --db football --collection ranking RANKING_eng.json --jsonArray
mongoimport --db football --collection ranking RANKING_esp.json --jsonArray
mongoimport --db football --collection ranking RANKING_ger.json --jsonArray
mongoimport --db football --collection league SEASON_ita.json --jsonArray
mongoimport --db football --collection league SEASON_eng.json --jsonArray
mongoimport --db football --collection league SEASON_esp.json --jsonArray
mongoimport --db football --collection league SEASON_ger.json --jsonArray
mongoimport --db football --collection team TEAM_ita.json --jsonArray
mongoimport --db football --collection team TEAM_eng.json --jsonArray
mongoimport --db football --collection team TEAM_esp.json --jsonArray
mongoimport --db football --collection team TEAM_ger.json --jsonArray
