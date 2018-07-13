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


class DiscoverAction(val numCards: Int) : AbstractGameAction() {

    init {
        this.actionType = ActionType.CARD_MANIPULATION
        this.duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (numCards == 1) {
                addOneCard()
                this.isDone = true
            } else {
                openSelectScreen()
            }
        } else {
            AbstractDungeon.gridSelectScreen.selectedCards.forEach {
                addToDrawPile(it)
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            this.isDone = true
        }

        this.tickDuration()
    }

    private fun addOneCard() {
        val card = discoverCards(1).first()
        addToDrawPile(card)
    }

    private fun addToDrawPile(card: AbstractCard) {
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        card, 1, true, true
                )
        )
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
                avoidRepeatedCard@ for (i in 1..10) {
                    if (c != null && !retVal.contains(c!!)) {
                        break@avoidRepeatedCard
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
