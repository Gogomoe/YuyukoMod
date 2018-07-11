package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon


class UpgradeAllAction(val cardID: String) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = AbstractGameAction.ActionType.WAIT
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
            it.cardID == cardID || it.cardID.matches(Regex("""$cardID \(.*\)"""))
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
