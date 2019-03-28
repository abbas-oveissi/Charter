package ir.oveissi.charter.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface TicketPriceDao {

    @Query("SELECT * from ticket_price_table ORDER BY id DESC ")
    fun getAll(): DataSource.Factory<Int, TicketPrice>


    @Insert(onConflict = REPLACE)
    fun insert(weatherData: TicketPrice)

    @Query("DELETE from ticket_price_table")
    fun deleteAll()

    @Query("SELECT * FROM ticket_price_table ORDER BY id DESC LIMIT 1")
    fun lastTicketPrice(): LiveData<TicketPrice>
}