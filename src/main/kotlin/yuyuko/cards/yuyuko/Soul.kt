package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum

class Soul : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.STATUS, CardColorEnum.YUYUKO_COLOR,
        CardRarity.SPECIAL, CardTarget.NONE
) {

    companion object {
        @JvmStatic
        val ID = "Soul"
        val IMAGE_PATH = "images/yuyuko/cards/soul.png"
        val COST = -2
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.isEthereal = true
    }

    override fun makeCopy(): AbstractCard = Soul()

    override fun canUpgrade(): Boolean = false

    override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {
    }

    override fun upgrade() {
    }
}