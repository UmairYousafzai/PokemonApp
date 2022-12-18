package com.test.pokemonapp.pokemonDetail

import androidx.lifecycle.ViewModel
import com.test.pokemonapp.data.remote.response.Pokemon
import com.test.pokemonapp.repository.PokemonRepository
import com.test.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    val repository: PokemonRepository
):ViewModel(){

    suspend fun getPokemonDetail(nameString: String):Resource<Pokemon>
    {
        return repository.getPokemon(nameString)
    }
}