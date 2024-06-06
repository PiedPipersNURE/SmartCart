package ua.nure.smartcart.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Log-in to see your shopping lists..."
    }
    val text: LiveData<String> = _text
}