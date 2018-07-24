package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.ChangeAllAction
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

        val condition: (AbstractCard) -> Boolean = { it.cardID == Sakura.ID }
        val toChange = listOf(::SakuraBloom)

        AbstractDungeon.actionManager.addToBottom(
                ChangeAllAction(condition, toChange)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}