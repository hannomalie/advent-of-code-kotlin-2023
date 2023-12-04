import java.io.File
import kotlin.math.max
import kotlin.math.min

data class Gear(val symbol: Symbol, val a: Number, val b: Number)
data class Symbol(val value: String, val row: Int, val column: Int)
data class Number(val value: String, val row: Int, val endColumn: Int) {
    val startColumn = endColumn - value.count() + 1
}
fun main() {
    run {
        val input = File("src/Day03_test.txt")

        val (symbols, numbers) = input.parseSymbolsAndNumbers()

        val partNumbers = numbers.filter {
            it.isPartNumber(symbols)
        }

        check(symbols[0] == Symbol("*", 1, 3))
        check(numbers.first { it == Number("467",0,2) }.getAdjacentSymbols(symbols) == listOf(Symbol("*", 1, 3)))
        check(numbers.first { it.value == "35" }.getAdjacentSymbols(symbols).size == 1)
        check(numbers.first { it.value == "633" }.getAdjacentSymbols(symbols).size == 1)
        check(numbers.first { it.value == "617" }.getAdjacentSymbols(symbols) == listOf(Symbol("*", 4, 3)))
        check(numbers.first { it.value == "592" }.getAdjacentSymbols(symbols).size == 1)
        check(numbers.first { it.value == "755" }.getAdjacentSymbols(symbols).size == 1)
        check(numbers.first { it.value == "664" }.getAdjacentSymbols(symbols) == listOf(Symbol("$", 8, 3)))
        check(numbers.first { it.value == "598" }.getAdjacentSymbols(symbols) == listOf(Symbol("*", 8, 5)))

        check(numbers.first { it.value == "114" }.getAdjacentSymbols(symbols) == emptyList<Symbol>())
        check(numbers.first { it.value == "58" }.getAdjacentSymbols(symbols) == emptyList<Symbol>())

        check(partNumbers.size == numbers.size - 2)
        check(partNumbers.firstOrNull { it.value == "114" } == null)
        check(partNumbers.firstOrNull { it.value == "58" } == null)

        check(partNumbers.sumOf { it.value.toInt() } == 4361)

        val gears = getGears(symbols, numbers)
        check(gears.size == 2)
        val gearRatios = gears.map { it.a.value.toInt() * it.b.value.toInt() }
        check(gearRatios[0] == 16345)
        check(gearRatios[1] == 451490)
    }
    run {
        val input = File("src/Day03.txt")

        val (symbols, numbers) = input.parseSymbolsAndNumbers()

        val partNumbers = numbers.filter {
            it.isPartNumber(symbols)
        }
        println("Result Part 1:")
        println(partNumbers.sumOf { it.value.toInt() })

        val gears = getGears(symbols, numbers)
        val gearRatios = gears.map { it.a.value.toInt() * it.b.value.toInt() }

        println("Result Part 2:")
        println(gearRatios.sum())
    }
}

internal fun getGears(
    symbols: List<Symbol>,
    numbers: List<Number>
) = symbols.mapNotNull {
    if(it.isGear(numbers, symbols)) {
        val adjacentNumbers = it.getAdjacentNumbers(numbers, symbols)
        Gear(it, adjacentNumbers[0], adjacentNumbers[1])
    } else null
}

internal fun Symbol.isGear(numbers: List<Number>, symbols: List<Symbol>): Boolean {
    return value == "*" && getAdjacentNumbers(numbers, symbols).size == 2
}

private fun Symbol.getAdjacentNumbers(
    numbers: List<Number>,
    symbols: List<Symbol>
) = numbers.filter { it.getAdjacentSymbols(symbols).contains(this) }

internal fun File.parseSymbolsAndNumbers() = readLines().parseSymbolsAndNumbers()
internal fun String.parseSymbolsAndNumbers() = lines().parseSymbolsAndNumbers()
internal fun List<String>.parseSymbolsAndNumbers(): Pair<List<Symbol>, List<Number>> {
    val symbols = mutableListOf<Symbol>()
    val numbers = mutableListOf<Number>()
    mapIndexed { row, line ->

        var currentNumber = ""
        line.forEachIndexed { column, char ->
            if (char.isDigit()) {
                currentNumber += char.toString()
            } else if (char.toString() == ".") {
                if (currentNumber != "") {
                    numbers.add(Number(value = currentNumber, row = row, endColumn = column - 1))
                    currentNumber = ""
                }
            } else {
                if (currentNumber != "") {
                    numbers.add(Number(value = currentNumber, row = row, endColumn = column - 1))
                    currentNumber = ""
                }
                symbols.add(Symbol(char.toString(), row, column))
            }
            val isLastChar = column == line.length - 1
            if (isLastChar && currentNumber != "") {
                numbers.add(Number(value = currentNumber, row = row, endColumn = column))
                currentNumber = ""
            }
        }
    }
    return Pair(symbols, numbers)
}

internal fun Number.isPartNumber(symbols: List<Symbol>): Boolean {
    val adjacentSymbols = getAdjacentSymbols(symbols)
    return adjacentSymbols.isNotEmpty()
}

internal fun Number.getAdjacentSymbols(symbols: List<Symbol>): List<Symbol> {
    val adjacentSymbols = mutableSetOf<Symbol>()
    symbols.forEach { symbol ->
        for (currentColumn in startColumn.. endColumn) {
            val horizontalDistance = max(row, symbol.row) - min(row, symbol.row)
            if (horizontalDistance <= 1) {
                val verticalDistance = max(currentColumn, symbol.column) - min(currentColumn, symbol.column)
                if (verticalDistance <= 1) {
                    adjacentSymbols.add(symbol)
                }
            }
        }
    }
    return adjacentSymbols.toList()
}