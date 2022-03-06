package mkk.csb.sleepingcity.model

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
    var session : Session? = null
    lateinit var name : String
    var alive : Boolean = true
    var role : Role? = null
    enum class State {
        AWAKE,
        SLEEPING,
        PERFORMING_NIGHTTIME_ACTION,
        DEAD;

        open fun onEnter() {
        }

        open fun onExit() {
        }
    }
    var state : State? = null
    var miscellaneous : String
        set(value) {
            role?.setDataFromMiscellaneous(value)
        }
        get() {
            return role?.getMiscellaneous() ?: ""
        }

    constructor(id : Int, session: Session?, name : String, alive : Boolean, role : Role?, state: State?, miscellaneous : String) : this() {
        this.id = id
        this.session = session
        this.name = name
        this.alive = alive
        this.role = role
        this.state = state
        this.miscellaneous = miscellaneous
    }

    override fun toString(): String {
        return "id: " + id.toString() + ", sessionID: "+ session?.id + ", name: " + name + ", alive: " + alive.toString() + ", role: " + role.toString() + ", state: " + state.toString() + ", miscellaneous: " + miscellaneous
    }

    fun beginGame() {
        alive = true
        transitionToStates(State.AWAKE)
        role?.onBeginGame()
    }

    fun goToSleep() {
        if (alive) {
            transitionToStates(State.SLEEPING)
            role?.onGoToSleep()
        }
    }

    fun wakeUp() {
        if (alive) {
            transitionToStates(State.AWAKE)
            role?.onWakeUp()
        }
    }

    fun beginPerformingNighttimeAction() {
        if (alive) {
            transitionToStates(State.PERFORMING_NIGHTTIME_ACTION)
            role?.onBeginPerformingNighttimeAction()
        }
    }

    fun finishPerformingNighttimeAction() {
        if (alive) {
            transitionToStates(State.SLEEPING)
            role?.onFinishPerformingNighttimeAction()
        }
    }

    fun getKilled() {
        if (alive) {
            transitionToStates(State.DEAD)
            role?.onGetKilled()
            alive = false
        }
    }

    private fun transitionToStates(newState : State) {
        state?.onExit()
        newState.onEnter()
        state = newState;
    }
}