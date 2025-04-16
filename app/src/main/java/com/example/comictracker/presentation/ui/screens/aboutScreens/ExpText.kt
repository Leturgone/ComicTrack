package com.example.comictracker.presentation.ui.screens.aboutScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Expandable text
 *
 * @param text
 * @param modifier
 * @param maxLines
 * @param width
 */
@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 4,
    width: Int = 200
){
    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }

    val textOverflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis
    val currentMaxLines = if (isExpanded) Int.MAX_VALUE else maxLines

    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.White,
        modifier = modifier
            .width(width.dp)
            .clickable {
                if (isOverflowing) {
                    isExpanded = !isExpanded
                }
            },
        textAlign = TextAlign.Justify,
        overflow = textOverflow,
        maxLines = currentMaxLines,

        onTextLayout = { textLayoutResult ->
            isOverflowing = textLayoutResult.hasVisualOverflow
        }
    )
    if (isOverflowing && !isExpanded) {
        Text(
            text = " ...See More",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = modifier
                .clickable { isExpanded = true },
            textAlign = TextAlign.Justify,
            maxLines = 1
        )
    } else if (isExpanded) {
        Text(
            text = " See Less",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = modifier
                .clickable { isExpanded = false },
            textAlign = TextAlign.Justify,
            maxLines = 1
        )
    }

}