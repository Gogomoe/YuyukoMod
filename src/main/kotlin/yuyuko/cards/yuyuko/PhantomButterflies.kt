package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.ChangeAllAction
import yuyuko.patches.CardColorEnum

class PhantomButterflies : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Phantom Butterflies"
        val IMAGE_PATH = "images/yuyuko/cards/power.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = PhantomButterflies()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {

        val condition: (AbstractCard) -> Boolean = { it.cardID == Butterfly.ID }
        val toChange = listOf(::ButterflyDelusion)

        AbstractDungeon.actionManager.addToBottom(
                ChangeAllAction(condition, toChange)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            upgradeBaseCost(0)
        }
    }

}