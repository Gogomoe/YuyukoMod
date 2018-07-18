package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.Sakura

class ButterfliesLoveFlowersAction(val timesUpgraded: Int) : AbstractGameAction() {

    init {
        this.actionType = ActionType.CARD_MANIPULATION
        this.duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            openSelectScreen()
        } else {
            AbstractDungeon.gridSelectScreen.selectedCards.forEach {
                AbstractDungeon.actionManager.addToBottom(
                        MakeTempCardInDrawPileAction(it, 1, true, true)
                )
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            this.isDone = true
        }

        this.tickDuration()
    }


    private fun openSelectScreen() {
        val group = CardGroup(UNSPECIFIED)

        val cards = listOf(Sakura(), Butterfly())

        cards.forEach { card ->
            repeat(timesUpgraded) {
                card.upgrade()
            }
            group.addToBottom(card)
        }

        AbstractDungeon.gridSelectScreen.open(
                group,
                1,
                "Select a card", //TODO 语言包
                false,
                false,
                false,
                false
        )

    }

}
