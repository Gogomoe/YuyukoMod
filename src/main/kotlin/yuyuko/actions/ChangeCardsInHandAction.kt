package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.getRandom

class ChangeCardsInHandAction(val condition: (AbstractCard) -> Boolean, val changeTo: List<() -> AbstractCard>) : AbstractGameAction() {

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

        player.hand.group
                .filter(condition)
                .forEach {
                    player.hand.removeCard(it)
                    val card = constructor(it.timesUpgraded)
                    player.hand.addToTop(card)
                }

        player.hand.refreshHandLayout()
    }

}