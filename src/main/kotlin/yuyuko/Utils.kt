package yuyuko

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType
import com.megacrit.cardcrawl.core.AbstractCreature
import java.util.Random


private val rng = Random()

fun <E> List<E>.getRandom(): E? {
    if (this.isEmpty()) {
        return null
    }
    return this[rng.nextInt(size)]
}

fun randomInt(range: Int): Int = rng.nextInt(range)

fun randomBoolean(rarity: Float): Boolean = rng.nextFloat() <= rarity


inline fun <S, T> Iterable<T>.reduce(start: S, operation: (acc: S, T) -> S): S {
    val iterator = this.iterator()
    if (!iterator.hasNext()) throw UnsupportedOperationException("Empty collection can't be reduced.")
    var accumulator: S = start
    while (iterator.hasNext()) {
        accumulator = operation(accumulator, iterator.next())
    }
    return accumulator
}

inline fun <S> Iterable<Iterable<out S>>.collect(): List<S> = mutableListOf<S>().apply {
    this@collect.forEach {
        this.addAll(it)
    }
}

inline fun CardGroup.addToRandomSpotIfIsDrawPile(card: AbstractCard) {
    when (this.type) {
        CardGroupType.DRAW_PILE -> this.addToRandomSpot(card)
        else -> this.addToTop(card)
    }
}

inline fun AbstractCreature.hasEnoughPower(powerID: String, amount: Int = 1): Boolean =
        this.getPower(powerID)?.amount ?: 0 >= amount

inline fun AbstractCard.setCantUseMessage(canPlay: Boolean, message: String) {
    if (!canPlay) {
        this.cantUseMessage = message
    }
}