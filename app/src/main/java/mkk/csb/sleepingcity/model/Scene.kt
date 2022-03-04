package mkk.csb.sleepingcity.model

import android.util.Log
import mkk.csb.sleepingcity.sqlite.PersistentDataHelper
import java.lang.Exception

/*
Contains all the objects related to the core logic of the game.
 */
class Scene {
    private var inhabitants : MutableList<Inhabitant> = ArrayList()

    fun load(dataHelper : PersistentDataHelper) {
        inhabitants = dataHelper.restoreTables()
    }

    fun persist(dataHelper : PersistentDataHelper) {
        dataHelper.clearTables()
        dataHelper.persistTables(inhabitants)
    }

    fun doTurn() {
        //Test:
        for (inhabitant in inhabitants) {
            Log.v("Scene",inhabitant.id.toString() + ", " + inhabitant.name)
        }
        if (inhabitants.size == 0) {
            Log.v("Scene","No inhabitants.")
        }
        //inhabitants.clear()
        addInhabitant("Zoli")
        addInhabitant("Simon")
    }

    fun addInhabitant(name : String) {
        for (inhabitant in inhabitants) {
            if (inhabitant.name == name) {
                throw Exception("Inhabitant with this name already exists!")
            }
        }
        val inhabitant = Inhabitant()
        inhabitant.name = name
        inhabitants.add(inhabitant)
    }

}