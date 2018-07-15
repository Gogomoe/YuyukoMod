package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import demo.cards.isSpecialButterfly
import demo.cards.isSpecialSakura
import kotlin.math.max
import kotlin.math.min

class FanPower(amount: Int) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Fan"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = Texture("images/powers/power.png")
    }

    override fun onUseCard(card: AbstractCard?, action: UseCardAction?) {
        if (card!!.isSpecialSakura() && count(AbstractCard::isSpecialSakura) > amount) {
            this.flash()
            action!!.exhaustCard = true
        }
        if (card.isSpecialButterfly() && count(AbstractCard::isSpecialButterfly) > amount) {
            this.flash()
            action!!.exhaustCard = true
        }
    }

    private fun count(condition: AbstractCard.() -> Boolean): Int {
        val player = AbstractDungeon.player
        val groups = listOf(player.hand, player.drawPile, player.discardPile)
        return groups
                .map {
                    it.group.filter { it.condition() }.size
                }
                .reduce { acc, i -> acc + i }
    }


    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
