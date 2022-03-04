package mkk.csb.sleepingcity.model.inhabitantStates

abstract class InhabitantState {

    abstract override fun toString(): String

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        return this::class == other!!::class
    }

    open fun onEnter() {
    }

    open fun onExit() {
    }
}