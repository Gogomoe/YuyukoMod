package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import demo.actions.TriggerGhostPowerAction
import kotlin.math.max
import kotlin.math.min

class BecomeGhostPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Become Ghost"
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
        this.img = Texture("images/powers/power.png")
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            return
        }

        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        owner, owner,
                        GhostPower(owner, amount),
                        amount
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                TriggerGhostPowerAction()
        )
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
