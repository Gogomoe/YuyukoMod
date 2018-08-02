package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.randomBoolean
import yuyuko.randomInt

open class DiscoverAction(val numCards: Int) : AbstractGameAction() {

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
                addToHand(it)
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            this.isDone = true
        }

        this.tickDuration()
    }

    private fun addOneCard() {
        val card = discoverCards(1).first()
        addToHand(card)
    }

    private fun addToHand(card: AbstractCard) {
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInHandAction(card, 1)
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


    open fun discoverCards(numCards: Int): List<AbstractCard> {
        val retVal = mutableListOf<AbstractCard>()

        repeat(numCards) {

            var c = discoverCard()

            avoidRepeatedCard@ for (i in 1..10) {
                if (!retVal.contains(c)) {
                    break@avoidRepeatedCard
                }
                c = discoverCard()
            }

            retVal.add(c)
        }

        return retVal

    }

    open fun discoverCard(): AbstractCard {
        val rarity = rollRarity()
        val c = AbstractDungeon.getCard(rarity)

        if (shouldUpgrade(c)) {
            c.upgrade()
        }
        return c
    }

    open fun rollRarity(): CardRarity {
        val rnd = randomInt(100)
        return when {
            rnd < 10 -> CardRarity.RARE
            rnd < 40 -> CardRarity.UNCOMMON
            else -> CardRarity.COMMON
        }
    }

    open fun shouldUpgrade(c: AbstractCard): Boolean =
            when (c.rarity) {
                CardRarity.RARE -> false
                CardRarity.UNCOMMON -> {
                    randomBoolean(0.125f)
                }
                CardRarity.COMMON -> {
                    randomBoolean(0.25f)
                }
                else -> false
            }


}
