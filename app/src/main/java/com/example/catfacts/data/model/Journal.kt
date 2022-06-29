package com.example.catfacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catfacts.utils.RandomTextGenerator
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "table_journal")
data class Journal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "journal_id")
    val journalId: Long = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "image_file_path")
    val imageFilePath: String? = null,

    @ColumnInfo(name = "capture_date")
    val captureDate: OffsetDateTime = OffsetDateTime.now()
) {
    companion object {
        fun randomCapture(): Journal {

            return Journal(
                title = RandomTextGenerator.generateRandomText(10),
                description = RandomTextGenerator.generateRandomText(50),
                captureDate = OffsetDateTime.now()
            )
        }
    }

    fun displayDescription(): Boolean {
        return description.isNotEmpty()
    }
}