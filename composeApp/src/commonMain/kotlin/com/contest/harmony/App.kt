package com.contest.harmony
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        // State variables
        var selectedNote by remember { mutableStateOf("C") } // Root note
        var selectedChordType by remember { mutableStateOf(MusicTheory.ChordType.MAJOR) } // Chord type

        // Compute the list of notes to highlight
        val resultNotes = MusicTheory.getChordNotes(selectedNote, selectedChordType)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEEEEEE))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "🎹 Harmony Wiz",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )

            // --- SECTION 1: PIANO KEYBOARD ---
            Text("Select a root note:", style = MaterialTheme.typography.caption)

            // Call the keyboard component created in the other file
            PianoKeyboard(
                highlightedNotes = resultNotes, // Pass the highlighted notes
                onNoteClick = { note ->
                    selectedNote = note // Update root note when tapped
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            // --- SECTION 2: CHORD TYPE SELECTION ---
            Text("Select chord type:", style = MaterialTheme.typography.h6)
            Row(modifier = Modifier.padding(10.dp)) {
                MusicTheory.ChordType.values().forEach { type ->
                    Button(
                        onClick = { selectedChordType = type },
                        modifier = Modifier.padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (selectedChordType == type) Color(0xFF6C5CE7) else Color.White,
                            contentColor = if (selectedChordType == type) Color.White else Color.Black
                        )
                    ) {
                        Text(type.name.take(3))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- SECTION 3: RESULT ---
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Chord ${selectedNote} ${selectedChordType.title}", style = MaterialTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = resultNotes.joinToString(" - "),
                        style = MaterialTheme.typography.h3,
                        color = Color(0xFF2D3436),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}