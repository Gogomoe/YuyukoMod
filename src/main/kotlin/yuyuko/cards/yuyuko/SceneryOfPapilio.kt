package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.HideAction
import yuyuko.cards.HideCards
import yuyuko.cards.isSpecial
import yuyuko.patches.CardColorEnum
import yuyuko.reduce

class SceneryOfPapilio : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Scenery of Papilio"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 2
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!

    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = SceneryOfPapilio()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val player = AbstractDungeon.player
        val groups = listOf(player.hand, player.drawPile, player.discardPile)

        groups
                .map {
                    it.group.filter(AbstractCard::isSpecial)
                }
                .reduce(mutableListOf<AbstractCard>()) { s, t ->
                    s.also { it.addAll(t) }
                }
                .forEach {
                    AbstractDungeon.actionManager.addToBottom(
                            MakeTempCardInDrawPileAction(it, 1, true, true)
                    )
                }
    }

    override fun triggerWhenDrawn() {
        if (!upgraded) {
            return
        }
        if (HideCards.shouldHide()) {
            AbstractDungeon.actionManager.addToTop(
                    HideAction(this)
            )
        }
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }

}