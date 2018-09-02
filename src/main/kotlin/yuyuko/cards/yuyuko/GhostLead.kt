package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.ChangeAllAction
import yuyuko.patches.CardColorEnum

class GhostLead : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.POWER, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Ghost Lead"
        val IMAGE_PATH = "images/yuyuko/cards/power2.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = GhostLead()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        listOf(
                CardRarity.UNCOMMON to CardRarity.RARE,
                CardRarity.COMMON to CardRarity.UNCOMMON,
                CardRarity.BASIC to CardRarity.COMMON
        ).forEach { (base, upgrade) ->

            val find: (AbstractCard) -> Boolean = { it.rarity == base }
            val changeTo = { AbstractDungeon.getCard(upgrade) }

            AbstractDungeon.actionManager.addToBottom(
                    ChangeAllAction(find, changeTo)
            )
        }

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