package mkk.csb.sleepingcity.model.roles

abstract class Role() {
    open val description: String = "Role"   // Provides details for the player about the specific role.
    open val className : String = "Role"

    enum class Attitudes {
        good,
        evil
    }

    protected abstract val attitude: Attitudes

    open fun setDataFromMiscellaneous(miscellaneous: String) {}

    open fun getMiscellaneous() : String {return ""}

    abstract override fun toString(): String

    open fun onBeginGame() {}
    open fun onGoToSleep() {}
    open fun onWakeUp() {}
    open fun onBeginPerformingNighttimeAction() {}
    open fun onFinishPerformingNighttimeAction() {}
    open fun onGetKilled() {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (description != other.description) return false
        if (className != other.className) return false
        if (attitude != other.attitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + className.hashCode()
        result = 31 * result + attitude.hashCode()
        return result
    }

}

