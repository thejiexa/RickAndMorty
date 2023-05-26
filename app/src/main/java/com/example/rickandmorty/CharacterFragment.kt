package com.example.rickandmorty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load


class CharacterFragment: AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var speciesTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var originTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var searchName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_character )

        imageView = findViewById(R.id.imageView)
        nameTextView = findViewById(R.id.name)
        speciesTextView = findViewById(R.id.species)
        genderTextView = findViewById(R.id.gender)
        statusTextView = findViewById(R.id.status)
        originTextView = findViewById(R.id.origin)
        locationTextView = findViewById(R.id.location)

        searchName = com.example.rickandmorty.Characters.searchName

        val character = Characters.list.find {
            it.name.contentEquals(searchName, ignoreCase = true)

        }

        imageView.load(character?.image)
        nameTextView.setText(character?.name)
        speciesTextView.setText(character?.species)
        genderTextView.setText(character?.gender)
        statusTextView.setText(character?.status)
        originTextView.setText(character?.origin)
        locationTextView.setText(character?.location)

    }

}