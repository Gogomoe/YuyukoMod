package demo.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.yuyuko.BuryInTheTemplate
import demo.cards.yuyuko.DyingDream
import demo.cards.yuyuko.FinalOfFinal
import demo.cards.yuyuko.FullInkySakura
import demo.cards.yuyuko.InfiniteSin
import demo.cards.yuyuko.MonsterCherryTree
import demo.cards.yuyuko.RemainHere
import demo.cards.yuyuko.ReverseTheScreen
import demo.cards.yuyuko.SceneryOfPapilio
import demo.cards.yuyuko.SereneSpring

private val hideCards = mapOf(
        ReverseTheScreen.ID to setOf(true),
        DyingDream.ID to setOf(true),
        InfiniteSin.ID to setOf(true),
        MonsterCherryTree.ID to setOf(true, false),
        RemainHere.ID to setOf(false),
        FullInkySakura.ID to setOf(false),
        BuryInTheTemplate.ID to setOf(true, false),
        SceneryOfPapilio.ID to setOf(true),
        FinalOfFinal.ID to setOf(true),
        SereneSpring.ID to setOf(true, false)
)

fun AbstractCard.isHide(): Boolean = this.upgraded in hideCards[this.cardID] ?: emptySet()


object HideCards {

    fun shouldHide(): Boolean {
        val drawPile = AbstractDungeon.player.drawPile.group
        val remain = drawPile.count { it.isHide() }
        return drawPile.size != remain
    }

}

