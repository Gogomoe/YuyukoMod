package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.hasEnoughPower
import yuyuko.patches.CardColorEnum
import yuyuko.powers.BecomePhantomPower
import yuyuko.powers.FanPower
import yuyuko.setCantUseMessage

class BecomePhantom : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Become Phantom"
        val IMAGE_PATH = "images/yuyuko/cards/power.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = BecomePhantom()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean =
            super.canUse(self, target) && self!!.hasEnoughPower(FanPower.POWER_ID, 2).also {
                setCantUseMessage(it, EXTENDED_DESCRIPTION[0])
            }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        self!!.getPower(FanPower.POWER_ID)?.reducePower(2)
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        BecomePhantomPower(),
                        1
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.isInnate = true
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}