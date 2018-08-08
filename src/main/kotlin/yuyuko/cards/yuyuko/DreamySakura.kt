package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.cards.isSakura
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.CARD
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum

class DreamySakura : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.BASIC, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Dreamy Sakura"
        val IMAGE_PATH = "images/yuyuko/cards/skill.png"
        val COST = 1
        val DIAPHANEITY_AMOUNT = 2
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = DIAPHANEITY_AMOUNT
        this.magicNumber = DIAPHANEITY_AMOUNT
    }

    override fun makeCopy(): AbstractCard = DreamySakura()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        EventDispenser.emit(ApplyDiaphaneityPowerEvent(self!!, self, this.magicNumber, CARD))

        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isSakura))
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }

}