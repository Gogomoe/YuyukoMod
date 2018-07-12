package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.DiscoverAction
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower

//TODO to be tested
class Explore : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        AbstractCard.CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Explore"
        val IMAGE_PATH = "images/yuyuko/cards/explore.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPDEAGE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }


    override fun makeCopy(): AbstractCard = Explore()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DiscoverAction(1)
        )
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            DiaphaneityPower(self!!, this.magicNumber),
                            this.magicNumber
                    )
            )
        }

    }

    override fun upgrade() {
        upgradeName()
        this.rawDescription = UPDEAGE_DESCRIPTION
        this.initializeDescription()
    }


}