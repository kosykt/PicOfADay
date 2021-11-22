package ru.konstantin.material.room

import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM ItemEntity")
    fun all(): List<ItemEntity>

    @Query("SELECT * FROM ItemEntity WHERE id=:id")
    fun getDataByWord(id: Long): ItemEntity


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: ItemEntity)

    @Update
    fun update(entity: ItemEntity)

    @Delete
    fun delete(entity: ItemEntity)
}