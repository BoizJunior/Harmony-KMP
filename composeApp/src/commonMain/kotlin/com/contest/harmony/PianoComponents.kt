package com.contest.harmony

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Standard sizes for piano keys
val whiteKeyWidth = 40.dp
val whiteKeyHeight = 160.dp
val blackKeyWidth = 24.dp // Smaller than white keys
val blackKeyHeight = 100.dp // Shorter than white keys


@Composable
fun PianoKeyboard(
    highlightedNotes: List<String>, // List of notes to highlight (chord notes)
    onNoteClick: (String) -> Unit  // Callback when a key is pressed
) {
    Box(modifier = Modifier.padding(10.dp)) {
        // LAYER 1: Draw all WHITE keys at the bottom
        Row {
            val whiteNotes = listOf("C", "D", "E", "F", "G", "A", "B")
            whiteNotes.forEach { note ->
                PianoKey(
                    note = note,
                    isWhite = true,
                    isHighlighted = highlightedNotes.contains(note),
                    onClick = { onNoteClick(note) }
                )
                Spacer(modifier = Modifier.width(2.dp)) // Gap between white keys
            }
        }

        // LAYER 2: Draw BLACK keys on top
        // Black keys are offset, so use Offset or Spacer to align them
        Row(modifier = Modifier.padding(start = whiteKeyWidth - (blackKeyWidth / 2))) {
            val blackNotesMap = listOf(
                "C#" to true,  // Has a black key
                "D#" to true,  // Has a black key
                "Spacer" to false, // E-F has no black key, so insert spacing
                "F#" to true,
                "G#" to true,
                "A#" to true
            )

            blackNotesMap.forEach { (note, exists) ->
                if (exists) {
                    PianoKey(
                        note = note,
                        isWhite = false,
                        isHighlighted = highlightedNotes.contains(note),
                        onClick = { onNoteClick(note) }
                    )
                    // Spacing to the next black key:
                    // (White key width) - (Black key width) + (gap)
                    Spacer(modifier = Modifier.width(whiteKeyWidth - blackKeyWidth + 2.dp))
                } else {
                    // For the E-F gap (no black key), insert spacing equal to one white key
                    Spacer(modifier = Modifier.width(whiteKeyWidth + 2.dp))
                }
            }
        }
    }
}

@Composable
fun PianoKey(
    note: String,
    isWhite: Boolean,
    isHighlighted: Boolean,
    onClick: () -> Unit
) {
    // Colors: If highlighted, use Yellow/Blue. If not, use White/Black
    val keyColor = if (isHighlighted) {
        Color(0xFFFFD700) // Gold color for highlighted notes
    } else {
        if (isWhite) Color.White else Color.Black
    }

    val textColor = if (isWhite && !isHighlighted) Color.Black else Color.Gray

    Box(
        modifier = Modifier
            .width(if (isWhite) whiteKeyWidth else blackKeyWidth)
            .height(if (isWhite) whiteKeyHeight else blackKeyHeight)
            .background(
                color = keyColor,
                shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
            )
            .border(1.dp, Color.Black, RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        if (isWhite) {
            Text(text = note, color = textColor, fontSize = 10.sp, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}