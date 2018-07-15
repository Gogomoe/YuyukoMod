package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import demo.actions.UpgradeAllAction
import demo.cards.isButterfly
import demo.cards.yuyuko.Butterfly
import kotlin.math.max
import kotlin.math.min

class FloatOnMoonPower(amount: Int) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Float on Moon"
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

    override fun atStartOfTurn() {
        repeat(amount) {
            AbstractDungeon.actionManager.addToBottom(
                    UpgradeAllAction(Butterfly.ID)
            )
        }
    }

    override fun onUseCard(card: AbstractCard?, action: UseCardAction?) {
        if (card!!.isButterfly()) {
            this.flash()
            action!!.exhaustCard = true
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]
    }

}
