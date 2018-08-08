package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.DRAW_PILE
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class RetrieveAction(val condition: (AbstractCard) -> Boolean, val count: Int = 1) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        val player = AbstractDungeon.player
        val groups = listOf(player.drawPile, player.discardPile)

        repeat(count) {
            for (group in groups) {

                val list = if (group.type == DRAW_PILE) {
                    group.group.reversed()
                } else {
                    group.group
                }

                val card = list.find(condition)

                if (card != null) {
                    group.moveToHand(card, group)
                    break
                }
            }
        }
        this.isDone = true
    }

}