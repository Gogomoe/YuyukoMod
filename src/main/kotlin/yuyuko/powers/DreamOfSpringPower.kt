package yuyuko.powers


import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.math.max
import kotlin.math.min

class DreamOfSpringPower(owner: AbstractCreature, amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Dream of Spring"
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
        this.type = PowerType.BUFF
        this.isTurnBased = true
        this.img = ImageMaster.loadImage("images/powers/ghost.png")//TODO needs power img
    }

    override fun onHeal(healAmount: Int): Int {
        return healAmount * 2
    }

    override fun atDamageReceive(damage: Float, damageType: DamageInfo.DamageType?): Float {
        return if (damageType == DamageInfo.DamageType.HP_LOSS) {
            damage
        } else {
            damage / 2
        }
    }

    override fun atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(owner, owner, this, 1)
        )
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}