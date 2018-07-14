package demo

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

fun CardGroup.addToRandomSpotIfIsDrawPile(card: AbstractCard) {
    if (this.type == CardGroupType.DRAW_PILE) {
        this.addToRandomSpot(card)
    } else {
        this.addToBottom(card)
    }
}