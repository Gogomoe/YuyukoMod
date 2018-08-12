package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.event.DegradeEvent
import yuyuko.event.DegradeEvent.DegradeReason.USE
import yuyuko.event.EventDispenser
import yuyuko.patches.CardColorEnum

class DyingSakura : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Dying Sakura"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = DyingSakura()


    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        if (timesUpgraded >= 3) {
            AbstractDungeon.actionManager.addToBottom(
                    MakeTempCardInHandAction(Soul(), timesUpgraded / 3)
            )
        }

        EventDispenser.emit(DegradeEvent(this, USE, this::degradeToInitiation))

    }

    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
    }

    override fun upgradeName() {
        ++this.timesUpgraded
        this.upgraded = true
        this.name = "$NAME+$timesUpgraded"
        this.initializeTitle()
    }

    private fun degradeToInitiation() {
        this.upgraded = false
        this.name = NAME
        this.timesUpgraded = 0
        this.initializeTitle()
    }

}