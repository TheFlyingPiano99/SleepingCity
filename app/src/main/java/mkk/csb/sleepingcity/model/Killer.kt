package mkk.csb.sleepingcity.model

class Killer() : Role() {
    override val attitude: Attitudes = Role.Attitudes.evil
    override val description : String = "It's a killer!"
}