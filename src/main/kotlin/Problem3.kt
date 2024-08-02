package org.example

class Problem3 {

    fun compute2(matrix: List<String>): Int {
        val map = mutableMapOf<Point, MutableList<Number>>()
        for (i in matrix.indices) {
            val line = matrix[i]

            val numbers = getNumbers(line, i)
            numbers.forEach { number ->
                run {
                    val adj = getAdjacentPieces(number, matrix)
                    adj.forEach { part ->
                        run {
                            if (!map.containsKey(part)) {
                                map[part] = mutableListOf(number)
                            } else {
                                map[part]!!.add(number)
                            }
                        }
                    }

                }
            }
        }

        val relevant = map.filter { it.value.size == 2 }

        var sum = 0
        relevant.forEach {
            var product = 1
            it.value.forEach {
                number -> product *= number.value
            }

            sum += product
        }

        return sum
    }


    fun getAdjacentPieces(part: Number, matrix: List<String>): List<Point> {
        val adjacentPoints = getAdjacentPoints(part, matrix)
        return adjacentPoints.filter { matrix[it.x][it.y] == '*' }
    }

    fun getAdjacentPoints(part: Number, matrix: List<String>): MutableList<Point> {
        val result = mutableListOf<Point>()
        for (i in (part.start.x - 1)..(part.start.x + 1)) {
            for (j in (part.start.y - 1)..(part.end.y + 1)) {
                if (Point.isValid(i, j, matrix)) {
                    result.add(Point(i, j))
                }
            }
        }

        return result
    }

    fun compute1(matrix: List<String>): Int {
        var sum = 0
        for (i in matrix.indices) {
            val line = matrix[i]

            val numbers = getNumbers(line, i)
            numbers.forEach {
                if (shouldCount(matrix, it.start, it.end)) {
                    sum += it.value
                }
            }
        }

        return sum
    }

    fun getNumbers(line: String, x: Int): List<Number> {
        val result = mutableListOf<Number>()
        var start = 0
        var end = -1
        var reset = true
        var number = 0

        for (i in line.indices) {
            if (line[i].isDigit()) {
                if (reset) {
                    start = i
                    reset = false
                }

                end = i
                number = number * 10 + (line[i] - '0')
            } else {
                if (!reset) {
                    reset = true
                    result.add(Number(number, Point(x, start), Point(x, end)))
                    number = 0
                }
            }
        }

        if (end == line.length - 1) {
            result.add(Number(number, Point(x, start), Point(x, end)))
        }

        return result
    }

    fun shouldCount(matrix: List<String>, startPosition: Point, endPosition: Point): Boolean {
        return startPosition.checkLeft(matrix) || startPosition.checkTopLeft(matrix) || startPosition.checkBottomLeft(
            matrix
        )
                || endPosition.checkRight(matrix) || endPosition.checkTopRight(matrix) || endPosition.checkBottomRight(
            matrix
        )
                || getRange(startPosition, endPosition).any { it.checkAbove(matrix) || it.checkBelow(matrix) }
    }

    fun getRange(start: Point, end: Point): List<Point> {
        val result = mutableListOf<Point>()
        for (i in start.y..end.y) {
            result.add(Point(start.x, i))
        }

        return result
    }

    data class Number(val value: Int, val start: Point, val end: Point)

    data class Point(val x: Int, val y: Int) {
        fun checkAbove(matrix: List<String>): Boolean = checkAtPosition(x - 1, y, matrix)
        fun checkBelow(matrix: List<String>): Boolean = checkAtPosition(x + 1, y, matrix)
        fun checkLeft(matrix: List<String>): Boolean = checkAtPosition(x, y - 1, matrix)
        fun checkRight(matrix: List<String>): Boolean = checkAtPosition(x, y + 1, matrix)

        fun checkTopLeft(matrix: List<String>): Boolean = checkAtPosition(x - 1, y - 1, matrix)
        fun checkTopRight(matrix: List<String>): Boolean = checkAtPosition(x - 1, y + 1, matrix)

        fun checkBottomLeft(matrix: List<String>): Boolean = checkAtPosition(x + 1, y - 1, matrix)
        fun checkBottomRight(matrix: List<String>): Boolean = checkAtPosition(x + 1, y + 1, matrix)

        companion object {
            fun isSymbol(char: Char): Boolean {
                return !char.isDigit() && char != '.'
            }

            fun isValid(x: Int, y: Int, matrix: List<String>): Boolean {
                return x >= 0 && x < matrix.size
                        && y >= 0 && y < matrix[0].length
            }

            fun checkAtPosition(x: Int, y: Int, matrix: List<String>): Boolean {
                return isValid(x, y, matrix) && isSymbol(matrix[x][y])
            }
        }
    }
}