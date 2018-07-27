package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.BecomeGhostPower
import yuyuko.powers.FanPower
import yuyuko.powers.GhostPower

class BecomeGhost : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Become Ghost"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = BecomeGhost()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean {
        if (!super.canUse(self, target)) {
            return false
        }
        val amount = self!!.getPower(FanPower.POWER_ID)?.amount ?: 0
        if (amount < 1) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0]
            return false
        }
        return true
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(self, self, FanPower.POWER_ID, 1)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        BecomeGhostPower(),
                        1
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, 1),
                        1
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.exhaust = false
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}