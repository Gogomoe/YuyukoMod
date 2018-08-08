package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.ReviveAction
import yuyuko.cards.isButterfly
import yuyuko.cards.isSakura
import yuyuko.cards.isSpecial
import yuyuko.hasEnoughPower
import yuyuko.patches.CardColorEnum
import yuyuko.powers.FanPower
import yuyuko.powers.InfiniteRevivingPower
import yuyuko.setCantUseMessage

class InfiniteReviving : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Infinite Reviving"
        val IMAGE_PATH = "images/yuyuko/cards/skill4.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean =
            super.canUse(self, target) && self!!.hasEnoughPower(FanPower.POWER_ID).also {
                setCantUseMessage(it, EXTENDED_DESCRIPTION[0])
            }

    override fun makeCopy(): AbstractCard = InfiniteReviving()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(self, self, FanPower.POWER_ID, 1)
        )
        AbstractDungeon.actionManager.addToBottom(
                ReviveAction(AbstractCard::isSakura)
        )
        AbstractDungeon.actionManager.addToBottom(
                ReviveAction(AbstractCard::isButterfly)
        )
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    ReviveAction(AbstractCard::isSpecial)
            )
        }

        val power = self!!.getPower(InfiniteRevivingPower.POWER_ID) as InfiniteRevivingPower?
        if (power != null) {
            power.cards.add(this)
            power.flash()
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            InfiniteRevivingPower(this)
                    )
            )
        }
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }

}