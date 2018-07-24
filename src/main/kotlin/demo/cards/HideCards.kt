package demo.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.yuyuko.DyingDream
import demo.cards.yuyuko.InfiniteSin
import demo.cards.yuyuko.ReverseTheScreen

fun AbstractCard.isHide(): Boolean =
        when {
            this.cardID == ReverseTheScreen.ID && this.upgraded -> true
            this.cardID == DyingDream.ID && this.upgraded -> true
            this.cardID == InfiniteSin.ID && this.upgraded -> true
            else -> false
        }

object HideCards {

    fun shouldHide(): Boolean {
        val drawPile = AbstractDungeon.player.drawPile.group
        val remain = drawPile.count { it.isHide() }
        return drawPile.size != remain
    }

}

