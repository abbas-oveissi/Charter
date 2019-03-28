package ir.oveissi.charter.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.oveissi.charter.template.TP
import ir.oveissi.charter.utils.PathEnum
import java.net.HttpURLConnection
import java.net.URL


object Api {

    fun getMinTp(pathEnum: PathEnum): TP? {
        val htmlData = getHtmlDataFromWebsite() ?: return null
        val turnsType = object : TypeToken<List<TP>>() {}.type
        val tps = Gson().fromJson<List<TP>>(htmlData, turnsType)
        return tps.minBy {
            try {
                it.price.toLong()
            } catch (ex:java.lang.Exception){
                Long.MAX_VALUE
            }
        }
    }

    private fun getHtmlDataFromWebsite(): String? {
        var text = ""
        var connection: HttpURLConnection? = null
        try {
            connection =
                    URL("https://charter.liara.run/tickets").openConnection() as HttpURLConnection
            connection.connect()
            text = connection.inputStream.use {
                it.reader().use { reader ->
                    reader.readText()
                }
            }
        } catch (e: Exception) {
            // some other Exception occurred
            e.printStackTrace()
            connection?.disconnect()
            return null
        }
        connection.disconnect()
        return text
    }
}