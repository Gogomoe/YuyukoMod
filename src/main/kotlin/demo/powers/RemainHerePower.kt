package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class RemainHerePower : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Remain Here"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.updateDescription()
        this.type = PowerType.DEBUFF
        this.isTurnBased = true
        this.img = Texture("images/powers/power.png")
    }


    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(owner, owner, FanPower.POWER_ID, 10)
        )
        this.flash()
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
