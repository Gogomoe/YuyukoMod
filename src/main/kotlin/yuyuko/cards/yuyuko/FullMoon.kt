package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.RegenPower
import yuyuko.cards.isSakura
import yuyuko.patches.CardColorEnum

class FullMoon : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Full Moon"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = FullMoon()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        var count = 0
        listOf(self!!.hand, self.drawPile, self.discardPile)
                .map {
                    it to it.group.filter(AbstractCard::isSakura)
                }
                .forEach { (group, cards) ->
                    cards.forEach {
                        count++
                        AbstractDungeon.actionManager.addToBottom(
                                ExhaustSpecificCardAction(it, group)
                        )
                    }
                }

        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        RegenPower(self, count),
                        count
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}