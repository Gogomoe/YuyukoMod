package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.NihilityPower

class Nihility : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Nihility"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION
    }

    init {
        this.baseMagicNumber = 1
        this.magicNumber = 1
    }

    override fun makeCopy(): AbstractCard = Nihility()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    DrawCardAction(self, 1)
            )
        }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        NihilityPower(this.magicNumber),
                        this.magicNumber
                )
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