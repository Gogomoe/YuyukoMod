package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.DiscoverAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower


class Explore : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.SPECIAL, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Explore"
        val IMAGE_PATH = "images/yuyuko/cards/explore.png"
        val COST = -2
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPDEAGE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }


    override fun makeCopy(): AbstractCard = Explore()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DiscoverAction(3)
        )
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            DiaphaneityPower(self!!, 1),
                            1
                    )
            )
        }

    }

    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            this.rawDescription = UPDEAGE_DESCRIPTION
            this.initializeDescription()
        }
    }


}