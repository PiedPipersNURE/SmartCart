package ua.nure.smartcart.ui.cartgallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartGalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cart gallery menu"
    }
    val text: LiveData<String> = _text
}