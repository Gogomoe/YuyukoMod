package yuyuko

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType
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

fun CardGroup.addToRandomSpotIfIsDrawPile(card: AbstractCard) {
    when (this.type) {
        CardGroupType.DRAW_PILE -> this.addToRandomSpot(card)
        else -> this.addToTop(card)
    }
}

inline fun <S, T> Iterable<T>.reduce(start: S, operation: (acc: S, T) -> S): S {
    val iterator = this.iterator()
    if (!iterator.hasNext()) throw UnsupportedOperationException("Empty collection can't be reduced.")
    var accumulator: S = start
    while (iterator.hasNext()) {
        accumulator = operation(accumulator, iterator.next())
    }
    return accumulator
}
