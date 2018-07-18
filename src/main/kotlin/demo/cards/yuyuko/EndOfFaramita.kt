package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower

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
    }

    init {
        this.isEthereal = true
    }

    override fun makeCopy(): AbstractCard = EndOfFaramita()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean {
        if (!super.canUse(self, target)) {
            return false
        }
        val amount = target!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        if (amount >= 20) {
            return false
        }
        return true
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val amount = target!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: return
        if (amount >= 20) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, self,
                        DiaphaneityPower(target, amount),
                        amount
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}