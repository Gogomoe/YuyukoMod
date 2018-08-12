package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import kotlin.math.max
import kotlin.math.min

class TheWayToDeathPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "The Way to Death"
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
        this.img = ImageMaster.loadImage("images/powers/theWayToDeathPower.png")
    }

    private val costs = mutableSetOf<Int>()

    override fun onUseCard(card: AbstractCard?, action: UseCardAction?) {
        costs.add(when (card!!.cost) {
            -2 -> 0
            -1 -> EnergyPanel.totalCount
            else -> card.cost
        })
    }

    override fun atStartOfTurn() {
        val count = costs.size * amount
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        owner, owner,
                        GhostPower(owner, count),
                        count
                )
        )
        costs.clear()
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
