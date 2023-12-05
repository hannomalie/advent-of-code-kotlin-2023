import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

class Day04Test {

    @Test
    fun `cards and winning numbers are parsed correctly`() {
        val cardsAndNumbersIHave = """
            |Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            |Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            |Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            |Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            |Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            |Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimMargin().trim().parseCardsAndWinningNumbers()

        val cards = cardsAndNumbersIHave.map { it.first }
        assertThat(cards).isEqualTo(
            listOf(
                Card(listOf(41, 48, 83, 86, 17,)),
                Card(listOf(13, 32, 20, 16, 61,)),
                Card(listOf(1, 21, 53, 59, 44,)),
                Card(listOf(41, 92, 73, 84, 69,)),
                Card(listOf(87, 83, 26, 28, 32,)),
                Card(listOf(31, 18, 13, 56, 72,)),
            )
        )
        val numbersIHave = cardsAndNumbersIHave.map { it.second }
        assertThat(numbersIHave).isEqualTo(
            listOf(
                NumbersIHave(listOf(83, 86 , 6, 31, 17,  9, 48, 53)),
                NumbersIHave(listOf(61, 30, 68, 82, 17, 32, 24, 19)),
                NumbersIHave(listOf(69, 82, 63, 72, 16, 21, 14,  1)),
                NumbersIHave(listOf(59, 84, 76, 51, 58,  5, 54, 83)),
                NumbersIHave(listOf(88, 30, 70, 12, 93, 22, 82, 36)),
                NumbersIHave(listOf(74, 77, 10, 23, 35, 67, 36, 11)),
            )
        )

        val winnersPerCard = cardsAndNumbersIHave.getWinnersPerCard()
        assertThat(winnersPerCard[0]).isEqualTo(listOf(83, 86, 17, 48))

        val pointsPerCard = winnersPerCard.calculatePointsPerCard()
        assertThat(pointsPerCard[0]).isEqualTo(8)
        assertThat(pointsPerCard[1]).isEqualTo(2)
        assertThat(pointsPerCard[2]).isEqualTo(2)
        assertThat(pointsPerCard[3]).isEqualTo(1)
        assertThat(pointsPerCard[4]).isEqualTo(0)
        assertThat(pointsPerCard[5]).isEqualTo(0)

        assertThat(pointsPerCard.sum()).isEqualTo(13)


        val pile = winnersPerCard.calculatePile()

        assertThat(pile[0].get()).isEqualTo(1)
        assertThat(pile[1].get()).isEqualTo(2)
        assertThat(pile[2].get()).isEqualTo(4)
        assertThat(pile[3].get()).isEqualTo(8)
        assertThat(pile[4].get()).isEqualTo(14)
        assertThat(pile[5].get()).isEqualTo(1)

        assertThat(pile.sumOf { it.get() }).isEqualTo(30)

    }
}
