package mkk.csb.sleepingcity.model

import mkk.csb.sleepingcity.model.roles.Role
import mkk.csb.sleepingcity.model.states.CycleState

class Inhabitant() {
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
    lateinit var name : String
    var alive : Boolean = true
    var role : Role? = null
    var cycleState : CycleState? = null
    var miscellaneous : String
        set(value) {
            role?.setDataFromMiscellaneous(value)
        }
        get() {
            return role?.getMiscellaneous() ?: ""
        }

    constructor(id : Int, name : String, alive : Boolean, role : Role?, cycleState: CycleState?, miscellaneous : String) : this() {
        this.id = id
        this.name = name
        this.alive = alive
        this.role = role
        this.cycleState = cycleState
        this.miscellaneous = miscellaneous
    }

}