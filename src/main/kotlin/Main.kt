package org.example

import java.io.File

fun main() {
    solve4()
}

fun solve4() {
    println(
        Problem4().solve1(File("./build/resources/main/4/sample").readLines()))

    println(
        Problem4().solve1(File("./build/resources/main/4/pb").readLines()))

    println(
        Problem4().solve2(File("./build/resources/main/4/sample").readLines()))

    println(
        Problem4().solve2(File("./build/resources/main/4/pb").readLines()))
}


fun solve3() {
    println(
        Problem3().compute2(File("./build/resources/main/3/sample").readLines()))

    println(
        Problem3().compute2(File("./build/resources/main/3/pb").readLines()))

    println(
        Problem3().compute1(File("./build/resources/main/3/sample").readLines()))

    println(
        Problem3().compute1(File("./build/resources/main/3/pb").readLines()))
}


fun solve1() {
    println(
        Problem1().compute1(File("./build/resources/main/1/Problem1Sample.txt").readLines()))

    println(
        Problem1().compute1(File("./build/resources/main/1/Problem1.txt").readLines()))

    println(
        Problem1().compute2(File("./build/resources/main/1/Problem1-2Sample.txt").readLines()))

    println(
        Problem1().compute2(File("./build/resources/main/1/Problem1-2.txt").readLines()))
}

fun solve2() {
    println(
        Problem2().compute(File("./build/resources/main/2/Problem2Sample.txt").readLines()))

    println(
        Problem2().compute(File("./build/resources/main/2/Problem2.txt").readLines()).sum())

    println(
        Problem2().compute2(File("./build/resources/main/2/Problem2Sample.txt").readLines()))

    println(
        Problem2().compute2(File("./build/resources/main/2/Problem2.txt").readLines()))
}