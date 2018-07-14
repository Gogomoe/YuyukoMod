package demo.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.ButterflyDeepRooted
import demo.cards.yuyuko.ButterflyDelusion
import demo.cards.yuyuko.ButterflyGhost
import demo.cards.yuyuko.ButterflySwallowtail
import demo.cards.yuyuko.Sakura
import demo.cards.yuyuko.SakuraBloom
import demo.cards.yuyuko.SakuraDormancy
import demo.cards.yuyuko.SakuraSeal
import demo.cards.yuyuko.SakuraSuicide

@Suppress("UNCHECKED_CAST")
object CardProperty {
    private val map = mutableMapOf<String, Any>()
    fun <T : Any> put(id: String, value: T) {
        map[id] = value
    }

    fun <T : Any> get(id: String): T? = map[id] as T?
}

fun AbstractCard.triggerOnDiscard(isEndTurn: Boolean) {
    val func = CardProperty
            .get<AbstractCard.(isEndTurn: Boolean) -> Unit>("${this.cardID}:triggerOnDiscard") ?: return
    this.func(isEndTurn)
}

private val ButterflyIDS = listOf(
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID
)
private val SakuraIDS = listOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID
)

fun AbstractCard.isButterfly(): Boolean = this.cardID in ButterflyIDS
fun AbstractCard.isSakura(): Boolean = this.cardID in SakuraIDS