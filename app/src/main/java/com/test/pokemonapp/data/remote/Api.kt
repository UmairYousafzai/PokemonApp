package com.test.pokemonapp.data.remote

import com.test.pokemonapp.data.remote.response.Pokemon
import com.test.pokemonapp.data.remote.response.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offSet:Int,
        @Query("limit") limit:Int
    ):PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name:String
    ):Pokemon
}