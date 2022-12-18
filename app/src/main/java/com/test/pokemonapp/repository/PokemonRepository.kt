package com.test.pokemonapp.repository

import com.test.pokemonapp.data.remote.Api
import com.test.pokemonapp.data.remote.response.Pokemon
import com.test.pokemonapp.data.remote.response.PokemonList
import com.test.pokemonapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor (
   private val api:Api
    ){

    suspend fun getPokemonList(
        offset:Int,
        limit:Int
    ):Resource<PokemonList>
    {
        val response = try {
            api.getPokemonList(offset,limit)
        }
        catch (e:Exception)
        {
            return Resource.Error(message = e.message.toString())
        }

        return Resource.Success(response)
    }

    suspend fun getPokemon(
        name:String
    ):Resource<Pokemon>
    {
        val response = try {
            api.getPokemon(name)
        }
        catch (e:Exception)
        {
            return Resource.Error(message = e.message.toString())
        }

        return Resource.Success(response)
    }
}