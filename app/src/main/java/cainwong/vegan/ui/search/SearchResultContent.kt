package cainwong.vegan.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import cainwong.vegan.data.Place
import cainwong.vegan.ui.shared.PriceRow
import cainwong.vegan.ui.shared.RatingsRow
import cainwong.vegan.ui.theme.dimens
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun SearchResultContent(
    place: Place,
) {
    val imageSizePx = with(LocalDensity.current) { MaterialTheme.dimens.xLarge.toPx().toInt() }
    Row(
        modifier = Modifier.padding(MaterialTheme.dimens.small),
    ) {
        Image(
            modifier = Modifier
                .width(MaterialTheme.dimens.xLarge)
                .height(MaterialTheme.dimens.xLarge),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = place.photoUrl(imageSizePx),
            ),
            contentDescription = "Photo of ${place.name}",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimens.medium,
                    end = MaterialTheme.dimens.medium,
                ),
        ) {

            Text(
                text = place.name,
                style = MaterialTheme.typography.subtitle1,
            )
            RatingsRow(place)
            PriceRow(place)
        }
    }
}
