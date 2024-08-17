package org.example

val s1 = listOf("ნული", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა", "ათი")
val s2 = listOf("თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")
val s3 = listOf("ორ", "სამ", "ოთხ", "ხუთ", "ექვს", "შვიდ", "რვა", "ცხრა")
val s4 = listOf("ათი", "ოცი", "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი")
val s5 = listOf("ოცდა", "ორმოცდა", "სამოცდა", "ოთხმოცდა")
val s6 = listOf("ას", "ორას", "სამას", "ოთხას", "ხუთას", "ექვსას", "შვიდას", "რვაას", "ცხრაას")
val s7 = listOf("ათას", "მილიონ", "მილიარდ", "ტრილიონ")
val s8 = listOf("ი")

fun twoDigitNumber(units: Int, tens: Int): String {
    val number = tens * 10 + units
    return when {
        number in 11..19 -> s2[number % 10 - 1]
        units == 0 -> s4[tens - 1]
        else -> {
            val prefix = when (tens) {
                in 2..3 -> s5[0]
                in 4..5 -> s5[1]
                in 6..7 -> s5[2]
                else -> s5[3]
            }
            val suffix = if (tens % 2 == 0) s1[units] else s2[units - 1]
            prefix + suffix
        }
    }
}

fun threeDigitNumber(units: Int, tens: Int, hundreds: Int): String {
    val hundredText = s6[hundreds - 1]
    return when {
        tens == 0 && units == 0 -> hundredText + s8[0]
        tens == 0 -> hundredText + " " + s1[units]
        else -> hundredText + " " + twoDigitNumber(units, tens)
    }
}

fun otherNumber(number: Long): String {
    val length = number.toString().length
    val units = number % 10
    val tens = (number / 10) % 10
    val hundreds = (number / 100) % 10

    return when (length) {
        1 -> s1[units.toInt()]
        2 -> twoDigitNumber(units.toInt(), tens.toInt())
        3 -> threeDigitNumber(units.toInt(), tens.toInt(), hundreds.toInt())
        else -> ""
    }
}

fun numberToText(number: Long): String {
    val numberString = number.toString()
    val groups = mutableListOf<String>()

    for ((k, i) in (numberString.length downTo 1 step 3).withIndex()) {
        val start = maxOf(0, i - 3)
        val currentThreeNumber = numberString.substring(start, i).toLong()
        if (currentThreeNumber > 0) {
            val n = otherNumber(currentThreeNumber)
            val sep = if (k in 1..4) s7[k - 1] else ""
            groups.add("$n $sep")
        }
    }

    return groups.reversed().joinToString(" ")
}

fun main() {
    val number = 999999

    val text = numberToText(number.toLong())

    println(text)
}
