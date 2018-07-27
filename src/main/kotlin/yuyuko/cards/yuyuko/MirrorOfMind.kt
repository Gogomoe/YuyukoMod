package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum

class MirrorOfMind : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Mirror of Mind"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        val AMOUNT = 1
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = AMOUNT
        this.magicNumber = AMOUNT
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = MirrorOfMind()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val hp = self!!.currentHealth
        val card = Photo(hp)
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(card, this.magicNumber, true, true)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }


}