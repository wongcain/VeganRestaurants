package cainwong.vegan.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cainwong.vegan.R
import cainwong.vegan.data.Place
import cainwong.vegan.ui.theme.dimens
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@FlowPreview
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel,
    onResultClicked: (Place) -> Unit,
    onSearchError: (String) -> Unit,
) {
    val searchState by viewModel.searchScreenState.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var showMap by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (!showMap || isPortrait) {
                    SearchBar(
                        onSearchTextChanged = { viewModel.setSearchText(it) },
                    )
                }
                if (searchState?.location != null) {
                    if (showMap) {
                        SearchResultsMap(
                            searchState = searchState,
                            onResultClicked = onResultClicked,
                        )
                    } else {
                        SearchResultsList(
                            searchState = searchState,
                            onResultClicked = onResultClicked,
                        )
                    }
                }
            }
            ListMapToggleButton(showMap) {
                showMap = !showMap
            }
        }
        if (searchState is SearchState.Loading) {
            CircularProgressIndicator()
        }
        if (searchState is SearchState.Error) {
            onSearchError(stringResource((searchState as SearchState.Error).message))
        }
    }
}

@Composable
fun ListMapToggleButton(
    showMap: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.padding(bottom = MaterialTheme.dimens.medium),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    if (showMap) {
                        R.drawable.ic_baseline_list_24
                    } else {
                        R.drawable.ic_baseline_map_24
                    }
                ),
                contentDescription = "Back",
            )
            Text(
                modifier = Modifier
                    .padding(start = MaterialTheme.dimens.small),
                text = if (showMap) {
                    "List"
                } else {
                    "Map"
                }
            )
        }
    }
}
