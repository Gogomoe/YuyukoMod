package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum

class SakuraWard : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Sakura Ward"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        val BLOCK_AMOUNT = 5
        val UPGRADE_PLUS_BLOCK = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPDEAGE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!

    }

    init {
        this.baseBlock = BLOCK_AMOUNT
    }

    override fun makeCopy(): AbstractCard = SakuraWard()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                GainBlockAction(self, self, this.block)
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        SakuraSeal(), 1, true, true
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            upgradeBlock(UPGRADE_PLUS_BLOCK)
            this.isInnate = true
            this.rawDescription = UPDEAGE_DESCRIPTION
            this.initializeDescription()
        }
    }


}