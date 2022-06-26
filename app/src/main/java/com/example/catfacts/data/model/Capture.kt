package com.example.catfacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catfacts.utils.RandomTextGenerator
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "captures_table")
data class Capture(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val captureId: Long = 0,
    val description: String,
    val captureDate: OffsetDateTime? = null
) {
    companion object {
        fun randomCapture(): Capture {

            return Capture(
                description = RandomTextGenerator.generateRandomText(),
                captureDate = OffsetDateTime.now()
            )
        }
    }
}