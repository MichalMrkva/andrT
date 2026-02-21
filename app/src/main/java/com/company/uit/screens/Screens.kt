package com.company.uit.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens {
	@Serializable
	data object Home : Screens

	@Serializable
	data object Mvvm : Screens

	@Serializable
	data object Mvi : Screens
}