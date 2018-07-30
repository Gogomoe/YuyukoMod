package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import yuyuko.event.EndOfRoundDiaphaneityReduceEvent
import yuyuko.event.EventDispenser
import yuyuko.event.Observer

class LivingToDiePower : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Living to Die"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = -1
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/livingToDie.png")
    }

    private var observer: Observer<EndOfRoundDiaphaneityReduceEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(EndOfRoundDiaphaneityReduceEvent.ID) {
            if (it.power.owner != AbstractDungeon.player) {
                it.cancel()
            }
        }
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(EndOfRoundDiaphaneityReduceEvent.ID, observer!!)
    }


    override fun onApplyPower(power: AbstractPower?, target: AbstractCreature?, source: AbstractCreature?) {
        if (power!!.ID != DiaphaneityPower.POWER_ID || target == AbstractDungeon.player) {
            return
        }
        val amount = power.amount + (target!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0)
        if (amount >= 100) {
            AbstractDungeon.actionManager.addToBottom(
                    RemoveSpecificPowerAction(target, source, DiaphaneityPower.POWER_ID)
            )
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            target, source,
                            FadePower(target, 1),
                            1
                    )
            )
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            target, source,
                            IntangiblePlayerPower(target, 1),
                            1
                    )
            )
            this.flash()
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
