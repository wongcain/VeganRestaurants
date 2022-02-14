package cainwong.vegan.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cainwong.vegan.data.Place

@Composable
fun PriceRow(place: Place) {
    Row {
        repeat((0 until place.priceLevel).count()) {
            Text(
                text = "$",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}
