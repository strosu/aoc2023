package org.example

class Problem1 {
    val map = mapOf("one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9)

    fun compute1(input: List<String>): Int {
        return input.sumOf { getValue(it) }
    }

    fun compute2(input: List<String>): Int {
        return input.sumOf { getValue2(it) }
    }

    fun getValue2(line: String): Int {
        var first: Int? = null
        var last = 0

        for (i in line.indices) {
            if (line[i].isDigit()) {
                if (first == null) {
                    first = line[i] - '0'
                }

                last = line[i] - '0'
            }

            val firstMatch = map.keys.firstOrNull { line.substring(i).startsWith(it) }
            if (firstMatch != null) {
                if (first == null) {
                    first = map[firstMatch]
                }

                last = map[firstMatch]!!
            }
        }

        return first!! * 10 + last
    }

    private fun getValue(line: String): Int {
        var first: Int? = null
        var last = 0
        for (i in line) {
            if (i.isDigit()) {
                if (first == null) {
                    first = i - '0'
                }

                last = i - '0'
            }
        }

        return first!! * 10 + last
    }
}
