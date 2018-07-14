package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.math.max
import kotlin.math.min

class DiaphaneityPower(owner: AbstractCreature, amount: Int) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Diaphaneity"
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
        this.type = if (owner == AbstractDungeon.player) PowerType.BUFF else PowerType.DEBUFF
        this.isTurnBased = true
        this.img = Texture("images/powers/power.png")
    }


    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    RemoveSpecificPowerAction(this.owner, this.owner, NAME)
            )
        }
    }

    override fun atDamageReceive(damage: Float, damageType: DamageType?): Float {
        return when {
            damageType != DamageType.NORMAL -> damage
            this.owner == AbstractDungeon.player -> max(damage * (1 - 0.05f * amount), 0f)
            else -> damage * (1 + 0.05f * amount)
        }
    }

    override fun atEndOfRound() {
        val reduceAmount = this.amount / 2
        AbstractDungeon.actionManager.addToTop(
                ReducePowerAction(this.owner, this.owner, POWER_ID, reduceAmount)
        )
    }

    override fun updateDescription() {
        this.description = if (this.owner == AbstractDungeon.player) {
            DESCRIPTIONS[0] + this.amount * 5 + DESCRIPTIONS[2]
        } else {
            DESCRIPTIONS[1] + this.amount * 5 + DESCRIPTIONS[2]
        }
    }

}