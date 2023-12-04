import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class Day03 {

    @Test
    fun `numbers and symbols are determined correctly`() {
        val (symbols, numbers) = """.*...
            |.12..
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(symbols).isEqualTo(listOf(Symbol("*", 0, 1)))
        assertThat(numbers).isEqualTo(listOf(Number("12", 1, 2)))
    }

    @Test
    fun `numbers and symbols are determined correctly when no distance`() {
        val (symbols, numbers) = """.*12#...
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(symbols).isEqualTo(listOf(Symbol("*", 0, 1), Symbol("#", 0, 4)))
        assertThat(numbers).isEqualTo(listOf(Number("12", 0, 3)))
    }

    @Test
    fun `part number status is determined when adjacent`() {
        val (symbols, numbers) = """.*...
            |.12..
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(numbers.first { it.value == "12" }.isPartNumber(symbols)).isTrue()
    }

    @Test
    fun `part number status is determined when diagonally adjacent`() {
        val (symbols, numbers) = """*....
            |.12..
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(numbers.first { it.value == "12" }.isPartNumber(symbols)).isTrue()
    }
    @Test
    fun `part number status is determined when diagonally adjacent 1`() {
        val (symbols, numbers) = """.12..
            |...*.
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(numbers.first { it.value == "12" }.isPartNumber(symbols)).isTrue()
    }

    @Test
    fun `part number status is determined when diagonally adjacent to multiple`() {
        val (symbols, numbers) = """12#..
            |..*..
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(numbers.first { it.value == "12" }.isPartNumber(symbols)).isTrue()
    }

    @Test
    fun `number is parsed when symbol directly besides it`() {
        val (symbols, numbers) = """-12#..
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(numbers.first { it.value == "12" }.isPartNumber(symbols)).isTrue()
    }

    @Test
    fun `numbers are parsed when only one dot between them`() {
        val (symbols, numbers) = """.12.24.
        """.trimMargin().parseSymbolsAndNumbers()

        assertThat(numbers).contains(Number("12", 0, 2))
        assertThat(numbers).contains(Number("24", 0, 5))
    }

    @Test
    fun `test input from reddit is parsed correctly`() {
        val (symbols, numbers) = """12.......*..
            |+.........34
            |.......-12..
            |..78........
            |..*....60...
            |78..........
            |.......23...
            |....90*12...
            |............
            |2.2......12.
            |.*.........*
            |1.1.......56""".trimMargin().parseSymbolsAndNumbers()

        Number("34", 1, 11).let { number ->
            assertThat(numbers).contains(number)
            assertThat(number.isPartNumber(symbols)).isTrue()
        }

        assertThat(numbers.filter { it.isPartNumber(symbols) }.sumOf { it.value.toInt() }).isEqualTo(413)
    }
}