package seaWar

import kotlin.random.Random

class SeaBattleGame(
    private val firstPlayer: SeaWarSolver,
    private val secondPlayer: SeaWarSolver
) {
    private val firstPlayerBoard = Board()
    private val secondPlayerBoard = Board()
    private val firstPlayerShips = mutableSetOf<Point>()
    private val secondPlayerShips = mutableSetOf<Point>()
    private val alphabetHeader = "    А Б В Г Д  Е Ж З И К"

    init {
        placeShips(firstPlayerShips, firstPlayerBoard)
        placeShips(secondPlayerShips, secondPlayerBoard)
    }

    private fun placeShips(ships: MutableSet<Point>, board: Board) {
        val shipSizes = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)

        for (size in shipSizes) {
            var placed = false
            while (!placed) {
                val isHorizontal = Random.nextBoolean()
                val startX = Random.nextInt(10)
                val startY = Random.nextInt(10)

                val positions = mutableListOf<Point>()
                for (i in 0 until size) {
                    val x = if (isHorizontal) startX else startX + i
                    val y = if (isHorizontal) startY + i else startY
                    if (x !in 0..9 || y !in 0..9 || Point(x, y) in ships) break
                    positions.add(Point(x, y))
                }

                if (positions.size == size && positions.all { isValidPlacement(it, ships) }) {
                    positions.forEach {
                        ships.add(it)
                        board[it.x][it.y] = "🚢"
                    }
                    placed = true
                }
            }
        }
    }

    private fun isValidPlacement(position: Point, ships: Set<Point>): Boolean {
        val (x, y) = position
        val neighbors = listOf(
            Point(x = x - 1, y = y),
            Point(x = x + 1, y = y),
            Point(x = x, y = y - 1),
            Point(x = x, y = y + 1),
            Point(x = x - 1, y = y - 1),
            Point(x = x - 1, y = y + 1),
            Point(x = x + 1, y = y - 1),
            Point(x = x + 1, y = y + 1)
        )
        return neighbors.none { it in ships }
    }

    private fun printField(board: Board) {
        println(alphabetHeader)
        board.forEachIndexed { i, row ->
            println("${i + 1}".padStart(2) + " " + row.joinToString(""))
        }
    }

    fun start(showLogs: Boolean): GameResult {
        var turn = 0
        while (firstPlayerShips.isNotEmpty() && secondPlayerShips.isNotEmpty()) {
            if (showLogs) {
                println("\nХод ${turn + 1}")
                println("Поле игрока ${firstPlayer.name}:")
                printField(firstPlayerBoard)
                println("Поле игрока ${secondPlayer.name}:")
                printField(secondPlayerBoard)
            }

            val move1 = firstPlayer.makeMove()
            val hit1 = move1 in secondPlayerShips
            if (hit1) {
                secondPlayerShips.remove(move1)
                secondPlayerBoard[move1.x][move1.y] = "💥"
            } else {
                secondPlayerBoard[move1.x][move1.y] = "🕳️"
            }
            firstPlayer.registerHit(move1, hit1)

            if (secondPlayerShips.isEmpty()) break

            val move2 = secondPlayer.makeMove()
            val hit2 = move2 in firstPlayerShips
            if (hit2) {
                firstPlayerShips.remove(move2)
                firstPlayerBoard[move2.x][move2.y] = "💥"
            } else {
                firstPlayerBoard[move2.x][move2.y] = "🕳️"
            }
            secondPlayer.registerHit(move2, hit2)

            turn++
        }

        if (showLogs) {
            println("\nИгра окончена!")
        }
        var winner: SeaWarSolver? = null
        when {
            firstPlayerShips.isEmpty() -> {
                if (showLogs) {
                    println("Победил игрок ${firstPlayer.name} за ${turn + 1} ходов!")
                }
                winner = firstPlayer
            }

            secondPlayerShips.isEmpty() -> {
                if (showLogs) {
                    println("Победил игрок ${secondPlayer.name} за ${turn + 1} ходов!")
                }
                winner = secondPlayer
            }
        }

        @Suppress("KotlinConstantConditions")
        if (showLogs && winner == null) {
            println("Ничья за ${turn + 1} ходов!")
        }
        return GameResult(
            winner = winner,
            turns = turn + 1
        )
    }
}