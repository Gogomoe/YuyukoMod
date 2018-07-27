package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.Property
import yuyuko.cards.yuyuko.Sakura
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
import yuyuko.event.UpgradeAllEvent
import kotlin.math.max
import kotlin.math.min

class PostponeBloomPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Postpone Bloom"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!

        init {
            Property.put<AbstractPower.() -> Unit>("$POWER_ID:triggerOnShuffle") {
                this as PostponeBloomPower
                EventDispenser.unsubscribe(UpgradeAllEvent.ID, observer!!)

                EventDispenser.emit(UpgradeAllEvent(Sakura.ID, count))
                AbstractDungeon.actionManager.addToBottom(
                        DrawCardAction(owner, count)
                )

                AbstractDungeon.actionManager.addToBottom(
                        RemoveSpecificPowerAction(owner, owner, this)
                )
            }
        }
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/postponeBloom.png")
    }

    private var count = 0

    private var observer: Observer<UpgradeAllEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(UpgradeAllEvent.ID) {
            if (it.cardID == Sakura.ID) {
                count += this.amount
                this.flash()
                this.updateDescription()
            }
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + count + DESCRIPTIONS[1] + count + DESCRIPTIONS[2]
    }

}
