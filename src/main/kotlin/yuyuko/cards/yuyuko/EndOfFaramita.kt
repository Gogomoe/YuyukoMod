package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.CARD
import yuyuko.event.EventDispenser
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower

class EndOfFaramita : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "End of Faramita"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.isEthereal = true
    }

    override fun makeCopy(): AbstractCard = EndOfFaramita()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean {
        if (!super.canUse(self, target)) {
            return false
        }
        val amount = target?.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        if (amount >= 20) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0]
            return false
        }
        return true
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val amount = target!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: return
        if (amount >= 20) {
            return
        }
        EventDispenser.emit(ApplyDiaphaneityPowerEvent(target, self!!, amount, CARD))

    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}