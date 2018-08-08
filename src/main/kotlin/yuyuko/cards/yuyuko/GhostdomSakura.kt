package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.cards.isSakura
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum

class GhostdomSakura : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Ghostdom Sakura"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }


    override fun makeCopy(): AbstractCard = GhostdomSakura()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        SakuraSuicide(), 1, true, true
                )
        )
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isSakura))

    }

    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            upgradeBaseCost(0)
        }
    }


}