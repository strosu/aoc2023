package org.example

class Problem2 {

    fun compute2(lines:List<String>): Int {
        val games = lines.map { parse(it) }
        return games.sumOf { it.getMinCube() }
    }


    fun compute(lines: List<String>): List<Int> {
        val games = lines.map { parse(it) }
        return games.filter { it.rounds.all {
            round -> round.reds <= 12 && round.greens <= 13 && round.blues <= 14
        } }.map { Integer.parseInt(it.id)
        }
    }

    fun parse(line: String): Game {
        val first = line.split(":")
        val id = first[0].split(" ")[1]

        val rounds = parseRounds(first[1])
        return Game(id = id, rounds = rounds)
    }

    fun parseRounds(games: String): List<GameRound> {
        val strings = games.split(";")
        return strings.map { parseRound(it) }
    }

    fun parseRound(game: String): GameRound {
        var reds = 0
        var greens = 0
        var blues = 0

        val parts = game.split(",")
        parts.forEach {
            if (it.contains("red")) {
                reds = it.trim().split(" ")[0].toInt()
            }
            if (it.contains("green")) {
                greens = it.trim().split(" ")[0].toInt()
            }
            if (it.contains("blue")) {
                blues = it.trim().split(" ")[0].toInt()
            }
        }

        return GameRound(reds, blues, greens)
    }

    data class Game(
        val id: String,
        val rounds: List<GameRound>) {

        fun getMinCube(): Int {
            val maxRed = rounds.maxBy { it.reds }.reds
            val maxBlues = rounds.maxBy { it.blues }.blues
            val maxGreens = rounds.maxBy { it.greens }.greens

            return maxRed * maxBlues * maxGreens
        }
    }

    data class GameRound(
        val reds: Int,
        val blues: Int,
        val greens: Int
    )
}