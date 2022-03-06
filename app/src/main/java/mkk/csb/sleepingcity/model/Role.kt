package mkk.csb.sleepingcity.model


enum class Role {
    GRANNY {
        override val faction = Faction.INHABITANTS
    },
    KILLER {
        override val faction = Faction.KILLERS
    };

    enum class Faction {
        KILLERS,
        INHABITANTS,
        NEUTRAL,
        NULL
    }

    protected abstract val faction: Faction

    open fun setDataFromMiscellaneous(miscellaneous: String) {}

    open fun getMiscellaneous() : String {return ""}
    open fun onBeginGame() {}
    open fun onGoToSleep() {}
    open fun onWakeUp() {}
    open fun onBeginPerformingNighttimeAction() {}
    open fun onFinishPerformingNighttimeAction() {}
    open fun onGetKilled() {}
}

