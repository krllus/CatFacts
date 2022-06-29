package com.example.catfacts.utils


import com.example.catfacts.R
import com.example.catfacts.models.NavigationItem
import com.example.catfacts.ui.HomeActivity

object Constants {
    val BottomNavigationItens = listOf(
        NavigationItem(
            label = R.string.navigation_item_journal,
            icon = R.drawable.ic_baseline_notes_24,
            route = Destination.JournalList,
            contentDescription = R.string.navigation_item_journal_description
        ),
        NavigationItem(
            label = R.string.navigation_item_facts,
            icon = R.drawable.ic_cat,
            route = Destination.Facts,
            contentDescription = R.string.navigation_item_facts_description
        )
    )
}

sealed class Destination(val destinationName: String) {
    object JournalList : Destination("journal")
    object JournalCrud : Destination("journal_crud")
    object JournalDetails : Destination("journal_details/{${HomeActivity.KEY_JOURNAL_ID}}")
    object Facts : Destination("facts")
}