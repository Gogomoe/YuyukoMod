package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.event.EndOfRoundDiaphaneityReduceEvent
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
import kotlin.math.max
import kotlin.math.min

class TheForgottenWinterPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "The Forgotten Winter"
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
        this.isTurnBased = true
        this.img = ImageMaster.loadImage("images/powers/theForgottenWinter.png")
    }

    private var observer: Observer<EndOfRoundDiaphaneityReduceEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(EndOfRoundDiaphaneityReduceEvent.ID) {
            if (power.owner == owner) {
                cancel()
            }
        }
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(EndOfRoundDiaphaneityReduceEvent.ID, observer!!)
    }

    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    RemoveSpecificPowerAction(this.owner, this.owner, this)
            )
        }
    }

    override fun atEndOfRound() {
        this.flash()
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(owner, owner, this, 1)
        )
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
