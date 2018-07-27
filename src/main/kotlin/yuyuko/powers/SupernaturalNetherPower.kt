package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.actions.SupernaturalNetherAction
import kotlin.math.max
import kotlin.math.min

class SupernaturalNetherPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Supernatural Nether"
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
        this.img = ImageMaster.loadImage("images/powers/supernaturalNether.png")
    }

    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    RemoveSpecificPowerAction(this.owner, this.owner, this)
            )
        }
    }


    override fun atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(
                SupernaturalNetherAction()
        )
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(owner, owner, this, 1)
        )
        this.flash()
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
