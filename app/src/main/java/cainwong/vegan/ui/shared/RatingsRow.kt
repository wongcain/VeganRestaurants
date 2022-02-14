package cainwong.vegan.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import cainwong.vegan.data.Place
import cainwong.vegan.ui.theme.Grey
import cainwong.vegan.ui.theme.Yellow
import kotlin.math.roundToInt

@Composable
fun RatingsRow(place: Place) {
    val ratingInt = place.rating.roundToInt()
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${place.rating}",
            style = MaterialTheme.typography.caption
        )
        repeat((0 until ratingInt).count()) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Filled Star",
                tint = Yellow,
            )
        }
        repeat((ratingInt until MAX_RATING).count()) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Filled Star",
                tint = Grey,
            )
        }
        Text(
            text = "(${place.userRatingsTotal})",
            style = MaterialTheme.typography.caption
        )
    }
}

const val MAX_RATING = 5
