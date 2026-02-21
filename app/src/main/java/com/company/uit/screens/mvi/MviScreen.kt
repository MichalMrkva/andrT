package com.company.uit.screens.mvi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun MviScreen(
	navController: NavController,
	vm: MviViewModel = viewModel()
) {
	val snackBarHostState = remember { SnackbarHostState() }

	//Setup posluchače intentů
	LaunchedEffect(vm) {
		vm.uiSideEffect.collect { effect ->
			when (effect) {
				is MviUiSideEffect.NavigateBack -> navController.popBackStack()

				is MviUiSideEffect.ShowSnackBar -> {
					snackBarHostState.showSnackbar(
						message = effect.msg,
						duration = SnackbarDuration.Short
					)
				}
			}
		}
	}

	//Setup uiState aby při změně reference udělal trigger rekompozice UI
	val state by vm.uiState.collectAsStateWithLifecycle()

	//Pass všech states/callbacks do contentu
	MviContent(
		state = state,
		snack = snackBarHostState,
		action = vm::onAction
	)
}

@Composable
fun MviContent(
	state: HomeUIState,
	snack: SnackbarHostState,
	action: (HomeUIAction) -> Unit
) {
	Scaffold(
		snackbarHost = { SnackbarHost(snack) }
	) { paddingValues ->
		Column(
			modifier = Modifier.padding(paddingValues)
		) {
			Button(
				onClick = { action(HomeUIAction.NavigateBack) },
				modifier = Modifier.fillMaxWidth()
			) {
				Text("GO BACK")
			}
			Row {
				TextField(
					value = state.s,
					onValueChange = { action(HomeUIAction.TextFieldChanged(it)) }
				)
				Button(onClick = { action(HomeUIAction.AddItem) }) {
					Text("add")
				}
			}
			LazyColumn {
				items(state.items) {
					Text(it)
				}
			}
		}
	}
}