package ru.konstantin.material.model

import ru.konstantin.material.room.ItemEntity
import ru.konstantin.material.ui.picture.PODServerResponseData
import java.util.*

class Mapping {

    fun convertItemEntityToItemModel(itemList: List<ItemEntity>): List<Item> {
        return itemList.map { Item(it.id, it.title, it.description, it.date?.let { it1 -> Date(it1) }, it.imageUrl) }
    }

    fun convertItemToEntity(itemModel: Item): ItemEntity {
        return ItemEntity(0, itemModel.title, itemModel.description, itemModel.date?.time, itemModel.imageUrl)
    }

    fun convertPODServerResponseDataToItem(data: PODServerResponseData): Item {
        return Item(
            0,
            data.title?:"",
            data.explanation,
            Date(),
            data.url
        )
    }
}