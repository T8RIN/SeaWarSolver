package seaWar

typealias Board = Array<Array<String>>

fun Board(fillSymbol: String = "🟢"): Board = Array(10) { Array(10) { fillSymbol } }