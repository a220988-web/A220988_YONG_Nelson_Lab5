package com.example.a220988_yong_nelson_lab5.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a220988_yong_nelson_lab5.ui.AppViewModelProvider
import com.example.a220988_yong_nelson_lab5.ui.OrderViewModel
import com.example.a220988_yong_nelson_lab5.ui.home.StartOrderScreen
import com.example.a220988_yong_nelson_lab5.ui.home.SelectFlavorScreen
import com.example.a220988_yong_nelson_lab5.ui.home.SelectDateScreen
import com.example.a220988_yong_nelson_lab5.ui.home.OrderSummaryScreen
import com.example.a220988_yong_nelson_lab5.ui.home.OrderHistoryScreen

enum class CupcakeScreen(val title: String) {
    Start("Order Cupcakes"),
    Flavor("Choose Flavor"),
    Pickup("Choose Pickup Date"),
    Summary("Order Summary"),
    History("Order History")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFFFCE4EC)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun InventoryNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    val currentScreen = CupcakeScreen.valueOf(currentRoute)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        val historyList by viewModel.orderHistory.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    onNextButtonClicked = { quantity ->
                        viewModel.setQuantity(quantity)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    onCheckHistoryClicked = {
                        navController.navigate(CupcakeScreen.History.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.Flavor.name) {
                SelectFlavorScreen(
                    subtotal = uiState.price,
                    selectedFlavor = uiState.flavor,
                    onSelectionChanged = { flavor -> viewModel.setFlavor(flavor) },
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                    onCancelButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(CupcakeScreen.Start.name, false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.Pickup.name) {
                SelectDateScreen(
                    subtotal = uiState.price,
                    selectedDate = uiState.date,
                    options = viewModel.dateOptions,
                    onSelectionChanged = { date -> viewModel.setDate(date) },
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                    onCancelButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(CupcakeScreen.Start.name, false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.Summary.name) {
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(CupcakeScreen.Start.name, false)
                    },
                    onSendButtonClicked = { subject, text ->
                        viewModel.completeOrder()
                        shareOrder(context, subject, text)
                        navController.navigate(CupcakeScreen.History.name) {
                            popUpTo(CupcakeScreen.Start.name) { inclusive = false }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.History.name) {
                OrderHistoryScreen(
                    historyList = historyList,
                    onResetClicked = {
                        viewModel.resetOrder()
                        navController.navigate(CupcakeScreen.Start.name) {
                            popUpTo(CupcakeScreen.Start.name) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private fun shareOrder(context: Context, subject: String, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Sharing text"))
}