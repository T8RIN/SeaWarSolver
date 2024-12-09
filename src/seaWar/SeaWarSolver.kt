package seaWar

sealed interface SeaWarSolver {
    val name: String
    fun makeMove(): Point
    fun registerHit(point: Point, isHit: Boolean)
}