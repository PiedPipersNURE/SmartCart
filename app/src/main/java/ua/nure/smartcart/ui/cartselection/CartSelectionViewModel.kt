package ua.nure.smartcart.ui.cartselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartSelectionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cart selection menu"
    }
    val text: LiveData<String> = _text
}