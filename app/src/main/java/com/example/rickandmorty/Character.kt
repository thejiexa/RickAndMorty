package com.example.rickandmorty

import androidx.lifecycle.ViewModel

data class Character(var id: Int, var image: String, var name: String, var species: String, var gender: String, var status: String, var origin: String, var location: String)

class Characters : ViewModel(){
    companion object {
        var list = mutableListOf<com.example.rickandmorty.Character>()
        var searchName = ""
    }
}