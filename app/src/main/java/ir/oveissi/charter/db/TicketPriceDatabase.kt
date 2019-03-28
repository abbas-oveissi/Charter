package ir.oveissi.charter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TicketPrice::class], version = 2)
@TypeConverters(TiviTypeConverters::class)
abstract class TicketPriceDatabase : RoomDatabase() {

    abstract fun ticketPriceDao(): TicketPriceDao

    companion object {
        private var INSTANCE: TicketPriceDatabase? = null

        fun getInstance(context: Context): TicketPriceDatabase? {
            if (INSTANCE == null) {
                synchronized(TicketPriceDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TicketPriceDatabase::class.java, "ticketprices.db"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}