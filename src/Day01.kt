import java.io.File

fun main() {
    run {
        val expectedTwoDigitNumbers = listOf(12, 38, 15, 77)
        val expectedSum = 142

        val testInput = File("src/Day01_test.txt").readLines()

        val actualTwoDigitValues = testInput.mapToTwoDigits()
        check(actualTwoDigitValues == expectedTwoDigitNumbers)

        val actualSum = expectedTwoDigitNumbers.sum()
        check(actualSum == expectedSum)
    }

    val input = File("src/Day01.txt").readLines()
    println("Result:")
    println(input.mapToTwoDigits().sum())
}

private val String.firstCharacter get() = first { char -> char.isDigit() }
private val String.lastCharacter get() = last { it.isDigit() }

private fun List<String>.mapToTwoDigits() = map { line ->
    line.firstCharacter.toString() + line.lastCharacter.toString()
}.map { it.toInt() }