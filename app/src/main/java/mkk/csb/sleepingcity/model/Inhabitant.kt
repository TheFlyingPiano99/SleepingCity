package mkk.csb.sleepingcity.model

import mkk.csb.sleepingcity.model.roles.Role
import mkk.csb.sleepingcity.model.inhabitantStates.*

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
    var state : InhabitantState? = null
    var miscellaneous : String
        set(value) {
            role?.setDataFromMiscellaneous(value)
        }
        get() {
            return role?.getMiscellaneous() ?: ""
        }

    constructor(id : Int, name : String, alive : Boolean, role : Role?, state: InhabitantState?, miscellaneous : String) : this() {
        this.id = id
        this.name = name
        this.alive = alive
        this.role = role
        this.state = state
        this.miscellaneous = miscellaneous
    }

    override fun toString(): String {
        return "id: " + id.toString() + ", name: " + name + ", alive: " + alive.toString() + ", role: " + role.toString() + ", state: " + state.toString() + ", miscellaneous: " + miscellaneous
    }

    fun beginGame() {
        alive = true
        transitionToStates(AwakeState())
        role?.onBeginGame()
    }

    fun goToSleep() {
        if (alive) {
            transitionToStates(SleepingState())
            role?.onGoToSleep()
        }
    }

    fun wakeUp() {
        if (alive) {
            transitionToStates(AwakeState())
            role?.onWakeUp()
        }
    }

    fun beginPerformingNighttimeAction() {
        if (alive) {
            transitionToStates(PerformingNighttimeActionState())
            role?.onBeginPerformingNighttimeAction()
        }
    }

    fun finishPerformingNighttimeAction() {
        if (alive) {
            transitionToStates(SleepingState())
            role?.onFinishPerformingNighttimeAction()
        }
    }

    fun getKilled() {
        if (alive) {
            transitionToStates(DeadState())
            role?.onGetKilled()
            alive = false
        }
    }

    private fun transitionToStates(newState : InhabitantState) {
        state?.onExit()
        newState.onEnter()
        state = newState;
    }
}