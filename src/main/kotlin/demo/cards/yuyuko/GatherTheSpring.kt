package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.FanPower

class GatherTheSpring : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Gather the Spring"
        val IMAGE_PATH = "images/yuyuko/cards/attack4.png"
        val COST = 1
        val ATTACK_DMG = 11
        val UPGRADE_PLUS_DMG = 5
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.baseDamage = ATTACK_DMG
    }

    override fun makeCopy(): AbstractCard = GatherTheSpring()

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
                DamageAllEnemiesAction(
                        self,
                        this.multiDamage,
                        DamageType.NORMAL,
                        AttackEffect.SLASH_DIAGONAL
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(Sakura(), 1, true, true)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

}