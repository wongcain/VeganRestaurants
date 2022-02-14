package cainwong.vegan.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import cainwong.vegan.R
import cainwong.vegan.data.Place
import cainwong.vegan.ui.shared.PriceRow
import cainwong.vegan.ui.shared.RatingsRow
import cainwong.vegan.ui.theme.Grey
import cainwong.vegan.ui.theme.dimens
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun DetailScreen(
    viewModel: DetailsScreenViewModel,
    id: String,
) {
    val place = viewModel.getPlace(id).observeAsState().value ?: return
    DetailsScreenContent(place)
}

@ExperimentalCoilApi
@Composable
fun DetailsScreenContent(
    place: Place,
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
    val photoWidthDp = min(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    val photoWidthPx = with(LocalDensity.current) { photoWidthDp.toPx().toInt() }
    if (isPortrait) {
        PortraitLayout(
            place = place,
            photoWidthPx = photoWidthPx,
        ) {
            InfoColumn(place)
        }
    } else {
        LandscapeLayout(
            place = place,
            photoWidthPx = photoWidthPx
        ) {
            InfoColumn(place)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PortraitLayout(
    place: Place,
    photoWidthPx: Int,
    content: @Composable () -> Unit,
) {
    Column {
        Box(modifier = Modifier.background(color = Grey)) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .height(MaterialTheme.dimens.xxxLarge),
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(
                    data = place.photoUrl(photoWidthPx),
                ),
                contentDescription = "Photo of ${place.name}",
            )
        }
        content()
    }
}

@Composable
fun LandscapeLayout(
    place: Place,
    photoWidthPx: Int,
    content: @Composable () -> Unit,
) {
    Row {
        Box(modifier = Modifier.background(color = Grey)) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .height(MaterialTheme.dimens.xxxLarge),
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(
                    data = place.photoUrl(photoWidthPx),
                ),
                contentDescription = "Photo of ${place.name}",
            )
        }
        content()
    }
}

@Composable
fun InfoColumn(
    place: Place,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(all = MaterialTheme.dimens.medium),
    ) {
        Text(
            text = place.name,
            style = MaterialTheme.typography.h6,
        )
        RatingsRow(place)
        PriceRow(place)
        Row(
            modifier = Modifier.clickable {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(place.directionsUrl())
                    )
                )
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(end = MaterialTheme.dimens.small),
                text = place.address,
                style = MaterialTheme.typography.body1,
            )
            Icon(
                painter = painterResource(R.drawable.ic_baseline_directions_24),
                contentDescription = "Back",
            )
        }
    }
}
