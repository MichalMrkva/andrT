package com.company.uit.screens.mvi

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUIState(
	val s: String = "",
	val items: List<String> = emptyList()
)