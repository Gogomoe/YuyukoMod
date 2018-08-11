package yuyuko.powers

import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.event.EndOfRoundDiaphaneityReduceEvent
import yuyuko.event.EventDispenser
import yuyuko.relics.BlueKimonoFullMoon
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
        this.img = ImageMaster.loadImage("images/powers/diaphaneity.png")
    }

    private val rateInt: Int
        get() = if (AbstractDungeon.player.hasRelic(BlueKimonoFullMoon.ID)) 6 else 5
    private val rate: Float
        get() = if (AbstractDungeon.player.hasRelic(BlueKimonoFullMoon.ID)) 0.06f else 0.05f


    override fun atDamageReceive(damage: Float, damageType: DamageType?): Float {
        return when {
            damageType != DamageType.NORMAL -> damage
            this.owner == AbstractDungeon.player -> damage * max((1 - rate * amount), 0f)
            else -> damage * min((1 + rate * amount), 2f)
        }
    }

    override fun atEndOfRound() {
        val reduceAmount = this.amount / 2
        EventDispenser.emit(EndOfRoundDiaphaneityReduceEvent(this, reduceAmount))
    }

    override fun updateDescription() {
        this.description = if (this.owner == AbstractDungeon.player) {
            DESCRIPTIONS[0] + min(this.amount * rateInt, 100) + DESCRIPTIONS[2]
        } else {
            DESCRIPTIONS[1] + min(this.amount * rateInt, 100) + DESCRIPTIONS[2]
        }
    }

}