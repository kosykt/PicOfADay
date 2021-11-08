package ru.konstantin.material.model

import ru.konstantin.material.room.ItemEntity
import java.util.*

class Mapping {

    fun convertItemEntityToItemModel(itemList: List<ItemEntity>): List<Item> {
        return itemList.map { Item(it.id, it.title, it.description, it.date?.let { it1 -> Date(it1) }, it.imageUrl) }
    }

    fun convertItemToEntity(itemModel: Item): ItemEntity {
        return ItemEntity(0, itemModel.title, itemModel.description, itemModel.date?.time, itemModel.imageUrl)
    }
}