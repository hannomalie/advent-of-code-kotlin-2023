import java.io.File
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    run {
        val inputFile = File("src/Day04.txt")

        val cardsAndNumbersIHave =  inputFile.readText().trim().parseCardsAndWinningNumbers()

        val winnersPerCard = cardsAndNumbersIHave.getWinnersPerCard()

        val pointsPerCard = winnersPerCard.calculatePointsPerCard()

        println("Result Part1:")
        println(pointsPerCard.sum())

        println("Result Part2:")

        val pile = winnersPerCard.calculatePile()
        println(pile.sumOf { it.get() })
    }
}

internal fun List<List<Int>>.calculatePile(): List<AtomicInteger> {
    val pile = map { AtomicInteger(1) }
    forEachIndexed { cardIndex, winners ->
        repeat(pile[cardIndex].get()) {
            repeat(winners.size) { winnerIndex ->
                val indexToIncrement = cardIndex + winnerIndex + 1
                if (indexToIncrement < size) {
                    pile[indexToIncrement].getAndIncrement()
                }
            }
        }
    }
    return pile
}

fun String.parseCardsAndWinningNumbers() = lines().map { line ->
    val cardsAndNumbers = line.split("|")
    val card = Card(cardsAndNumbers[0].split(":")[1].trim().replace("  ", " ").split(" ").map { it.toInt() })
    val winningNumbers = NumbersIHave(cardsAndNumbers[1].trim().replace("  ", " ").split(" ").map { it.toInt() })
    Pair(card, winningNumbers)
}
class Card(val winningNumbers: List<Int>) {

    override fun equals(other: Any?): Boolean {
        if (other !is Card) return false

        return this.winningNumbers == other.winningNumbers
    }

    override fun hashCode(): Int = winningNumbers.hashCode()
    override fun toString(): String {
        return winningNumbers.toString()
    }
}
class NumbersIHave(val numbers: List<Int>) {
    override fun equals(other: Any?): Boolean {
        if (other !is NumbersIHave) return false

        return this.numbers == other.numbers
    }

    override fun hashCode(): Int = numbers.hashCode()
    override fun toString(): String {
        return numbers.toString()
    }
}

fun List<Pair<Card, NumbersIHave>>.getWinnersPerCard() =
    map { (card, numbersIHave) ->
        numbersIHave.numbers.filter { card.winningNumbers.contains(it) }
    }

fun List<List<Int>>.calculatePointsPerCard(): List<Int> {
    return map {
        var result = 1
        if(it.isEmpty()) {
            result = 0
        } else {
            result = 1
            repeat(it.size - 1) {
                result *= 2
            }
        }
        result
    }
}