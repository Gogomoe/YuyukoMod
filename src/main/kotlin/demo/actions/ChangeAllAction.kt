package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.addToRandomSpotIfIsDrawPile
import demo.getRandom

class ChangeAllAction(val condition: (AbstractCard) -> Boolean, val changeTo: List<() -> AbstractCard>) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.CARD_MANIPULATION
    }

    override fun update() {
        this.isDone = true

        val constructor: (Int) -> AbstractCard = { shouldUpgradeTimes ->
            changeTo.getRandom()!!.invoke().apply {
                repeat(shouldUpgradeTimes) {
                    upgrade()
                }
            }
        }

        val player = AbstractDungeon.player
        val groups = listOf(player.hand, player.drawPile, player.discardPile)

        groups.forEach { group ->
            group.group
                    .filter(condition)
                    .forEach {
                        group.removeCard(it)
                        val card = constructor(it.timesUpgraded)
                        group.addToRandomSpotIfIsDrawPile(card)
                    }
        }

        player.hand.refreshHandLayout()
    }

}