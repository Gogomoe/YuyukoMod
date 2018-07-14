package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.isButterfly
import demo.cards.isSakura
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.Sakura

class RetrievalAction(val cardID: String, val count: Int = 1) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
    }

    override fun update() {
        val player = AbstractDungeon.player
        val groups = listOf(player.drawPile, player.discardPile)

        repeat(count) {
            for (group in groups) {
                val card = when (cardID) {
                    Butterfly.ID -> group.findCardByCondition(AbstractCard::isButterfly)
                    Sakura.ID -> group.findCardByCondition(AbstractCard::isSakura)
                    else -> group.findCardByName(cardID)
                }
                if (card != null) {
                    group.removeCard(card)
                    player.hand.addToTop(card)
                    player.hand.refreshHandLayout()
                    break
                }
            }
        }
        this.isDone = true
    }


    private fun CardGroup.findCardByCondition(condition: (AbstractCard) -> Boolean): AbstractCard? {
        val var2 = this.group.iterator()

        var c: AbstractCard
        do {
            if (!var2.hasNext()) {
                return null
            }

            c = var2.next()
        } while (!condition(c))

        return c
    }

}