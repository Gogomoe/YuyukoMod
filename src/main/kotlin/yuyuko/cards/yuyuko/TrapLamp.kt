package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.GhostPower
import yuyuko.powers.TrapLampPower

class TrapLamp : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Trap Lamp"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = 1
        this.magicNumber = 1
        this.exhaust = true
        this.isEthereal = true
    }

    override fun makeCopy(): AbstractCard = TrapLamp()

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean {
        if (!super.canUse(p, m)) {
            return false
        }
        val amount = AbstractDungeon.player.getPower(GhostPower.POWER_ID)?.amount ?: 0
        if (amount < magicNumber) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0]
            return false
        }
        return true
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(
                        self, self,
                        GhostPower.POWER_ID,
                        magicNumber
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(ButterflyGhost(), magicNumber, true, true)
        )

        val card = makeStatEquivalentCopy() as TrapLamp
        card.upgradeMagicNumber(1)

        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        TrapLampPower(card)
                )
        )
    }

    override fun makeStatEquivalentCopy(): AbstractCard = super.makeStatEquivalentCopy().also {
        it.magicNumber = this.magicNumber
        it.baseMagicNumber = this.baseMagicNumber
        it.upgradedMagicNumber = this.upgradedMagicNumber
    }

    override fun upgrade() {
        if (!upgraded) {
            this.isEthereal = false
            upgradeName()
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }

}