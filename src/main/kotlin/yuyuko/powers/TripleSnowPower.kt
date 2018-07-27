package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.CARD
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
import kotlin.math.max
import kotlin.math.min

class TripleSnowPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Triple Snow"
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
        this.img = ImageMaster.loadImage("images/powers/tripleSnow.png")
    }

    private var observer: Observer<ApplyDiaphaneityPowerEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(ApplyDiaphaneityPowerEvent.ID) {
            if (it.reason != CARD || it.target != AbstractDungeon.player) {
                return@subscribe
            }
            it.amount *= (amount * 3)
        }
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(ApplyDiaphaneityPowerEvent.ID, observer!!)
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                RemoveSpecificPowerAction(owner, owner, this)
        )
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount * 3 + DESCRIPTIONS[1]
    }

}
