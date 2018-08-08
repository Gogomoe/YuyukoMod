package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import yuyuko.cards.isSakura
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.CARD
import yuyuko.event.EventDispenser
import yuyuko.hasEnoughPower
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower
import yuyuko.powers.FanPower
import yuyuko.setCantUseMessage

class SnowingSakura : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Snowing Sakura"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
        val EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun canUse(self: AbstractPlayer?, target: AbstractMonster?): Boolean =
            super.canUse(self, target) && self!!.hasEnoughPower(FanPower.POWER_ID).also {
                setCantUseMessage(it, EXTENDED_DESCRIPTION[0])
            }


    override fun makeCopy(): AbstractCard = SnowingSakura()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(
                        self, self,
                        FanPower.POWER_ID,
                        1
                )
        )
        var count = countSakura()
        EventDispenser.emit(ApplyDiaphaneityPowerEvent(self!!, self, count, CARD))

        count += self.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        AbstractDungeon.actionManager.addToBottom(
                GainEnergyAction(count / 5)
        )
        count = count / 5 + EnergyPanel.totalCount
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(SakuraBloom(), count / 3, true, true)
        )
        if (upgraded) {
            degrade()
        }
    }


    private fun countSakura(): Int {
        val player = AbstractDungeon.player
        val groups = listOf(player.hand, player.drawPile, player.discardPile)
        return groups
                .map {
                    it.group.filter { it.isSakura() }.size
                }
                .reduce { acc, i -> acc + i }
    }

    private fun degrade() {
        this.upgraded = false
        this.name = NAME
        this.timesUpgraded = 0
        this.rawDescription = DESCRIPTION
        AbstractDungeon.actionManager.addToBottom(
                object : AbstractGameAction() {
                    override fun update() {
                        this.isDone = true
                        this@SnowingSakura.exhaust = true
                    }
                }
        )
        this.initializeDescription()
        this.initializeTitle()
    }


    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            this.exhaust = false
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }

}