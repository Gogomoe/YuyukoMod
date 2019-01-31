package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.cards.isButterfly
import yuyuko.cards.isSakura
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.getRandom
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower
import yuyuko.powers.GhostPower

class MiniGhostdom : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Mini Ghostdom"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = MiniGhostdom()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                GainEnergyAction(1)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, 1),
                        1
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        DiaphaneityPower(self, 1),
                        1
                )
        )
        val sakura = listOf(::Sakura, ::SakuraBloom, ::SakuraDormancy,
                ::SakuraSeal, ::SakuraSuicide).getRandom()
        val butterfly = listOf(::Butterfly, ::ButterflyDeepRooted,
                ::ButterflyDelusion, ::ButterflyGhost, ::ButterflySwallowtail).getRandom()

        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(sakura!!(), 1, true, true)
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(butterfly!!(), 1, true, true)
        )
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isSakura))
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isButterfly))
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, 1)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.exhaust = false
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}