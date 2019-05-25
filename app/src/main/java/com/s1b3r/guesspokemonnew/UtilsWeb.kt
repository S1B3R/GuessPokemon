package com.s1b3r.guesspokemonnew

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.s1b3r.guesspokemonnew.model.Pokemon
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val baseAdress = "https://pokeapi.co/api/v2/pokemon/"

object UtilsWeb{

    fun getPkm(number: Int, sucesso:(Pokemon)->Unit){
        DownloadPkm(sucesso).execute(number)
    }

    private class DownloadPkm(val sucesso:(Pokemon)->Unit): AsyncTask<Int, Int, Boolean>() {

        override fun doInBackground(vararg p0: Int?): Boolean {
            return try {
                sucesso(createPokemon(p0[0] ?: 1))
                 true
            }catch (e:Exception){
                e.message
                false
            }
        }

        private fun createPokemon(num: Int):Pokemon{
            val pkmJson = downloadPkm(num)
            return Pokemon(
                name = pkmJson.getString("name"),
                image = downloadImg(pkmJson.getJSONObject("sprites").getString("front_default")),
                num = num
            )
        }

        private fun downloadPkm(number: Int):JSONObject{

            val urlConnection =
                URL(baseAdress + number).openConnection() as HttpsURLConnection

            urlConnection.setRequestProperty("User-Agent","GuessPokemon")

            val streamReader =
                InputStreamReader(urlConnection.inputStream,"UTF-8")

            val jsonObject = JSONObject(streamReader.readText())

            urlConnection.disconnect()
            streamReader.close()

            return jsonObject
        }

        private fun downloadImg(rawUrl: String):Bitmap{

            val urlConnection = URL(rawUrl).openConnection() as HttpsURLConnection
            urlConnection.setRequestProperty("User-Agent","GuessPokemon")

            val bitMap = BitmapFactory.decodeStream(urlConnection.inputStream)

            urlConnection.disconnect()

            return bitMap

        }

    }
}
//            val jsonReader = JsonReader(streamReader)
//            var name = ""
//            var imageUrl = ""
//            jsonReader.beginObject()
//            while (jsonReader.hasNext()){
//                val key = jsonReader.nextName()
//                when(key){
//                    "name" -> name = jsonReader.nextString()
//                    "front_default","sprites" -> imageUrl = jsonReader.nextString()
//                    else -> jsonReader.skipValue()
//                }
//            }
//            jsonReader.close()
