package yuyuko.powers

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import yuyuko.actions.ReviveAction
import yuyuko.cards.isSpecial
import yuyuko.cards.yuyuko.Butterfly

class ReviveTheButterfliesPower : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Revive The Butterflies"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/reviveTheButterflies.png")
    }

    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        if (!usedCard!!.isSpecial()) {
            AbstractDungeon.actionManager.addToBottom(
                    ReviveAction(Butterfly.ID)
            )
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
