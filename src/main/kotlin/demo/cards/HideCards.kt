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

fun AbstractCard.isHide(): Boolean =
        when {
            this.cardID == ReverseTheScreen.ID && this.upgraded -> true
            this.cardID == DyingDream.ID && this.upgraded -> true
            this.cardID == InfiniteSin.ID && this.upgraded -> true
            this.cardID == MonsterCherryTree.ID -> true
            this.cardID == RemainHere.ID && !this.upgraded -> true
            this.cardID == FullInkySakura.ID && !this.upgraded -> true
            this.cardID == BuryInTheTemplate.ID -> true
            this.cardID == SceneryOfPapilio.ID && this.upgraded -> true
            this.cardID == FinalOfFinal.ID && this.upgraded -> true
            this.cardID == SereneSpring.ID -> true
            else -> false
        }

object HideCards {

    fun shouldHide(): Boolean {
        val drawPile = AbstractDungeon.player.drawPile.group
        val remain = drawPile.count { it.isHide() }
        return drawPile.size != remain
    }

}

