package mkk.csb.sleepingcity.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import mkk.csb.sleepingcity.model.Inhabitant
import mkk.csb.sleepingcity.model.Role
import mkk.csb.sleepingcity.model.Session
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/*
    Provides data persistence
 */
class PersistentDataHelper(context: Context) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: DbHelper = DbHelper(context)

    private var timeCreatedFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private var timeSpentFormatter = SimpleDateFormat("HH:mm:ss")


    private val inhabitantsTableColumns = arrayOf(
        DbConstants.Inhabitants.Columns.ID.name,
        DbConstants.Inhabitants.Columns.SessionID.name,
        DbConstants.Inhabitants.Columns.Name.name,
        DbConstants.Inhabitants.Columns.Alive.name,
        DbConstants.Inhabitants.Columns.Role.name,
        DbConstants.Inhabitants.Columns.State.name,
        DbConstants.Inhabitants.Columns.Miscellaneous.name
    )

    private val sessionsTableColumns = arrayOf(
        DbConstants.Sessions.Columns.ID.name,
        DbConstants.Sessions.Columns.DateOfCreation.name,
        DbConstants.Sessions.Columns.DaysSpent.name,
        DbConstants.Sessions.Columns.Winner.name,
        DbConstants.Sessions.Columns.State.name,
    )

    @Throws(SQLiteException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun persistTables(inhabitants: List<Inhabitant>, sessions : List<Session>) {
        clearTables()

        for (session in sessions) {
            val values = ContentValues()
            values.put(DbConstants.Sessions.Columns.ID.name, session.id)
            values.put(DbConstants.Sessions.Columns.DateOfCreation.name, timeCreatedFormatter.format(session.dateOfCreation ?: Date(0)))
            values.put(DbConstants.Sessions.Columns.DaysSpent.name, session.daysSpent)
            values.put(DbConstants.Sessions.Columns.Winner.name, session.winner?.name ?: "")
            values.put(DbConstants.Sessions.Columns.State.name, session.state?.name ?: "")
            database!!.insert(DbConstants.Sessions.DATABASE_TABLE, null, values)
        }

        for (inhabitant in inhabitants) {
            val values = ContentValues()
            values.put(DbConstants.Inhabitants.Columns.ID.name, inhabitant.id)
            values.put(DbConstants.Inhabitants.Columns.SessionID.name, inhabitant.session?.id ?: -1)
            values.put(DbConstants.Inhabitants.Columns.Name.name, inhabitant.name)
            values.put(DbConstants.Inhabitants.Columns.Alive.name, inhabitant.alive)
            values.put(DbConstants.Inhabitants.Columns.Role.name, inhabitant.role?.name ?: "")
            values.put(DbConstants.Inhabitants.Columns.State.name, inhabitant.state?.name ?: "")
            values.put(DbConstants.Inhabitants.Columns.Miscellaneous.name, inhabitant.miscellaneous)
            database!!.insert(DbConstants.Inhabitants.DATABASE_TABLE, null, values)
        }
    }

    class Tables {
        val inhabitants : MutableList<Inhabitant> = ArrayList()
        val sessions : MutableList<Session> = ArrayList()
    }
    fun restoreTables(): Tables {
        val tables = Tables()

        //Sessions:
        val sessionCursor: Cursor =
            database!!.query(DbConstants.Sessions.DATABASE_TABLE, sessionsTableColumns, null, null, null, null, null)
        sessionCursor.moveToFirst()
        while (!sessionCursor.isAfterLast) {
            tables.sessions.add(cursorToSession(sessionCursor))
            sessionCursor.moveToNext()
        }
        sessionCursor.close()

        //Inhabitants:
        val inhabitantCursor : Cursor =
            database!!.query(DbConstants.Inhabitants.DATABASE_TABLE, inhabitantsTableColumns, null, null, null, null, null)
        inhabitantCursor.moveToFirst()
        while (!inhabitantCursor.isAfterLast) {
            tables.inhabitants.add(cursorToInhabitant(inhabitantCursor, tables.sessions))
            inhabitantCursor.moveToNext()
        }
        inhabitantCursor.close()

        return tables
    }

    fun clearTables() {
        database!!.delete(DbConstants.Inhabitants.DATABASE_TABLE, null, null)
        database!!.delete(DbConstants.Sessions.DATABASE_TABLE, null, null)
    }

    private fun cursorToInhabitant(cursor: Cursor, sessions : List<Session>): Inhabitant {
        val id = cursor.getInt(DbConstants.Inhabitants.Columns.ID.ordinal)
        val sessionId = cursor.getInt(DbConstants.Inhabitants.Columns.SessionID.ordinal)
        val session = sessions.find {
             it.id == sessionId
        }
        val name = cursor.getString(DbConstants.Inhabitants.Columns.Name.ordinal)
        val alive = (cursor.getInt(DbConstants.Inhabitants.Columns.Alive.ordinal) > 0)
        val role = Role.valueOf(cursor.getString(DbConstants.Inhabitants.Columns.Role.ordinal))
        val state = Inhabitant.State.valueOf(cursor.getString(DbConstants.Inhabitants.Columns.State.ordinal))
        val miscellaneous = cursor.getString(DbConstants.Inhabitants.Columns.Miscellaneous.ordinal)
        val inhabitant = Inhabitant(id, session, name, alive, role, state, miscellaneous)
        session?.addInhabitant(inhabitant)
        return inhabitant
    }

    private fun cursorToSession(cursor: Cursor): Session {
        val id = cursor.getInt(DbConstants.Sessions.Columns.ID.ordinal)
        val dateOfCreation = timeCreatedFormatter.parse(cursor.getString(DbConstants.Sessions.Columns.DateOfCreation.ordinal))
        val daysSpent = (cursor.getInt(DbConstants.Sessions.Columns.DaysSpent.ordinal))
        val winner : Role.Faction? = try {
            Role.Faction.valueOf(cursor.getString(DbConstants.Sessions.Columns.Winner.ordinal))
        }
        catch (e : IllegalArgumentException)  {
            null
        }
        val state = try {Session.State.valueOf(cursor.getString(DbConstants.Sessions.Columns.State.ordinal))}
        catch (e : IllegalArgumentException) {
            null
        }
        return Session(id, dateOfCreation, daysSpent, winner, state)
    }

}
