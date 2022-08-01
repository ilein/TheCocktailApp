package com.ilein.thecocktailapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = DrinkFavoriteDb.DRINK_LIKE_TABLE)
data class DrinkEntity (
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String?,

    @ColumnInfo(name = COLUMN_IMAGE)
    val image: String?,

    @ColumnInfo(name=COLUMN_CREATE_DATE)
    val createDate: LocalDateTime?,

    @ColumnInfo(name = COLUMN_NOTE)
    val note: String?,

    ) {
    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE = "image_link"
        const val COLUMN_CREATE_DATE = "update_date"
        const val COLUMN_NOTE = "note"
    }
}
