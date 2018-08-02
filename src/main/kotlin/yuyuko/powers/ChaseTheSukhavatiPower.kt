package yuyuko.powers

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower

class ChaseTheSukhavatiPower : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Chase The Sukhavati"
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
        this.img = ImageMaster.loadImage("images/powers/chaseTheSukhavati.png")
    }

    private var playedCardCount = 0

    override fun onAfterCardPlayed(usedCard: AbstractCard?) {
        playedCardCount++
        if (playedCardCount >= 10) {
            AbstractDungeon.actionManager.addToBottom(
                    yuyuko.actions.EndTurnAction()
            )
            AbstractDungeon.actionManager.addToBottom(
                    RemoveSpecificPowerAction(owner, owner, this)
            )
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
