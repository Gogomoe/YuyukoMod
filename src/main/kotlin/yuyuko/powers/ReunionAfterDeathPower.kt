package yuyuko.powers

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.math.max
import kotlin.math.min

class ReunionAfterDeathPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Reunion after Death"
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
        this.img = ImageMaster.loadImage("images/powers/reunionAfterDeath.png")
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    override fun onEnergyRecharge() {
        this.flash()
        AbstractDungeon.player.gainEnergy(this.amount)
    }

}
