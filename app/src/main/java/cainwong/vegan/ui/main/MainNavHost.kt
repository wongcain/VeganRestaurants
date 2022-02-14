package cainwong.vegan.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cainwong.vegan.ui.detail.DetailScreen
import cainwong.vegan.ui.permission.LocationPermissionRequired
import cainwong.vegan.ui.search.SearchScreen
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

object Routes {
    const val SEARCH = "search"
    const val DETAIL = "detail"
}

object ArgKeys {
    const val ID = "id"
}

@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@FlowPreview
@Composable
fun MainNavHost(
    navController: NavHostController,
    onError: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(Routes.SEARCH) {
            LocationPermissionRequired {
                SearchScreen(
                    viewModel = hiltViewModel(),
                    onResultClicked = {
                        navController.navigate("${Routes.DETAIL}/${it.id}")
                    },
                    onSearchError = onError,
                )
            }
        }
        composable("${Routes.DETAIL}/{${ArgKeys.ID}}") {
            val id = it.arguments?.getString(ArgKeys.ID) ?: ""
            DetailScreen(
                viewModel = hiltViewModel(),
                id = id,
            )
        }
    }
}
