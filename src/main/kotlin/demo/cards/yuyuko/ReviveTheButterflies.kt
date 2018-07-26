package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.LoseHPAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import demo.patches.CardColorEnum
import demo.powers.FanPower
import demo.powers.ReviveTheButterfliesPower

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
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(self, self, FanPower.POWER_ID, amount)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(self, self,
                        FanPower(0)
                )
        )
        val hp = self!!.currentHealth - 1
        AbstractDungeon.actionManager.addToBottom(
                LoseHPAction(self, self, hp)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        IntangiblePlayerPower(self, 4),
                        4
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