package seaWar

fun main() {
    val smartVsRandom = mutableListOf<GameResult>()

    SeaBattleGame(
        firstPlayer = SmartSolver(),
        secondPlayer = RandomSolver()
    ).start(showLogs = true)

    repeat(100) {
        SeaBattleGame(
            firstPlayer = SmartSolver(),
            secondPlayer = RandomSolver()
        ).start(showLogs = false).also(smartVsRandom::add)
    }

    val smart = smartVsRandom.filter { it.winner is SmartSolver }
    val random = smartVsRandom.filter { it.winner is RandomSolver }

    println("\nРезультаты 100 игр")
    println("Умный = ${smart.size}, avrg = ${smart.sumOf { it.turns } / smart.size}")
    println("Случайный = ${random.size}, avrg = ${random.sumOf { it.turns } / random.size}")
}