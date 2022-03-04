package mkk.csb.sleepingcity.model


abstract class Role() {
    protected open val description : String = "Role"
    get

    enum class Attitudes {
        good,
        evil
    }

    protected abstract val attitude : Role.Attitudes

}
