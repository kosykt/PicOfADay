package ru.konstantin.material.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Item(
    val id: Long,
    val title: String,
    val description: String?,
    val date: Date?,
    val imageUrl: String?
) : Parcelable