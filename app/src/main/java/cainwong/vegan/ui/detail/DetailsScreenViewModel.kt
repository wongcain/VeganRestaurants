package cainwong.vegan.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cainwong.vegan.data.Place
import cainwong.vegan.data.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {

    fun getPlace(id: String) = liveData<Place> {
        val place = placesRepository.getPlace(id)
        emit(place)
    }
}
