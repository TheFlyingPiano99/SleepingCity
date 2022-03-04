package mkk.csb.sleepingcity.model.roles

class Killer() : Role() {
    override val attitude: Attitudes = Attitudes.evil
    override val description : String = "A dangerous killer!"

    override fun toString(): String {
        return "Killer"
    }
}