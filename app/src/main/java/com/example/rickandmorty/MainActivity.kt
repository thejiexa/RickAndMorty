package com.example.rickandmorty

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var searchButton: ImageButton
    private lateinit var searchTextView: AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        searchButton = findViewById(R.id.search_button)
        searchTextView = findViewById(R.id.search_text_view)

        GetCharacters()


        searchButton.setOnClickListener{
            var isThere: Boolean = false
            Characters.list.forEach{
                if (it.name.equals(searchTextView.text.toString(), ignoreCase = true))
                    isThere = true
            }


            if (searchTextView.text.isNotEmpty() && isThere){
                Characters.searchName = searchTextView.text.toString()
                startActivity(Intent(this, CharacterFragment::class.java))
            }
            else Toast.makeText(this, "Full name please", Toast.LENGTH_LONG).show()

        }

    }


    @SuppressLint("SetTextI18n")
    private fun GetCharacters() {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://rickandmortyapi.com/api/character/").build()

        Thread{
            val json = client.newCall(request).execute()
                .use { response -> JSONObject(response.body!!.string()) }
            if(json.has("error")){
                runOnUiThread() {
                    Toast.makeText(this, json.getString("error"), Toast.LENGTH_LONG).show()
                }
            } else {

                val pages = json.getJSONObject("info").getInt("pages")

                for (i in 0 until pages){
                    GetPages(i)
                }

            }

        }.start()

    }

    private fun GetPages(page: Int){
        val client = OkHttpClient()
        val request = Request.Builder().url("https://rickandmortyapi.com/api/character/?page=$page").build()

        Thread{
            val json = client.newCall(request).execute()
                .use { response -> JSONObject(response.body!!.string()) }
            if(json.has("error")){
                runOnUiThread() {
                    Toast.makeText(this, json.getString("error"), Toast.LENGTH_SHORT).show()
                }
            } else {

                val results = json.getJSONArray("results")

                for (i in 1 until results.length()-1) {

                    val character = results.getJSONObject(i)

                    runOnUiThread() {
                        Characters.list.add(
                            Character(
                                id = character.getInt("id"),
                                image = character.getString("image"),
                                name = character.getString("name").capitalize(),
                                species = character.getString("species").capitalize(),
                                gender = character.getString("gender").capitalize(),
                                status = character.getString("status").capitalize(),
                                origin = character.getJSONObject("origin").getString("name"),
                                location = character.getJSONObject("location").getString("name")
                            )
                        )
                    }

                }

            }

        }.start()

    }

}