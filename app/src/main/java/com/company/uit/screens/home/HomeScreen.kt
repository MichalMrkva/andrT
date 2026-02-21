package com.company.uit.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.company.uit.screens.Screens

@Composable
fun HomeScreen(navController: NavController) {
	Scaffold { paddingValues ->
		Column(
			modifier = Modifier
				.padding(paddingValues),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Button(
				onClick = { navController.navigate(Screens.Mvi) }
			) {
				Text("Mvi screen")
			}
		}
	}
}