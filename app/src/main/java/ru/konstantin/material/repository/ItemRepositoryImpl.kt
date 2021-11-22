package ru.konstantin.material.repository

import ru.konstantin.beautybox.repository.ItemRepository
import ru.konstantin.material.model.Item
import ru.konstantin.material.model.Mapping
import ru.konstantin.material.room.ItemDao
import java.util.*

class ItemRepositoryImpl(private val localDataSource: ItemDao): ItemRepository {
    override fun getAllItems(): List<Item> {
        val resp = localDataSource.all()
        return Mapping().convertItemEntityToItemModel(resp)
    }

    private var itemList = mutableListOf<Item>()

    override fun addItem(item: Item) {
        localDataSource.insert(Mapping().convertItemToEntity(item))
    }

    override fun editItem(item: Item) {
        localDataSource.update(Mapping().convertItemToEntity(item))
    }

    override fun removeItem(item: Item) {
        println("We want remove $item")
    }
}