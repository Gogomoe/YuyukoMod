package yuyuko.powers

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.cards.isSakura
import yuyuko.event.EventDispenser
import yuyuko.event.Observer
import yuyuko.event.OnDrawEvent
import kotlin.math.max
import kotlin.math.min

class CerasusSubhirtellaPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Cerasus Subhirtella"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    private var observer: Observer<OnDrawEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(OnDrawEvent.ID) {
            if (card.isSakura()) {
                repeat(amount) { _ ->
                    card.upgrade()
                }
            }
        }
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(OnDrawEvent.ID, observer!!)
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/cerasusSubhirtella.png")
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
