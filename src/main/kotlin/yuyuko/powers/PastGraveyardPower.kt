package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.randomInt
import kotlin.math.max
import kotlin.math.min

class PastGraveyardPower(amount: Int) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Past Graveyard"
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
        this.img = ImageMaster.loadImage("images/powers/pastGraveyard.png")
    }


    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            return
        }
        this.flash()

        applyDiaphaneityPower(owner)
        AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .forEach { applyDiaphaneityPower(it) }
    }

    private fun applyDiaphaneityPower(target: AbstractCreature) {
        val amount = randomInt(amount) + 1
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, owner,
                        DiaphaneityPower(target, amount),
                        amount
                )
        )
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
