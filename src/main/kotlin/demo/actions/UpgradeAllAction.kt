package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.isButterfly
import demo.cards.isSakura
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.Sakura


class UpgradeAllAction(val cardID: String) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            val p = AbstractDungeon.player
            this.upgradeAllCardsInGroup(p.hand)
            this.upgradeAllCardsInGroup(p.drawPile)
            this.upgradeAllCardsInGroup(p.discardPile)
            this.upgradeAllCardsInGroup(p.exhaustPile)
            this.isDone = true
        }

    }

    private fun upgradeAllCardsInGroup(cardGroup: CardGroup) {
        cardGroup.group.filter {
            when (cardID) {
                Butterfly.ID -> it.isButterfly()
                Sakura.ID -> it.isSakura()
                else -> it.cardID == cardID
            }
        }.forEach { card ->
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
