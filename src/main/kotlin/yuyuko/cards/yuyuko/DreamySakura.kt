package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.UpgradeAllAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower

class DreamySakura : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.BASIC, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Dreamy Sakura"
        val IMAGE_PATH = "images/yuyuko/cards/skill.png"
        val COST = 1
        val DIAPHANEITY_AMOUNT = 2
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = DIAPHANEITY_AMOUNT
        this.magicNumber = DIAPHANEITY_AMOUNT
    }

    override fun makeCopy(): AbstractCard = DreamySakura()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        DiaphaneityPower(self!!, this.magicNumber),
                        this.magicNumber
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                UpgradeAllAction(Sakura.ID)
        )
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }

}