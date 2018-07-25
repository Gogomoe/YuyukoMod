package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.HideAction
import demo.cards.HideCards
import demo.patches.CardColorEnum

class BuryInTheTemplate : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Bury in The Template"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = 4
        this.magicNumber = 4
    }

    override fun makeCopy(): AbstractCard = BuryInTheTemplate()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        this.baseMagicNumber -= 1
        this.magicNumber -= 1
        this.initializeDescription()
        if (this.baseMagicNumber == 0) {
            AbstractDungeon.getCurrRoom().isBattleOver = true
        }
    }

    override fun triggerWhenDrawn() {
        if (HideCards.shouldHide()) {
            AbstractDungeon.actionManager.addToBottom(
                    HideAction(this)
            )
        }
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(2)
        }
    }

}