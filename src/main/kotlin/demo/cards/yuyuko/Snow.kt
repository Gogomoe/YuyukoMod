package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower

class Snow : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Snow"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 0
        val DIAPHANEITY_AMOUNT = 6
        val UPGRADE_PLUS_AMOUNT = 4
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = DIAPHANEITY_AMOUNT
        this.magicNumber = DIAPHANEITY_AMOUNT
    }

    override fun makeCopy(): AbstractCard = Snow()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        DiaphaneityPower(self!!, this.magicNumber),
                        this.magicNumber
                )
        )
    }

    override fun upgrade() {
        upgradeName()
        upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
    }

}