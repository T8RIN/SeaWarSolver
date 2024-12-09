package seaWar

import kotlin.random.Random

class RandomSolver : SeaWarSolver {
    override val name: String = "Случайный"

    private val moves = mutableSetOf<Point>()

    override fun makeMove(): Point {
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

    override fun registerHit(point: Point, isHit: Boolean) {}
}