package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.cards.isSpecial
import yuyuko.collect
import yuyuko.patches.CardColorEnum
import yuyuko.powers.ReunionAfterDeathPower
import yuyuko.reduce

class ReunionAfterDeath : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Reunion after Death"
        val IMAGE_PATH = "images/yuyuko/cards/power.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = ReunionAfterDeath()

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean {
        if (!super.canUse(self, target)) {
            return false
        }
        val list = listOf(self!!.hand, self.drawPile, self.discardPile)
                .map { it.group.filter { it.rarity != BASIC && !it.isSpecial() } }
                .collect()
                .map { it.cardID }
        if (list.size == list.toSet().size) {
            return true
        }
        this.cantUseMessage = EXTENDED_DESCRIPTION[0]
        return false
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        listOf(self!!.hand, self.drawPile, self.discardPile)
                /**
                 * Type: List<Group to List<Card>>
                 */
                .map { it to it.group.filter { it.rarity == BASIC || it.isSpecial() } }
                /**
                 * Type: List<Group to Card>
                 */
                .reduce(mutableListOf<Pair<CardGroup, AbstractCard>>()) { acc, (group, list) ->
                    val cards = list.map { group to it }
                    acc.apply { addAll(cards) }
                }
                .forEach { (group, card) ->
                    AbstractDungeon.actionManager.addToBottom(
                            ExhaustSpecificCardAction(card, group)
                    )
                }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        ReunionAfterDeathPower(),
                        1
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.isInnate = true
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}