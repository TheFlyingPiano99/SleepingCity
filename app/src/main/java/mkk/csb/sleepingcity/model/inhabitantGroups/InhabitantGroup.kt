package mkk.csb.sleepingcity.model.inhabitantGroups

import mkk.csb.sleepingcity.model.Inhabitant

abstract class InhabitantGroup {
    private val members : ArrayList<Inhabitant> = ArrayList()

    fun addMember(inhabitant : Inhabitant) {
        members.add(inhabitant)
    }

    fun removeMember(inhabitant : Inhabitant) {
        members.remove(inhabitant)
    }

    fun clear() {
        members.clear()
    }

    fun iterator() : MutableIterator<Inhabitant> {
        return members.iterator()
    }
}