package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.event.DegradeEvent
import demo.event.DegradeEvent.DegradeReason.USE
import demo.event.EventDispenser
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower

class SakuraDormancy : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.STATUS, CardColorEnum.YUYUKO_COLOR,
        CardRarity.SPECIAL, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Sakura (Dormancy)"
        val IMAGE_PATH = "images/yuyuko/cards/sakura.png"
        val COST = -2
        val HEAL_AMOUNT = 1
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = HEAL_AMOUNT
        this.magicNumber = HEAL_AMOUNT
    }

    override fun makeCopy(): AbstractCard = SakuraDormancy()

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean =
            this.cardPlayable(m) && this.hasEnoughEnergy()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                HealAction(self, self, this.magicNumber)
        )
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, 1, false)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        DiaphaneityPower(self!!, 2),
                        2
                )
        )

        EventDispenser.emit(DegradeEvent(this, USE, this::degradeToInitiation))

    }

    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
        upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
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
        this.baseMagicNumber -= UPGRADE_PLUS_AMOUNT * this.timesUpgraded
        this.upgradedMagicNumber = false
        this.timesUpgraded = 0
        this.initializeTitle()
    }

}