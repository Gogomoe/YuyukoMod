package yuyuko.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import yuyuko.cards.yuyuko.ButterfliesInDream
import yuyuko.cards.yuyuko.ButterfliesRainbow
import yuyuko.cards.yuyuko.Butterfly
import yuyuko.cards.yuyuko.ButterflyDeepRooted
import yuyuko.cards.yuyuko.ButterflyDelusion
import yuyuko.cards.yuyuko.ButterflyGhost
import yuyuko.cards.yuyuko.ButterflySwallowtail
import yuyuko.cards.yuyuko.CerasusSubhirtella
import yuyuko.cards.yuyuko.DyingButterflies
import yuyuko.cards.yuyuko.DyingSakura
import yuyuko.cards.yuyuko.Explore
import yuyuko.cards.yuyuko.LingerOverFlower
import yuyuko.cards.yuyuko.Photo
import yuyuko.cards.yuyuko.Sakura
import yuyuko.cards.yuyuko.SakuraBloom
import yuyuko.cards.yuyuko.SakuraDormancy
import yuyuko.cards.yuyuko.SakuraSeal
import yuyuko.cards.yuyuko.SakuraSuicide
import yuyuko.cards.yuyuko.Soul
import yuyuko.cards.yuyuko.TheInkySakura
import yuyuko.cards.yuyuko.UnknownPetal

private val ButterflyIDS = setOf(
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID,

        ButterfliesRainbow.ID, LingerOverFlower.ID, DyingButterflies.ID,
        ButterfliesInDream.ID
)
private val SakuraIDS = setOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID,

        LingerOverFlower.ID, UnknownPetal.ID, TheInkySakura.ID,
        CerasusSubhirtella.ID, DyingSakura.ID
)
private val SpecialButterflyIDS = setOf(
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID
)
private val SpecialSakuraIDS = setOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID
)
private val SpecialCardIDS = setOf(
        Sakura.ID, SakuraBloom.ID, SakuraDormancy.ID,
        SakuraSeal.ID, SakuraSuicide.ID,
        Butterfly.ID, ButterflySwallowtail.ID, ButterflyGhost.ID,
        ButterflyDeepRooted.ID, ButterflyDelusion.ID,
        Soul.ID, Explore.ID, Photo.ID
)

fun AbstractCard.isButterfly(): Boolean = this.cardID in ButterflyIDS
fun AbstractCard.isSakura(): Boolean = this.cardID in SakuraIDS

fun AbstractCard.isSpecialButterfly(): Boolean = this.cardID in SpecialButterflyIDS
fun AbstractCard.isSpecialSakura(): Boolean = this.cardID in SpecialSakuraIDS

fun AbstractCard.isSpecial(): Boolean = this.cardID in SpecialCardIDS