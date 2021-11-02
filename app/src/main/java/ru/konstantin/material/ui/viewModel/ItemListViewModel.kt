package ru.konstantin.material.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.konstantin.material.repository.ItemRepositoryImpl
import ru.konstantin.material.app.App.Companion.getItemDao
import ru.konstantin.material.model.Item
import ru.konstantin.material.model.ViewState

class ItemListViewModel(private val itemRepository: ItemRepositoryImpl = ItemRepositoryImpl(getItemDao())): ViewModel() {

    private val itemLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun getData(): LiveData<ViewState> {
        return itemLiveData
    }

    fun getAllItems() {
//        itemLiveData.postValue(ViewState.Loading)
        itemLiveData.value = ViewState.Success(itemRepository.getAllItems())
    }

    fun saveItem(item: Item) {
        itemRepository.addItem(item)
    }
}