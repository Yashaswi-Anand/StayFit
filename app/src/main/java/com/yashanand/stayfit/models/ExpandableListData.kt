package com.yashanand.stayfit.models

internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()
            val myFavCricketPlayers: MutableList<String> = ArrayList()
            myFavCricketPlayers.add("As a beginner, you goal should be to adapt your body to your workout, and make workout a daily habit. The exercise intensity increases step by step, you'll find it's easy to pick up. ")
            myFavCricketPlayers.add("MS")
            myFavCricketPlayers.add("Dhoni")
            myFavCricketPlayers.add("MS.Dhoni")
            myFavCricketPlayers.add("yash")
       /*     val myFavFootballPlayers: MutableList<String> = ArrayList()
            myFavFootballPlayers.add("Cristiano Ronaldo")
            val myFavTennisPlayers: MutableList<String> = ArrayList()
            myFavTennisPlayers.add("Roger Federer")*/
            //var list: ArrayList<String> = ArrayList()

            expandableListDetail["I'm a beginner. Where to Start?"] = listOf(myFavCricketPlayers[0])
            expandableListDetail["2 question"] =listOf(myFavCricketPlayers[1])
           // expandableListDetail["TENNIS PLAYERS"] = listOf(myFavCricketPlayers[2])
             return expandableListDetail
        }
}