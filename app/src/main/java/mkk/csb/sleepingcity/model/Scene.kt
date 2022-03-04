package mkk.csb.sleepingcity.model

import android.util.Log
import mkk.csb.sleepingcity.model.inhabitantGroups.AliveGroup
import mkk.csb.sleepingcity.model.inhabitantGroups.RoleGroup
import mkk.csb.sleepingcity.model.roles.Granny
import mkk.csb.sleepingcity.model.inhabitantStates.AwakeState
import mkk.csb.sleepingcity.model.roles.Killer
import mkk.csb.sleepingcity.model.roles.Role
import mkk.csb.sleepingcity.sqlite.PersistentDataHelper

/*
Contains all the objects related to the core logic of the game.
 */
class Scene {
    private var inhabitants : MutableList<Inhabitant> = ArrayList()

    private val roleGroups : HashMap<Role, RoleGroup> = HashMap()
    private val currentRoleGroup : RoleGroup? = null

    val RoleOrder : Array<Role> = arrayOf(Granny(), Killer())

    fun load(dataHelper : PersistentDataHelper) {
        inhabitants = dataHelper.restoreTables()
    }

    fun persist(dataHelper : PersistentDataHelper) {
        dataHelper.clearTables()
        dataHelper.persistTables(inhabitants)
    }

    override fun toString(): String {
        var str = "Scene\n\tGroups:\n"
        for (group in roleGroups) {
            str += "\t\t" + group.value.role.toString() + " group:\n"
            for (member in group.value.iterator()) {
                str += "\t\t\t" + member.toString() +"\n"
            }
        }
        return str
    }

    /*
    Only for testing!
     */
    fun doTurn() {
        //Test:
        for (inhabitant in inhabitants) {
            Log.v("Scene","id: " + inhabitant.id.toString() + ", " + inhabitant.name + ", " + inhabitant.role.toString() + ", " + inhabitant.state.toString())
        }
        if (inhabitants.size == 0) {
            Log.v("Scene","No inhabitants.")
        }
        inhabitants.clear()
        addInhabitant("Zoli")
        addInhabitant("Simon")
    }

    fun addInhabitant(name : String) : Inhabitant {
        /*
        for (inhabitant in inhabitants) {
            if (inhabitant.name == name) {
                throw Exception("Inhabitant with this name already exists!")
            }
        }
        */
        val inhabitant = Inhabitant()
        inhabitant.name = name
        inhabitant.role = Granny()
        inhabitant.state = AwakeState()
        inhabitants.add(inhabitant)
        return inhabitant
    }

    fun beginGame() {
        sortInhabitantsToGroups()


        for (inhabitant in inhabitants) {
            inhabitant.beginGame()
        }
    }

    fun wakeUp() {
        for (inhabitant in inhabitants) {
            inhabitant.wakeUp()
        }
    }

    fun goToSleep() {
        for (inhabitant in inhabitants) {
            inhabitant.goToSleep()
        }
    }

    fun checkVictoryConditions() {
        //TODO("Needs to be implemented!")
    }

    fun deleteInhabitants() {
        inhabitants.clear()
    }

    private fun sortInhabitantsToGroups() {
        roleGroups.clear()
        for (inhabitant in inhabitants) {
            if (inhabitant.role != null) {  // What role?
                if (!roleGroups.containsKey(inhabitant.role!!)) {
                    roleGroups.put(inhabitant.role!!, RoleGroup(inhabitant.role!!))
                }
                roleGroups[inhabitant.role!!]?.addMember(inhabitant)
            }
        }
        Log.v("Scene", "Are hashes equal? -> " + (inhabitants[0].role!!.hashCode() == inhabitants[1].role!!.hashCode()).toString())
    }

}