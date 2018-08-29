package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.SetHPAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower

class Stifle : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Stifle"
        val IMAGE_PATH = "images/yuyuko/cards/attack3.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.isEthereal = true
    }

    override fun makeCopy(): AbstractCard = Stifle()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean {
        if (!super.canUse(self, target)) {
            return false
        }
        val amount = target?.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        if (amount >= 20) {
            return true
        }
        this.cantUseMessage = EXTENDED_DESCRIPTION[0]
        return false
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(target, self, DiaphaneityPower.POWER_ID, 20)
        )
        AbstractDungeon.actionManager.addToBottom(
                SetHPAction(target!!, self!!, target.currentHealth / 2)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.isEthereal = false
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}