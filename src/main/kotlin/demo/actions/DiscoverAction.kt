package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.randomInt

//TODO to be tested
class DiscoverAction(val numCards: Int) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = AbstractGameAction.ActionType.WAIT
    }

    override fun update() {
        openSelectScreen()

        AbstractDungeon.gridSelectScreen.selectedCards.forEach {
            AbstractDungeon.actionManager.addToBottom(
                    MakeTempCardInDrawPileAction(
                            it, 1, true, true
                    )
            )
        }

        AbstractDungeon.gridSelectScreen.selectedCards.clear()

        this.tickDuration()
    }

    private fun openSelectScreen() {
        val group = CardGroup(CardGroupType.UNSPECIFIED)

        discoverCards(numCards).forEach {
            group.addToBottom(it)
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

    companion object {


        private fun discoverCards(numCards: Int): List<AbstractCard> {
            val retVal = mutableListOf<AbstractCard>()

            var c: AbstractCard?
            repeat(numCards) {
                val rarity = rollRarity()
                c = AbstractDungeon.getCard(rarity)
                avoidRepeatedCard@ repeat(10) {
                    if (c != null && !retVal.contains(c!!)) {
                        return@avoidRepeatedCard
                    }
                    c = AbstractDungeon.getCard(rarity) ?: c
                }

                retVal.add(c!!)
            }
            return retVal

        }

        private fun rollRarity(): CardRarity {
            val rnd = randomInt(100)
            return when {
                rnd < 10 -> CardRarity.RARE
                rnd < 40 -> CardRarity.UNCOMMON
                else -> CardRarity.COMMON
            }
        }
    }


}
