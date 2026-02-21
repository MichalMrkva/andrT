package com.company.uit.screens.mvi

sealed interface HomeUIAction {
	data object NavigateBack : HomeUIAction
	data class TextFieldChanged(val s: String) : HomeUIAction
	data object AddItem : HomeUIAction
}