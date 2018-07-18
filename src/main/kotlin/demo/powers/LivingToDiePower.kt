package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.FadingPower
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower

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
        this.img = Texture("images/powers/power.png")
    }

    override fun onApplyPower(power: AbstractPower?, target: AbstractCreature?, source: AbstractCreature?) {
        if (power!!.ID != DiaphaneityPower.POWER_ID || target == AbstractDungeon.player) {
            return
        }
        val amount = power.amount + (target!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0)
        if (amount >= 100) {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            target, source,
                            FadingPower(target, 1),
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
