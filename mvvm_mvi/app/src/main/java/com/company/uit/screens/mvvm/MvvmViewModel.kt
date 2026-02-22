package com.company.uit.screens.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.uit.screens.mvi.MviUiSideEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MvvmViewModel : ViewModel() {
	private val _uiSideEffect = MutableSharedFlow<MviUiSideEffect>()
	val uiSideEffect = _uiSideEffect.asSharedFlow()

	private val _textFieldState = MutableStateFlow("")
	val textFieldState = _textFieldState.asStateFlow()

	private val _items = MutableStateFlow(emptyList<String>())
	val items = _items.asStateFlow()

	fun textFieldChange(value: String) = _textFieldState.update { value }

	fun navigateBack() {
		viewModelScope.launch {
			_uiSideEffect.emit(MviUiSideEffect.NavigateBack)
		}
	}

	fun addItem() {
		var snackMsg: String? = null

		_items.update {
			val textFiledValue = _textFieldState.value
			if (textFiledValue.isNotBlank()) {
				_textFieldState.update { "" }
				snackMsg = "Adding item: $textFiledValue"
				it + textFiledValue
			} else {
				snackMsg = "You can't add empty item"
				it
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