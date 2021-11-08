package ru.konstantin.material.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.konstantin.material.room.ItemDao
import ru.konstantin.material.room.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class ItemDataBase : RoomDatabase() {
    abstract fun historyDao(): ItemDao
}