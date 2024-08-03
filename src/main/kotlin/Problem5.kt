package aoc2023

class Problem5 {

    fun solve2(input: String): Long {
        val sections = input.split("\n\n")
        val seeds = getSeeds2(sections[0])
        seedToSoil = getMapCollection(sections[1])
        soilToFertilizer = getMapCollection(sections[2])
        fertilizerToWater = getMapCollection(sections[3])
        waterToLight = getMapCollection(sections[4])
        lightToTemperature = getMapCollection(sections[5])
        temperatureToHumidity = getMapCollection(sections[6])
        humidityToLocation = getMapCollection(sections[7])

        val first = doStep(seeds, seedToSoil)
        val second = doStep(first, soilToFertilizer)
        val third = doStep(second, fertilizerToWater)
        val fourth = doStep(third, waterToLight)
        val fifth = doStep(fourth, lightToTemperature)
        val sixth = doStep(fifth, temperatureToHumidity)
        val seventh = doStep(sixth, humidityToLocation)

        return seventh.minOf { it.start }
    }

    fun doStep(input: List<Interval>, map: MapCollection): List<Interval> {
        val result = mutableListOf<Interval>()

        input.forEach {
            result.addAll(map.intersectAll(it))
        }

        return result
    }

    fun getSeeds2(line: String): List<Interval> {
        val values = getSeeds(line)
        val result = mutableListOf<Interval>()
        for (i in 0..<values.lastIndex step 2) {
            result.add(Interval(values[i], values[i] + values[i + 1] - 1))
        }

        return result
    }


    fun solve(input: String): Long {
        val sections = input.split("\n\n")
        val seeds = getSeeds(sections[0])
        seedToSoil = getMapCollection(sections[1])
        soilToFertilizer = getMapCollection(sections[2])
        fertilizerToWater = getMapCollection(sections[3])
        waterToLight = getMapCollection(sections[4])
        lightToTemperature = getMapCollection(sections[5])
        temperatureToHumidity = getMapCollection(sections[6])
        humidityToLocation = getMapCollection(sections[7])

        var min = Long.MAX_VALUE

        seeds.forEach {
            val soil = seedToSoil.getCorresponding(it)
            val fert = soilToFertilizer.getCorresponding(soil)
            val water = fertilizerToWater.getCorresponding(fert)
            val light = waterToLight.getCorresponding(water)
            val temp = lightToTemperature.getCorresponding(light)
            val hum = temperatureToHumidity.getCorresponding(temp)
            val location = humidityToLocation.getCorresponding(hum)

            min = minOf(min, location)
        }

        return min
    }

    fun getSeeds(line: String): List<Long> {
        return line.split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
    }

    fun getMapCollection(input: String): MapCollection {
        val lines = input.split("\n")
        val result = MapCollection()

        for (i in 1..lines.lastIndex) {
            val values = lines[i].split(" ")
            val interval = MapInterval(Interval(values[1].toLong(), values[1].toLong() + values[2].toLong() - 1), values[0].toLong() - values[1].toLong())
            result.intervals.add(interval)
        }

        return result
    }

    var seedToSoil = MapCollection()
    var soilToFertilizer = MapCollection()
    var fertilizerToWater = MapCollection()
    var waterToLight = MapCollection()
    var lightToTemperature = MapCollection()
    var temperatureToHumidity = MapCollection()
    var humidityToLocation = MapCollection()

    data class MapCollection(
        val intervals: MutableList<MapInterval> = mutableListOf()
    ) {
        fun getCorresponding(value: Long): Long {

            return intervals.firstNotNullOfOrNull { it.getCorresponding(value) } ?: value
        }

        fun intersectAll(otherInterval: Interval): List<Interval> {
            val results = mutableListOf<Interval>()
            var toIntersect = listOf(otherInterval)

            for (interval in intervals) {
                val newList = mutableListOf<Interval>()

                for (current in toIntersect) {
                    val currentResult = interval.intersect(current)
                    if (currentResult.matched != null) {
                        results.add(currentResult.matched)
                    }

                    newList.addAll(currentResult.outside)
                }

                toIntersect = newList.toList()
            }

            return results + toIntersect
        }
    }

    data class MapInterval(
        val interval: Interval,
        val offset: Long
    ) {
        fun getCorresponding(value: Long): Long? {
            if (value !in interval.start..interval.end) {
                return null
            }

            return value + offset
        }

        fun intersect(otherInterval: Interval): IntersectionResult {
            // No overlap
            if (interval.end < otherInterval.start || interval.start > otherInterval.end) {
                return IntersectionResult(outside = listOf(otherInterval))
            }

            // otherInterval fully within
            if (otherInterval.start >= interval.start && otherInterval.end <= interval.end) {
                return IntersectionResult(matched = Interval(otherInterval.start + offset, otherInterval.end + offset))
            }

            // internal interval fully within
            if (otherInterval.start < interval.start && otherInterval.end > interval.end) {
                return IntersectionResult(
                    matched = Interval(interval.start + offset, interval.end + offset),
                    outside = listOf(Interval(otherInterval.start, interval.start - 1), Interval(interval.end + 1, otherInterval.end)))
            }

            if (otherInterval.start < interval.start) {
                return IntersectionResult(
                    matched = Interval(interval.start + offset, otherInterval.end + offset),
                    outside = listOf(Interval(otherInterval.start, interval.start - 1))
                )
            }

            return IntersectionResult(
                matched = Interval(otherInterval.start + offset, interval.end + offset),
                outside = listOf(Interval(interval.end + 1, otherInterval.end))
            )
        }
    }

    data class IntersectionResult(
        val matched: Interval? = null,
        val outside: List<Interval> = listOf()
    )

    data class Interval(
        val start: Long,
        val end: Long
    )
}