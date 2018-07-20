package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.event.DegradeEvent
import demo.event.EventDispenser
import demo.getRandom
import demo.patches.CardColorEnum

class ButterfliesRainbow : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Butterflies Rainbow"
        val IMAGE_PATH = "images/yuyuko/cards/skill4.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = ButterfliesRainbow()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val cards = listOf(
                ButterflyDeepRooted(),
                ButterflyDelusion(),
                ButterflyGhost(),
                ButterflySwallowtail()
        )
        val card = cards.getRandom()!!
        repeat(timesUpgraded) {
            card.upgrade()
        }
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(card, 1, true, true)
        )

        EventDispenser.emit(DegradeEvent(this, this::degradeToInitiation))
    }


    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
        upgradeBaseCost(0)
    }

    override fun upgradeName() {
        ++this.timesUpgraded
        this.upgraded = true
        this.name = "$NAME+$timesUpgraded"
        this.initializeTitle()
    }

    fun degradeToInitiation() {
        this.upgraded = false
        this.name = NAME
        this.timesUpgraded = 0
        this.initializeTitle()
    }

}