package com.company.uit.screens.mvi

sealed interface MviUiSideEffect {
	data object NavigateBack : MviUiSideEffect
	data class ShowSnackBar(val msg: String) : MviUiSideEffect
}