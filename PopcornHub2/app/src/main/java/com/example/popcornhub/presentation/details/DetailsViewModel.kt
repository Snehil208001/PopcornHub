package com.example.popcornhub.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popcornhub.domain.usecase.GetContentDetailsUseCase
import com.example.popcornhub.presentation.details.components.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getContentDetailsUseCase: GetContentDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> = _state.asStateFlow()

    fun loadContent(contentId: String, isMovie: Boolean) {
        _state.value = DetailState.Loading

        viewModelScope.launch {
            try {
                val content = getContentDetailsUseCase(contentId, isMovie)
                _state.value = DetailState.Success(content)
            } catch (e: Exception) {
                _state.value = DetailState.Error("Failed to load content")
            }
        }
    }
}
