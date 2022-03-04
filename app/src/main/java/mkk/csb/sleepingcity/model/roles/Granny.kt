package mkk.csb.sleepingcity.model.roles

class Granny() : Role() {
    override val attitude : Attitudes = Attitudes.good
    override val description : String = "The lovely old granny."

    override fun toString(): String {
        return "Granny"
    }
}
