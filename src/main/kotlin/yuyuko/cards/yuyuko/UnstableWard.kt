package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.DiscoverAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.GhostPower

class UnstableWard : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Unstable Ward"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = UnstableWard()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean {
        if (!super.canUse(self, target)) {
            return false
        }
        val amount = self!!.getPower(GhostPower.POWER_ID)?.amount ?: 0
        if (amount > 0) {
            return true
        }

        this.cantUseMessage = EXTENDED_DESCRIPTION[0]
        return false
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val draw = if (upgraded) 2 else 1
        val discover = if (upgraded) 5 else 3
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(self, self, GhostPower.POWER_ID, 1)
        )
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, draw)
        )
        AbstractDungeon.actionManager.addToBottom(
                DiscoverAction(discover)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}