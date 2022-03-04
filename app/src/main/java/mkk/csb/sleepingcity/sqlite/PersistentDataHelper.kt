package mkk.csb.sleepingcity.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import mkk.csb.sleepingcity.model.Inhabitant
import mkk.csb.sleepingcity.model.roles.Role
import mkk.csb.sleepingcity.model.states.CycleState
import java.text.SimpleDateFormat


/*
    Provides data persistence
 */
class PersistentDataHelper(context: Context) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: DbHelper = DbHelper(context)

    //private var timeCreatedFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    //private var timeSpentFormatter = SimpleDateFormat("HH:mm:ss")

    private val tableColumns = arrayOf(
        DbConstants.Inhabitants.Columns.ID.name,
        DbConstants.Inhabitants.Columns.Name.name,
        DbConstants.Inhabitants.Columns.Alive.name,
        DbConstants.Inhabitants.Columns.Role.name,
        DbConstants.Inhabitants.Columns.CycleState.name,
        DbConstants.Inhabitants.Columns.Miscellaneous.name
    )

    @Throws(SQLiteException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun persistTables(inhabitants: List<Inhabitant>) {
        clearTables()
        for (inhabitant in inhabitants) {
            val values = ContentValues()
            values.put(DbConstants.Inhabitants.Columns.ID.name, inhabitant.id)
            values.put(DbConstants.Inhabitants.Columns.Name.name, inhabitant.name)
            values.put(DbConstants.Inhabitants.Columns.Alive.name, inhabitant.alive)
            values.put(DbConstants.Inhabitants.Columns.Role.name, inhabitant.role.toString())
            values.put(DbConstants.Inhabitants.Columns.CycleState.name, inhabitant.cycleState.toString())
            values.put(DbConstants.Inhabitants.Columns.Miscellaneous.name, inhabitant.miscellaneous)
            database!!.insert(DbConstants.Inhabitants.DATABASE_TABLE, null, values)
        }
    }

    fun restoreTables(): MutableList<Inhabitant> {
        val inhabitants: MutableList<Inhabitant> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Inhabitants.DATABASE_TABLE, tableColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val inhabitant: Inhabitant = cursorToInhabitant(cursor)
            inhabitants.add(inhabitant)
            cursor.moveToNext()
        }
        cursor.close()
        return inhabitants
    }

    fun clearTables() {
        database!!.delete(DbConstants.Inhabitants.DATABASE_TABLE, null, null)
    }

    private fun cursorToInhabitant(cursor: Cursor): Inhabitant {
        val id = cursor.getInt(DbConstants.Inhabitants.Columns.ID.ordinal)
        val name = cursor.getString(DbConstants.Inhabitants.Columns.Name.ordinal)
        val alive = (cursor.getInt(DbConstants.Inhabitants.Columns.Alive.ordinal) > 0)
        val role : Role? = when(cursor.getString(DbConstants.Inhabitants.Columns.Role.ordinal)) {
            else -> null
        }
        val cycleState : CycleState? = when(cursor.getString(DbConstants.Inhabitants.Columns.CycleState.ordinal)) {
            else -> null
        }
        val miscellaneous = cursor.getString(DbConstants.Inhabitants.Columns.Miscellaneous.ordinal)
        return Inhabitant(id, name, alive, role, cycleState, miscellaneous)
    }

}
