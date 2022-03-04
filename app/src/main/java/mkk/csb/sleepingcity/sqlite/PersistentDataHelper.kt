package hu.bme.mobweb.lab.sudoku.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import mkk.csb.sleepingcity.model.Inhabitant
import java.text.SimpleDateFormat


/*
    Provides data persistence
 */
class PersistentDataHelper(context: Context) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: DbHelper = DbHelper(context)

    private var timeCreatedFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private var timeSpentFormatter = SimpleDateFormat("HH:mm:ss")

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
            values.put(DbConstants.Inhabitants.Columns.ID.name, inhabitant.ID)
            values.put(DbConstants.Inhabitants.Columns.Name.name, timeCreatedFormatter.format(inhabitant.timeCreated))
            values.put(DbConstants.Inhabitants.Columns.Alive.name, timeSpentFormatter.format(inhabitant.timeSpentSolving))
            values.put(DbConstants.Inhabitants.Columns.Role.name, inhabitant.toString())
            values.put(DbConstants.Inhabitants.Columns.CycleState.name, inhabitant.toString())
            values.put(DbConstants.Inhabitants.Columns.Miscellaneous.name, inhabitant.toString())
            database!!.insert(DbConstants.Inhabitants.DATABASE_TABLE, null, values)
        }
    }

    fun restoreTables(): MutableList<Puzzle> {
        val puzzles: MutableList<Puzzle> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Inhabitants.DATABASE_TABLE, tableColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val puzzle: Puzzle = cursorToPuzzle(cursor)
            puzzles.add(puzzle)
            cursor.moveToNext()
        }
        cursor.close()
        return puzzles
    }

    fun clearTables() {
        database!!.delete(DbConstants.Inhabitants.DATABASE_TABLE, null, null)
    }

    private fun cursorToPuzzle(cursor: Cursor): Puzzle {
        val puzzleID = cursor.getInt(DbConstants.Inhabitants.Columns.ID.ordinal)
        val timeCreated = timeCreatedFormatter.parse(cursor.getString(DbConstants.Inhabitants.Columns.timeCreated.ordinal))
        val timeSpentSolving = timeSpentFormatter.parse(cursor.getString(DbConstants.Inhabitants.Columns.timeSpentSolving.ordinal))
        val gridString = cursor.getString(DbConstants.Inhabitants.Columns.gridString.ordinal)
        return Puzzle(puzzleID, timeCreated!!, timeSpentSolving!!, gridString)
    }

}
