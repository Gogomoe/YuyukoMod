package yuyuko.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.cards.yuyuko.BuryInTheTemplate
import yuyuko.cards.yuyuko.DyingDream
import yuyuko.cards.yuyuko.FinalOfFinal
import yuyuko.cards.yuyuko.FullInkySakura
import yuyuko.cards.yuyuko.InfiniteSin
import yuyuko.cards.yuyuko.MonsterCherryTree
import yuyuko.cards.yuyuko.RemainHere
import yuyuko.cards.yuyuko.ReverseTheScreen
import yuyuko.cards.yuyuko.SceneryOfPapilio
import yuyuko.cards.yuyuko.SereneSpring

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

