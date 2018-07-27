package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.SupernaturalNetherPower

class SupernaturalNether : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Supernatural Nether"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 2
        val AMOUNT = 1
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = AMOUNT
        this.magicNumber = AMOUNT
    }

    override fun makeCopy(): AbstractCard = SupernaturalNether()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        SupernaturalNetherPower(this.magicNumber),
                        this.magicNumber
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }


}