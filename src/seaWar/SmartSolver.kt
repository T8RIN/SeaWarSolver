package seaWar

import kotlin.random.Random

class SmartSolver : SeaWarSolver {
    override val name: String = "Умный"

    private val moves = mutableSetOf<Point>()
    private val hitStack = mutableListOf<Point>()
    private val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    override fun makeMove(): Point {
        if (hitStack.isNotEmpty()) {
            val lastHit = hitStack.last()
            for (direction in directions) {
                val nextMove = Point(
                    x = lastHit.x + direction.first,
                    y = lastHit.y + direction.second
                )
                if (isValidMove(nextMove)) {
                    moves.add(nextMove)
                    return nextMove
                }
            }
            hitStack.removeAt(hitStack.size - 1)
        }
        var move: Point
        do {
            move = Point(
                x = Random.nextInt(10),
                y = Random.nextInt(10)
            )
        } while (move in moves)
        moves.add(move)
        return move
    }

    override fun registerHit(point: Point, isHit: Boolean) {
        if (isHit) hitStack.add(point)
    }

    private fun isValidMove(move: Point): Boolean {
        val (x, y) = move
        return x in 0..9 && y in 0..9 && move !in moves
    }
}