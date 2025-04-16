package com.example.comictracker.presentation.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Mark category
 *
 * @property icon
 * @property title
 * @property markBD
 * @constructor Create empty Mark category
 */
data class MarkCategory(
    val icon: ImageVector,
    val title:String,
    val markBD:String
)
