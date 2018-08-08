package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.RetrieveAction
import yuyuko.cards.isButterfly
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum

class FondlingOfPapilio : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Fondling of Papilio"
        val IMAGE_PATH = "images/yuyuko/cards/skill4.png"
        val COST = 1
        val BUTTERFLY_AMOUNT = 1
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = BUTTERFLY_AMOUNT
        this.magicNumber = BUTTERFLY_AMOUNT
    }

    override fun makeCopy(): AbstractCard = FondlingOfPapilio()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        repeat(this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(
                    RetrieveAction(AbstractCard::isButterfly)
            )
        }
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isButterfly, this.magicNumber))
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }

}