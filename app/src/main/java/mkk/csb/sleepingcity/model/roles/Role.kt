package mkk.csb.sleepingcity.model.roles


abstract class Role() {
    open val description: String = "Role"   // Provides details for the player about the specific role.

    enum class Attitudes {
        good,
        evil
    }

    protected abstract val attitude: Attitudes

    open fun setDataFromMiscellaneous(miscellaneous: String) {}

    open fun getMiscellaneous() : String {return ""}

    abstract override fun toString(): String
    override fun hashCode(): Int { return toString().hashCode() }

    open fun onBeginGame() {}
    open fun onGoToSleep() {}
    open fun onWakeUp() {}
    open fun onBeginPerformingNighttimeAction() {}
    open fun onFinishPerformingNighttimeAction() {}
    open fun onGetKilled() {}

}

