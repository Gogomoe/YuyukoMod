package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.ChangeAllAction
import demo.actions.HideAction
import demo.cards.HideCards
import demo.cards.isSpecialSakura
import demo.patches.CardColorEnum

class MonsterCherryTree : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Monster Cherry Tree"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = MonsterCherryTree()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {

        val specialSakuras = listOf<() -> AbstractCard>(
                ::SakuraBloom, ::SakuraDormancy, ::SakuraSeal, ::SakuraSuicide
        )

        AbstractDungeon.actionManager.addToBottom(
                ChangeAllAction(AbstractCard::isSpecialSakura, specialSakuras)
        )
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
            this.upgradeBaseCost(0)
        }
    }


}