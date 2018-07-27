package yuyuko.powers

import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.actions.RetrievalAction
import yuyuko.cards.isSakura
import yuyuko.cards.yuyuko.Sakura
import yuyuko.event.DegradeEvent
import yuyuko.event.DegradeEvent.DegradeReason.USE
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
import yuyuko.event.UpgradeAllEvent
import kotlin.math.max
import kotlin.math.min

class ShowyWitheringPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Showy Withering"
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
        this.img = ImageMaster.loadImage("images/powers/showyWithering.png")
    }

    private var observer: Observer<DegradeEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(DegradeEvent.ID) {
            if (it.reason == USE && it.card.isSakura() && count == 3) {
                it.cancel()
            }
        }
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(DegradeEvent.ID, observer!!)
    }

    override fun atStartOfTurn() {
        this.flash()
        EventDispenser.emit(UpgradeAllEvent(Sakura.ID, amount))
        AbstractDungeon.actionManager.addToBottom(
                RetrievalAction(Sakura.ID, this.amount)
        )
    }

    var count = 0

    override fun onUseCard(card: AbstractCard?, action: UseCardAction?) {
        if (card!!.isSakura()) {
            count++
            if (count == 4) {
                count = 1
                action!!.exhaustCard = true
            }
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]
    }

}
