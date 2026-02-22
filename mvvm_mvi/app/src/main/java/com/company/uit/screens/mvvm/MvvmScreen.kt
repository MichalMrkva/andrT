package com.company.uit.screens.mvvm

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
import com.company.uit.screens.mvi.MviUiSideEffect

@Composable
fun MvvmScreen(
	navController: NavController,
	vm: MvvmViewModel = viewModel()
) {
	val snackBarHostState = remember { SnackbarHostState() }

	LaunchedEffect(vm) {
		vm.uiSideEffect.collect { effect ->
			when (effect) {
				is com.company.uit.screens.mvi.MviUiSideEffect.NavigateBack -> navController.popBackStack()

				is MviUiSideEffect.ShowSnackBar -> {
					snackBarHostState.showSnackbar(
						message = effect.msg,
						duration = SnackbarDuration.Short
					)
				}
			}
		}
	}

	val textFieldState by vm.textFieldState.collectAsStateWithLifecycle()
	val itemsState by vm.items.collectAsStateWithLifecycle()

	MvvmContent(
		snack = snackBarHostState,
		inputField = textFieldState,
		items = itemsState,
		onAddItem = vm::addItem,
		onNavBack = vm::navigateBack,
		onInputChange = vm::textFieldChange
	)
}

@Composable
fun MvvmContent(
	snack: SnackbarHostState,
	inputField: String,
	items: List<String>,
	onAddItem: () -> Unit,
	onNavBack: () -> Unit,
	onInputChange: (String) -> Unit,
) {
	Scaffold(
		snackbarHost = { SnackbarHost(snack) }
	) { paddingValues ->
		Column(
			modifier = Modifier.padding(paddingValues)
		) {
			Button(
				onClick = { onNavBack() },
				modifier = Modifier.fillMaxWidth()
			) {
				Text("GO BACK")
			}
			Row {
				TextField(
					value = inputField,
					onValueChange = { onInputChange(it) }
				)
				Button(onClick = { onAddItem() }) {
					Text("add")
				}
			}
			LazyColumn {
				items(items) {
					Text(it)
				}
			}
		}
	}
}