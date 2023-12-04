import java.io.File

sealed interface ColorDraw {
    val count: Int
    data class Red(override val count: Int): ColorDraw
    data class Green(override val count: Int): ColorDraw
    data class Blue(override val count: Int): ColorDraw
}
typealias Draw = List<ColorDraw>
data class Game(val index: Int, val draws: List<Draw>)

fun main() {
    run {
        val games = File("src/Day02_test.txt").parseGames()

        val sumOfPossibleGames = games.filter { it.isPossible }.sumOf { it.index }
        check(sumOfPossibleGames == 8)

        val minimumSets = games.map { it.minimumSet }
        check(minimumSets.first().red.count == 4)
        check(minimumSets.first().green.count == 2)
        check(minimumSets.first().blue.count == 6)
        check(minimumSets.first().power == 48)

        check(minimumSets[1].red.count == 1)
        check(minimumSets[1].green.count == 3)
        check(minimumSets[1].blue.count == 4)
        check(minimumSets[1].power == 12)

        check(minimumSets[2].red.count == 20)
        check(minimumSets[2].green.count == 13)
        check(minimumSets[2].blue.count == 6)
        check(minimumSets[2].power == 1560)

        check(minimumSets[3].red.count == 14)
        check(minimumSets[3].green.count == 3)
        check(minimumSets[3].blue.count == 15)
        check(minimumSets[3].power == 630)

        check(minimumSets[4].red.count == 6)
        check(minimumSets[4].green.count == 3)
        check(minimumSets[4].blue.count == 2)
        check(minimumSets[4].power == 36)

        check(minimumSets.sumOf { it.power } == 2286)
    }
    run {
        val input = File("src/Day02.txt")
        println("Result Part 1:")
        val games = input.parseGames()
        println(games.filter {it.isPossible }.sumOf { it.index })
        println("Result Part 2:")
        println(games.sumOf { it.minimumSet.power })
    }
}

private fun File.parseGames() = readLines().map { line ->
    val draws = line.split(":")[1].split(";").map { draws ->
        val typedDraws = draws.split(",").map { draw ->
            val count = draw.replace("red", "")
                .replace("green", "")
                .replace("blue", "").trim().toInt()
            val draw = when {
                draw.contains("green") -> ColorDraw.Green(count)
                draw.contains("red") -> ColorDraw.Red(count)
                draw.contains("blue") -> ColorDraw.Blue(count)
                else -> throw IllegalStateException("Draw can't be processed: $draw")
            }
            draw
        }
        typedDraws
    }
    Game(line.gameNumber, draws)
}

private val MinimumSet.power: Int get() = this.red.count * this.green.count * blue.count
private val Game.minimumSet: MinimumSet
    get() {
        val redDraws = draws.flatMap { it.filterIsInstance<ColorDraw.Red>() }
        val greenDraws = draws.flatMap { it.filterIsInstance<ColorDraw.Green>() }
        val blueDraws = draws.flatMap { it.filterIsInstance<ColorDraw.Blue>() }
        return MinimumSet(redDraws.maxBy { it.count }, greenDraws.maxBy { it.count }, blueDraws.maxBy { it.count })
    }
data class MinimumSet(val red: ColorDraw.Red, val green: ColorDraw.Green, val blue: ColorDraw.Blue)
private val Game.isPossible get() = draws.all { draw -> draw.all { it.isPossible } }

private val ColorDraw.isPossible: Boolean
    get() = when(this) {
        is ColorDraw.Blue -> count <= 14
        is ColorDraw.Green -> count <= 13
        is ColorDraw.Red -> count <= 12
    }
private val String.gameNumber get() = split(":").first().substring(4).trim().toInt()
