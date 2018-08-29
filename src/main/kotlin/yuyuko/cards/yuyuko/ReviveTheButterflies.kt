package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import yuyuko.actions.SetHPAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.FanPower
import yuyuko.powers.ReviveTheButterfliesPower

class ReviveTheButterflies : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Revive The Butterflies"
        val IMAGE_PATH = "images/yuyuko/cards/power.png"
        val COST = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }


    override fun makeCopy(): AbstractCard = ReviveTheButterflies()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val amount = self?.getPower(FanPower.POWER_ID)?.amount ?: 0
        self!!.getPower(FanPower.POWER_ID)?.reducePower(amount)

        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(self, self,
                        FanPower(0)
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                SetHPAction(self, self, 1)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        IntangiblePlayerPower(self, 3),
                        3
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        ReviveTheButterfliesPower()
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(2)
        }
    }

}