package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon


class UpgradeAllAction(val condition: (AbstractCard) -> Boolean, val times: Int = 1) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        val p = AbstractDungeon.player
        repeat(times) {
            this.upgradeAllCardsInGroup(p.hand)
            this.upgradeAllCardsInGroup(p.drawPile)
            this.upgradeAllCardsInGroup(p.discardPile)
            this.upgradeAllCardsInGroup(p.exhaustPile)
        }
        this.isDone = true

    }

    private fun upgradeAllCardsInGroup(cardGroup: CardGroup) {
        cardGroup.group
                .filter(condition)
                .forEach { card ->
                    if (card.canUpgrade()) {
                        if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                            card.superFlash()
                        }
                        card.upgrade()
                        card.applyPowers()
                    }
                }

    }
}
