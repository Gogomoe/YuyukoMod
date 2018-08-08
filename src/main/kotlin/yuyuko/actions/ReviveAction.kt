package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class ReviveAction(val condition: (AbstractCard) -> Boolean) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = CARD_MANIPULATION
    }

    override fun update() {

        val group = AbstractDungeon.player.exhaustPile
        val list = group.group

        val card = list.find(condition)

        if (card != null) {
            card.unfadeOut()
            group.moveToHand(card, group)
        }

        this.isDone = true
    }

}