package mkk.csb.sleepingcity.model.roles

class RoleBuilder {

    fun buildFromName(role : String) : Role? {
        return when(role) {
            "Killer" -> Killer()
            "Granny" -> Granny()
            else -> null
        }
    }

}