package com.company.uit.screens.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MviViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(HomeUIState())
	val uiState = _uiState.asStateFlow()

	private val _uiSideEffect = MutableSharedFlow<MviUiSideEffect>()
	val uiSideEffect = _uiSideEffect.asSharedFlow()

	fun onAction(action: HomeUIAction) = when (action) {
		is HomeUIAction.TextFieldChanged -> _uiState.update { it.copy(s = action.s) }
		is HomeUIAction.AddItem -> addItem()
		HomeUIAction.NavigateBack -> navigateBack()
	}

	private fun navigateBack() {
		viewModelScope.launch {
			_uiSideEffect.emit(MviUiSideEffect.NavigateBack)
		}
	}

	private fun addItem() {
		var snackMsg: String? = null

		_uiState.update { state ->
			if (state.s.isNotBlank()) {
				snackMsg = "Adding item: ${state.s}"
				state.copy(
					s = "",
					items = state.items + state.s
				)
			} else {
				snackMsg = "You can't add empty item"
				state
			}
		}

		snackMsg?.let { snack(it) }
	}

	private fun snack(msg: String) {
		viewModelScope.launch {
			_uiSideEffect.emit(MviUiSideEffect.ShowSnackBar(msg))
		}
	}
}