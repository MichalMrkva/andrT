package com.company.uit.screens.mvvm

sealed interface MvvmUiSideEffect {
	data object NavigateBack : MvvmUiSideEffect
	data class ShowSnackBar(val msg: String) : MvvmUiSideEffect
}