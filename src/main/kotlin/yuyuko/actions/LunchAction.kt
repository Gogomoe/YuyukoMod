package yuyuko.actions

import com.megacrit.cardcrawl.cards.AbstractCard


class LunchAction(numCards: Int) : DiscoverAction(numCards) {

    override fun discoverCard(): AbstractCard {
        return super.discoverCard().apply {
            cost = 0
            costForTurn = 0
            isCostModified = true
        }
    }

}
