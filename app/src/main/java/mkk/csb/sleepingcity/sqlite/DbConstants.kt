package mkk.csb.sleepingcity.sqlite

import android.database.sqlite.SQLiteDatabase
import android.util.Log

/*
    Works with constant SQlite parameters
 */
object DbConstants {

    const val DATABASE_NAME = "sleeping_city.db"
    const val DATABASE_VERSION = 7

    object Inhabitants {
        const val DATABASE_TABLE = "inhabitants"

        enum class Columns {
            ID,
            SessionID,
            Name,
            Alive,      //0 = false, 1 = true
            Role,
            State,
            Miscellaneous
        }

        private val DATABASE_CREATE = """create table if not exists $DATABASE_TABLE (
            ${Columns.ID.name} integer primary key,
            ${Columns.SessionID.name} integer, 
            ${Columns.Name.name} text not null,
            ${Columns.Alive.name} integer not null,
            ${Columns.Role.name} text not null,
            ${Columns.State.name} text not null,
            ${Columns.Miscellaneous.name} text
            );"""
    //TODO("Foreign key")
    //  foreign key (${Columns.SessionID.name}) references ${Sessions.DATABASE_TABLE}(Sessions.ID.name)

        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Inhabitants::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }

    object Sessions {
        const val DATABASE_TABLE = "sessions"
        enum class Columns {
            ID,
            DateOfCreation,
            DaysSpent,
            Winner,
            State
        }

        private val DATABASE_CREATE = """create table if not exists $DATABASE_TABLE (
            ${Columns.ID.name} integer primary key,
            ${Columns.DateOfCreation.name} text not null,
            ${Columns.DaysSpent.name} integer not null,
            ${Columns.Winner.name} text not null,
            ${Columns.State.name} text not null
            );"""

        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Sessions::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }
}
