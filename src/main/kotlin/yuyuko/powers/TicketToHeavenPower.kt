package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF
import kotlin.math.max
import kotlin.math.min

class TicketToHeavenPower(owner: AbstractCreature, amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Ticket to Heaven"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = owner
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = DEBUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/ticketToHeaven.png")
    }

    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    RemoveSpecificPowerAction(this.owner, this.owner, this)
            )
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}