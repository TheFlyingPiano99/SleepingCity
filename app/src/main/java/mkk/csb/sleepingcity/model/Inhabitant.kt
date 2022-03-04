package mkk.csb.sleepingcity.model

class Inhabitant() {
    private companion object IDGenerator {
        private var maxID : Int = -1
    }

    private lateinit var role : Role
    get
    set

    private var ID : Int = ++maxID
        get
        set(value) {
            field = value
            if (field > maxID) {
                maxID = field
            }
        }


}