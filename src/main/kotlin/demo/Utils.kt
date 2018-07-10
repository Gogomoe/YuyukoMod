package demo

import kotlin.math.floor

fun <E> List<E>.getRandom(): E? {
    if (this.isEmpty()) {
        return null
    }
    return this[floor(Math.random() * size).toInt()]
}