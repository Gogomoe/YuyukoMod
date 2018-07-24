package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class HideAction(val card: AbstractCard) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
    }

    override fun update() {
        val player = AbstractDungeon.player
        player.hand.removeCard(card)
        player.drawPile.addToBottom(card)
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(player, 1)
        )
        this.isDone = true
    }

}
