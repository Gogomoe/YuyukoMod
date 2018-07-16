package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.addToRandomSpotIfIsDrawPile
import demo.patches.CardColorEnum

class UnfulfillingAttachment : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Unfulfilling Attachment"
        val IMAGE_PATH = "images/yuyuko/cards/power2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }


    override fun makeCopy(): AbstractCard = UnfulfillingAttachment()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        self!!

        val constructor: (Int) -> AbstractCard = { shouldUpgradeTimes ->
            SakuraBloom().apply {
                repeat(shouldUpgradeTimes) {
                    upgrade()
                }
            }
        }

        val groups = listOf(self.hand, self.drawPile, self.discardPile)

        groups.forEach { group ->
            val toChange = group.group
                    .filter { it.cardID == Sakura.ID }

            toChange.forEach {
                group.removeCard(it)
                val card = constructor(it.timesUpgraded)
                group.addToRandomSpotIfIsDrawPile(card)
            }
        }
        self.hand.refreshHandLayout()
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}