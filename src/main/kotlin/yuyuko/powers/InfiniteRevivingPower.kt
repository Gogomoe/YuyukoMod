package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.actions.ReviveAction

class InfiniteRevivingPower(val card: AbstractCard) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Infinite Reviving"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    val cards: MutableList<AbstractCard> = mutableListOf()

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/chaseTheSukhavati.png")//TODO

        cards.add(card)
    }

    override fun onAttacked(info: DamageInfo?, damageAmount: Int): Int {
        if (damageAmount < 1 || info?.type != DamageType.NORMAL) {
            return damageAmount
        }
        this.flash()
        cards.forEach {
            AbstractDungeon.actionManager.addToBottom(
                    ReviveAction { card: AbstractCard -> card == it }
            )
        }
        cards.clear()
        AbstractDungeon.actionManager.addToBottom(
                RemoveSpecificPowerAction(owner, owner, this)
        )
        return damageAmount
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
