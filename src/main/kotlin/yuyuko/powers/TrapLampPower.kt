package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.math.max
import kotlin.math.min

class TrapLampPower(val card: AbstractCard) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Trap Lamp"
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
        this.img = ImageMaster.loadImage("images/powers/trapLamp.png")
    }

    override fun atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInHandAction(card, 1)
        )
        AbstractDungeon.actionManager.addToBottom(
                RemoveSpecificPowerAction(owner, owner, this)
        )
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
