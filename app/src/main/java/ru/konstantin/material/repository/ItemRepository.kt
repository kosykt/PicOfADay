package ru.konstantin.beautybox.repository

import ru.konstantin.material.model.Item

interface ItemRepository {

    fun getAllItems(): List<Item>

    fun addItem(item: Item)

    fun editItem(item: Item)

    fun removeItem(item: Item)
}