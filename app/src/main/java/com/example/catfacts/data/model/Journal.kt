package com.example.catfacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catfacts.utils.RandomTextGenerator
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "table_journal")
data class Journal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val captureId: Long = 0,
    val description: String,
    val captureDate: OffsetDateTime? = null
) {
    companion object {
        fun randomCapture(): Journal {

            return Journal(
                description = RandomTextGenerator.generateRandomText(),
                captureDate = OffsetDateTime.now()
            )
        }
    }
}