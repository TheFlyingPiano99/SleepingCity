package mkk.csb.sleepingcity.model.inhabitantStates

class InhabitantStateBuilder {

    fun buildFromName(state : String) : InhabitantState? {
        return when(state) {
            "AwakeState" -> AwakeState()
            "DeadState" -> DeadState()
            "PerformingNighttimeActionState" -> PerformingNighttimeActionState()
            "SleepingState" -> SleepingState()
            else -> null
        }
    }
}