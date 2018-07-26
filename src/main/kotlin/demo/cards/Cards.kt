package demo.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import demo.cards.yuyuko.ButterfliesLoveFlowers
import demo.cards.yuyuko.ButterfliesRainbow
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.ButterflyDeepRooted
import demo.cards.yuyuko.ButterflyDelusion
import demo.cards.yuyuko.ButterflyGhost
import demo.cards.yuyuko.ButterflySwallowtail
import demo.cards.yuyuko.DyingButterflies
import demo.cards.yuyuko.Explore
import demo.cards.yuyuko.Photo
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

fun AbstractCard.triggerOnDiscard() {
    val func = CardProperty
            .get<AbstractCard.() -> Unit>("${this.cardID}:triggerOnDiscard") ?: return
    this.func()
}

private val ButterflyIDS = listOf(
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID,

        ButterfliesRainbow.ID, ButterfliesLoveFlowers.ID, DyingButterflies.ID
)
private val SakuraIDS = listOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID,

        ButterfliesLoveFlowers.ID
)
private val SpecialButterflyIDS = listOf(
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID
)
private val SpecialSakuraIDS = listOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID
)
private val SpecialCardIDS = listOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID,
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID,
        Explore.ID, Photo.ID
)

fun AbstractCard.isButterfly(): Boolean = this.cardID in ButterflyIDS
fun AbstractCard.isSakura(): Boolean = this.cardID in SakuraIDS

fun AbstractCard.isSpecialButterfly(): Boolean = this.cardID in SpecialButterflyIDS
fun AbstractCard.isSpecialSakura(): Boolean = this.cardID in SpecialSakuraIDS

fun AbstractCard.isSpecial(): Boolean = this.cardID in SpecialCardIDS