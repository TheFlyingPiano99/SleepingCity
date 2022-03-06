package mkk.csb.sleepingcity.model

import android.util.Log
import mkk.csb.sleepingcity.model.inhabitantGroups.RoleGroup
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/*
Contains all the objects related to the core logic of the game.
 */
class Session() {

    private companion object IDGenerator {
        private var maxID : Int = -1
    }
    var id : Int = ++maxID
        get
        set(value) {
            field = value
            if (field > maxID) {
                maxID = field
            }
        }

    var dateOfCreation : Date? = null
    var winner : Role.Faction? = Role.Faction.NULL
    enum class State {
        DAY,
        NIGHT,
        FINISHED
    }
    var state : State? = null
    var daysSpent : Int = 0

    private var inhabitants : MutableList<Inhabitant> = ArrayList()
    private val roleGroups : HashMap<Role, RoleGroup> = HashMap()
    private val currentRoleGroup : RoleGroup? = null

    constructor(id : Int, dateOfCreation : Date?, daysSpent : Int, winner : Role.Faction?, state : State?) : this() {
        this.id = id
        this.dateOfCreation = dateOfCreation
        this.daysSpent = daysSpent
        this.winner = winner
        this.state = state
    }

    override fun toString(): String {

        var str = "Session ID = ${id.toString()} \n\tGroups:\n"
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

    fun addInhabitant(inhabitant : Inhabitant) : Inhabitant {
        inhabitants.add(inhabitant)
        addInhabitantToGroup(inhabitant)
        return inhabitant
    }

    fun addInhabitant(name : String) : Inhabitant {
        for (inhabitant in inhabitants) {
            if (inhabitant.name == name) {
                throw Exception("Inhabitant with this name already exists!")
            }
        }

        val inhabitant = Inhabitant()
        inhabitant.name = name
        inhabitant.role = Role.GRANNY
        inhabitant.state = Inhabitant.State.AWAKE
        inhabitants.add(inhabitant)
        addInhabitantToGroup(inhabitant)
        return inhabitant
    }

    fun beginGame() {
        sortInhabitantsIntoGroups()

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

    private fun addInhabitantToGroup(inhabitant : Inhabitant) {
        if (inhabitant.role != null) {  // What role?
            if (!roleGroups.containsKey(inhabitant.role!!)) {
                roleGroups.put(inhabitant.role!!, RoleGroup(inhabitant.role!!))
            }
            roleGroups[inhabitant.role!!]?.addMember(inhabitant)
        }
    }


    private fun sortInhabitantsIntoGroups() {
        roleGroups.clear()
        for (inhabitant in inhabitants) {
            addInhabitantToGroup(inhabitant)
        }
    }

}