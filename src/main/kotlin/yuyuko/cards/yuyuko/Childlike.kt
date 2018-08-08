package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.ReviveAction
import yuyuko.cards.isButterfly
import yuyuko.cards.isSakura
import yuyuko.cards.isSpecial
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum

class Childlike : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Childlike"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = Childlike()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        self!!.hand.group
                .filter { it.isSpecial() }
                .forEach {
                    AbstractDungeon.actionManager.addToBottom(
                            ExhaustSpecificCardAction(it, self.hand)
                    )
                }
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isSakura))
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isButterfly))
        if (!self.exhaustPile.isEmpty) {
            AbstractDungeon.actionManager.addToBottom(
                    ReviveAction { true }
            )
        }

    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }

}