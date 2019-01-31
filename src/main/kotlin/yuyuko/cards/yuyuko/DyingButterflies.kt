package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.event.DegradeEvent
import yuyuko.event.DegradeEvent.DegradeReason.USE
import yuyuko.event.EventDispenser
import yuyuko.patches.CardColorEnum
import yuyuko.powers.GhostPower

class DyingButterflies : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Dying Butterflies"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = 1
        this.magicNumber = 1
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = DyingButterflies()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, this.magicNumber),
                        this.magicNumber
                )
        )
    }

    override fun canUpgrade(): Boolean = this.timesUpgraded < 4

    override fun upgrade() {
        if (this.timesUpgraded < 4) {
            this.upgradeName()
            this.upgradeMagicNumber(1)
        }
    }

    override fun upgradeName() {
        ++this.timesUpgraded
        this.upgraded = true
        this.name = "$NAME+$timesUpgraded"
        this.initializeTitle()
    }
}