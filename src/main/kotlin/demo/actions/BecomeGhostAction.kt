package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.isButterfly

class BecomeGhostAction : AbstractGameAction() {

    override fun update() {
        for (card in AbstractDungeon.player.hand.group) {
            if (card.isButterfly()) {
                AbstractDungeon.actionManager.addToBottom(
                        ExhaustSpecificCardAction(card, AbstractDungeon.player.hand)
                )
                break
            }
        }
        this.isDone = true
    }

}