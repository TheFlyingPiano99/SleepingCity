package mkk.csb.sleepingcity.model

import mkk.csb.sleepingcity.sqlite.PersistentDataHelper

class SessionManager {

    private var sessions : MutableList<Session> = ArrayList()
    private var inhabitants : MutableList<Inhabitant> = ArrayList()

    var activeSession : Session? = null
        set(value) {
        if (sessions.contains(value))
            field = value
    }


    fun load(dataHelper : PersistentDataHelper) {
        val tables = dataHelper.restoreTables()
        sessions = tables.sessions
    }

    fun persist(dataHelper : PersistentDataHelper) {
        dataHelper.clearTables()
        dataHelper.persistTables(inhabitants, sessions)
    }

    fun createSession() {
        val session = Session()
        val inhab1 = Inhabitant()
        val inhab2 = Inhabitant()
        inhab1.name = "Zoli"
        inhab1.role = Role.KILLER
        inhab2.name = "Simon"
        inhab2.role = Role.KILLER

        inhabitants.add(inhab1)
        inhabitants.add(inhab2)
        session.addInhabitant(inhab1)
        session.addInhabitant(inhab2)
        inhab1.session = session
        inhab2.session = session
        sessions.add(session)
        session.beginGame()
    }

    override fun toString(): String {
        return "SessionManager(sessions=$sessions, inhabitants=$inhabitants)"
    }

}