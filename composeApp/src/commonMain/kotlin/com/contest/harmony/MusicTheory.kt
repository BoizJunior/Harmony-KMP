package com.contest.harmony

object MusicTheory {
    // 1. List of the 12 notes in an octave (Chromatic Scale)
    private val notes = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

    // 2. Interval formulas of chord types (based on semitone steps)
    enum class ChordType(val title: String, val intervals: List<Int>) {
        MAJOR("Major ", listOf(0, 4, 7)),     // Formula: Root + 4 + 7 semitones
        MINOR("Minor ", listOf(0, 3, 7)),        // Formula: Root + 3 + 7 semitones
        DIMINISHED("Diminished ", listOf(0, 3, 6)),
        AUGMENTED("Augmented ", listOf(0, 4, 8))
    }

    // 3. Calculation function: Input root note + chord type -> Output list of notes
    fun getChordNotes(rootNote: String, type: ChordType): List<String> {
        val rootIndex = notes.indexOf(rootNote)
        if (rootIndex == -1) return emptyList()

        return type.intervals.map { interval ->
            // Circular algorithm: Wrap around the list if index exceeds 12 (Modulo 12)
            val noteIndex = (rootIndex + interval) % notes.size
            notes[noteIndex]
        }
    }
}