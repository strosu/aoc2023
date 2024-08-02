package org.example

import kotlin.math.pow

class Problem4 {
    fun solve2(lines: List<String>): Int {
        var sum = 0
        val cards = lines.map { getCard(it) }
        val map = cards.map { it.id to 1 }.toMap().toMutableMap()

        for (card in cards) {
            sum += map[card.id]!!
            val wonCards = getFollowup(card, cards)
            wonCards.forEach {
                map[it] = map[it]!! + map[card.id]!!
            }
        }

        return sum
    }

    fun getFollowup(card: Card, allCards: List<Card>): List<Int> {
        val points = card.getNextCards()
        if (points == 0) {
            return listOf()
        }

        val result = mutableListOf<Int>()
        for (i in 1..points) {
            result.add(card.id + i)
        }

        return result
    }

    fun solve1(lines: List<String>): Int {
        val cards = lines.map { getCard(it) }
        return cards.sumOf { it.getPoints() }
    }

    fun getCard(line: String): Card {
        val parts = line.split(":")
        val id = parts[0].trim().split(" ").filter { it.isNotBlank() }[1].toInt()
        val numbers = getAllNumbers(parts[1])
        return Card(id, numbers.first, numbers.second)
    }

    fun getAllNumbers(line: String): Pair<HashSet<Int>, HashSet<Int>> {
        val parts = line.split("|")
        return Pair(getNumbers(parts[0]), getNumbers(parts[1]))
    }

    fun getNumbers(line: String): HashSet<Int> {
        val values = line.trim().split(" ").filter { it.isNotBlank() }
        return values.asSequence().map { it.toInt() }.toHashSet()
    }

    data class Card(
        val id: Int,
        val wining: HashSet<Int>,
        val drawn: HashSet<Int>
    ) {
        fun getPoints(): Int {
            val goodNumbers = drawn.filter { wining.contains(it) }
            if (goodNumbers.isEmpty()) {
                return 0
            }

            return 2.0.pow(goodNumbers.count().toDouble() - 1).toInt()
        }

        fun getNextCards(): Int {
            val goodNumbers = drawn.filter { wining.contains(it) }
            return goodNumbers.count()
        }
    }
}