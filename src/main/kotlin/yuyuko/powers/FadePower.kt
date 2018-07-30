package yuyuko.powers

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower

class FadePower(owner: AbstractCreature, turns: Int) : AbstractPower() {

    init {
        this.name = FadePower.NAME
        this.ID = FadePower.POWER_ID
        this.owner = owner
        this.amount = turns
        this.updateDescription()
        this.loadRegion("fading")
    }

    override fun updateDescription() {
        if (this.amount == 1) {
            this.description = FadePower.DESCRIPTIONS[2]
        } else {
            this.description = FadePower.DESCRIPTIONS[0] + this.amount + FadePower.DESCRIPTIONS[1]
        }
    }

    override fun duringTurn() {
        if (this.amount == 1 && !this.owner.isDying) {
            val damage = owner.maxHealth + owner.currentBlock
            AbstractDungeon.actionManager.addToBottom(
                    RemoveSpecificPowerAction(owner, owner, IntangiblePlayerPower.POWER_ID)
            )
            AbstractDungeon.actionManager.addToBottom(
                    DamageAction(
                            owner,
                            DamageInfo(owner, damage, HP_LOSS),
                            FIRE
                    )
            )
        } else {
            AbstractDungeon.actionManager.addToBottom(ReducePowerAction(this.owner, this.owner, this, 1))
            this.updateDescription()
        }
    }

    companion object {
        val POWER_ID = "Fade"
        private val powerStrings: PowerStrings = CardCrawlGame.languagePack.getPowerStrings("Fading")
        val NAME: String
        val DESCRIPTIONS: Array<String>

        init {
            NAME = FadePower.powerStrings.NAME
            DESCRIPTIONS = FadePower.powerStrings.DESCRIPTIONS
        }
    }
}
