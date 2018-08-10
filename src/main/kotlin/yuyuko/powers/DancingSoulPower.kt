package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.actions.ReviveAction
import yuyuko.cards.isButterfly
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
import yuyuko.event.OnDrawEvent
import kotlin.math.max
import kotlin.math.min

class DancingSoulPower(card: AbstractCard) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Dancing Soul"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    val cards: MutableList<AbstractCard> = mutableListOf()

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/dancingSoul.png")

        cards.add(card)
    }

    private var observer: Observer<OnDrawEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(OnDrawEvent.ID) {
            if (card.isButterfly()) {
                this@DancingSoulPower.flash()
                cards.forEach {
                    AbstractDungeon.actionManager.addToBottom(
                            ReviveAction { card: AbstractCard -> card == it }
                    )
                }
                cards.clear()
                AbstractDungeon.actionManager.addToBottom(
                        RemoveSpecificPowerAction(owner, owner, this@DancingSoulPower)
                )
            }
        }
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(OnDrawEvent.ID, observer!!)
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
