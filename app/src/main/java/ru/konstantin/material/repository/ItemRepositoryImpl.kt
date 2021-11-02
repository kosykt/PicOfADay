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

//    override fun getAllItems(): List<Item> {
//        with(itemList) {
//            add(Item(111111111, "Title 1", "BeautyBox ver.1",  Date(), "https://rt.ru"))
//            add(Item(333, "Title 2", "BeautyBox ver.2",  Date(), "https://rt.ru"))
//            add(Item(444, "Title 3", "BeautyBox ver.3",  Date(), "https://rt.ru"))
//        }
//        return itemList
//    }

    override fun addItem(item: Item) {
//        return Item((1..999999999L).random(),"https://rt76utyhbfdvf.ru", "BeautyBox ver.10", (1..99999).random(), Date())
        localDataSource.insert(Mapping().convertItemToEntity(item))
    }

    override fun editItem(item: Item) {
        localDataSource.update(Mapping().convertItemToEntity(item))
    }

    override fun removeItem(item: Item) {
        println("We want remove $item")
    }
}