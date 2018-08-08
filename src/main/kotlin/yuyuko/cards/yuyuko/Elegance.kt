package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.cards.isSakura
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.CARD
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum
import yuyuko.powers.FanPower

class Elegance : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Elegance"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPDEAGE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = Elegance()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    MakeTempCardInDrawPileAction(
                            Sakura(), 1, true, true
                    )
            )
        }
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isSakura))

        val amount = self!!.getPower(FanPower.POWER_ID)?.amount ?: 0
        EventDispenser.emit(ApplyDiaphaneityPowerEvent(self, self, amount, CARD))

    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.rawDescription = UPDEAGE_DESCRIPTION
            this.initializeDescription()
        }
    }

}