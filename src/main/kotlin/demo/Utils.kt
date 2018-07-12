package demo

import java.util.Random


private val rng = Random()

fun <E> List<E>.getRandom(): E? {
    if (this.isEmpty()) {
        return null
    }
    return this[rng.nextInt(size)]
}

fun randomInt(range: Int): Int = rng.nextInt(range)