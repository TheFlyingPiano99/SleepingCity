package mkk.csb.sleepingcity.model.roles


abstract class Role() {
    open val description: String = "Role"

    enum class Attitudes {
        good,
        evil
    }

    protected abstract val attitude: Attitudes

    abstract fun setDataFromMiscellaneous(miscellaneous: String)

    abstract fun getMiscellaneous() : String
}

