package yuyuko.powers

import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.cards.isSpecialButterfly
import yuyuko.cards.isSpecialSakura
import yuyuko.event.DegradeEvent
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
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
        this.img = ImageMaster.loadImage("images/powers/fan.png")
    }


    /**
     * 防止消耗的 樱 蝶 降级
     */
    private var observer: Observer<DegradeEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(DegradeEvent.ID) {
            if (it.card.isSpecialSakura() && count(AbstractCard::isSpecialSakura) > amount) {
                it.cancel()
            }
            if (it.card.isSpecialButterfly() && count(AbstractCard::isSpecialButterfly) > amount) {
                it.cancel()
            }
        }
    }

    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        this.amount = min(max(amount, 0), 999)
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
