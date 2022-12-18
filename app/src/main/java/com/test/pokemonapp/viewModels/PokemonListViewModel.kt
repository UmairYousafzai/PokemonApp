package com.test.pokemonapp.viewModels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.test.pokemonapp.data.remote.models.PokemonListEntry
import com.test.pokemonapp.repository.PokemonRepository
import com.test.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    var curPage = 0
    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var allPokemon = listOf<PokemonListEntry>()
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun filterPokemon(query: String) {
        if (query.isNotEmpty()) {
            val filterPokemon = allPokemon.filter {
                it.name.trim().lowercase().contains(query.trim().lowercase()) ||
                        it.number.toString()== query.trim()
            }

            pokemonList.value= filterPokemon
        } else {
            pokemonList.value = allPokemon
        }
    }


    fun loadPokemonPaginated() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getPokemonList(curPage * 20, 20)
            when (result) {
                is Resource.Success -> {
                    endReached.value = curPage * 20 >= result.data!!.count
                    val pokemonEntries = result.data.results.mapIndexed { _, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

                        PokemonListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    curPage++
                    isLoading.value = false
                    loadError.value = ""
                    allPokemon = allPokemon + pokemonEntries
                    pokemonList.value += pokemonEntries

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun getDominantColor(
        drawable: Drawable, onFinish: (Color) -> Unit
    ) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->

                onFinish(Color(colorValue))
            }
        }

    }


}