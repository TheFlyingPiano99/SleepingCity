package mkk.csb.sleepingcity.model.roles

class Killer() : Role() {
    override val attitude: Attitudes = Attitudes.evil
    override val description : String = "It's a killer!"

    override fun setDataFromMiscellaneous(miscellaneous: String) {
        TODO("Not yet implemented")
    }

    override fun getMiscellaneous(): String {
        TODO("Not yet implemented")
        return ""
    }
}