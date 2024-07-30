package com.example.rickandmortycompose.domain.model.navigation

enum class Routes(val route:String) {
    Location("location"),
    Character("character"),
    Episode("episode"),
    CharacterDetails("characterDetails/{characterId}")
}