package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum

class ExploreGhostdom : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Explore Ghostdom"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPDEAGE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = ExploreGhostdom()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        self!!
        val constructor: () -> AbstractCard =
                if (upgraded) { ->
                    Explore().apply { upgrade() }
                } else { ->
                    Explore()
                }
        val groups = listOf(self.hand, self.drawPile, self.discardPile)

        groups.forEach { group ->
            val toChange = group.group
                    .filter { it.rarity != CardRarity.SPECIAL && it != this }

            toChange.forEach {
                group.removeCard(it)
                group.addToBottom(constructor())
            }
        }

        self.hand.refreshHandLayout()
    }

    override fun upgrade() {
        upgradeName()
        this.rawDescription = UPDEAGE_DESCRIPTION
    }


}