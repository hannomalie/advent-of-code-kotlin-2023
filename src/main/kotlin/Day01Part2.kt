import java.io.File

fun main() {
    run {
        val expectedTwoDigitNumbers = listOf(29, 83, 13, 24, 42, 14, 76)
        val expectedSum = 281

        val testInput = File("src/Day01Part2_test.txt").readLines()

        val actualTwoDigitValues = testInput.mapToFirstAndLastDigit()
        check(actualTwoDigitValues == expectedTwoDigitNumbers)

        val actualSum = expectedTwoDigitNumbers.sum()
        check(actualSum == expectedSum)
    }

    val input = File("src/Day01PartTwo.txt").readLines()
    println("Result:")
    println(input.mapToFirstAndLastDigit().sum())
}

data class IndexAndChar(val index: Int, val char: Char)
private val String.firstCharacter: Char
    get() = extractCharsWithIndex().minBy { it.index }.char
private val String.lastCharacter: Char
    get() = extractCharsWithIndex().maxBy { it.index }.char

private fun String.extractCharsWithIndex(): List<IndexAndChar> = replaceWords().mapIndexedNotNull { index, char ->
    if (char.isDigit()) IndexAndChar(index, char) else null
}

data class Word(val value: String, val replacement: String)
data class IndexAndWord(val index: Int, val word: Word)
private fun String.replaceWords(): String {
    val indexAndWords = listOf(
        Word("one", "_1_"),
        Word("two", "_2_"),
        Word("three", "_3_"),
        Word("four", "_4_"),
        Word("five", "_5_"),
        Word("six", "_6_"),
        Word("seven", "_7_"),
        Word("eight", "_8_"),
        Word("nine", "_9_"),
    ).mapNotNull{ word ->
        if(contains(word.value)) {
            IndexAndWord(this.indexOf(word.value), word)
        } else null
    }.sortedBy { it.index }
    val first = indexAndWords.firstOrNull()
    val last = indexAndWords.lastOrNull()

    return if(first == null) {
        this
    } else if(last == null) {
        this.replaceFirst(first.word.value, first.word.replacement)
    } else {
        this.replaceFirst(first.word.value, first.word.replacement)
            .replace(last.word.value, last.word.replacement)
    }
}

private fun List<String>.mapToFirstAndLastDigit() = map { line ->
    val firstOrNull = line.findAnyOf(digits)!!.second
    val lastOrNull = line.findLastAnyOf(digits)?.second ?: firstOrNull

    firstOrNull.toDigit() + lastOrNull.toDigit()
//    line.findAnyOf(digits).second + line.findLastAnyOf(digits).toString()
//    line.firstCharacter.toString() + line.lastCharacter.toString()
}.map { it.toInt() }

val digits = listOf(
    "1", "2", "3", "4", "5", "6", "7", "8", "9",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
)

fun String.toDigit() = when(this) {
    "one" -> "1"
    "two" -> "2"
    "three" -> "3"
    "four" -> "4"
    "five" -> "5"
    "six" -> "6"
    "seven" -> "7"
    "eight" -> "8"
    "nine" -> "9"
    else -> this
}
