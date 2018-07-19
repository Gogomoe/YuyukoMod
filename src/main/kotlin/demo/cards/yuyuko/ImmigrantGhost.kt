package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.ConstrictedPower
import demo.patches.CardColorEnum
import demo.powers.GhostPower

class ImmigrantGhost : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Immigrant Ghost"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 1
        val TIMES = 2
        val UPGRADE_PLUS_TIMES = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = TIMES
        this.magicNumber = TIMES
    }

    override fun makeCopy(): AbstractCard = ImmigrantGhost()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        var amount = (self!!.getPower(GhostPower.POWER_ID)?.amount ?: 0) / 2
        if (amount <= 0) {
            return
        }
        amount *= this.magicNumber

        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, self,
                        ConstrictedPower(target, self, amount),
                        amount
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(self, self, GhostPower.POWER_ID, amount)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(UPGRADE_PLUS_TIMES)
        }
    }


}