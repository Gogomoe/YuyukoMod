package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import demo.actions.RetrievalAction
import demo.actions.UpgradeAllAction
import demo.cards.isSakura
import demo.cards.yuyuko.Sakura
import demo.event.DegradeEvent
import demo.event.DegradeEvent.DegradeReason.USE
import demo.event.EventDispenser
import demo.event.Observer
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
        this.img = Texture("images/powers/power.png")
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
        repeat(this.amount) {
            AbstractDungeon.actionManager.addToBottom(
                    UpgradeAllAction(Sakura.ID)
            )
        }
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
