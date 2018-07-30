package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import yuyuko.patches.CardColorEnum
import yuyuko.powers.FadePower
import kotlin.math.max

class FaramitasTemptation : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.BASIC, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Faramita's Temptation"
        val IMAGE_PATH = "images/yuyuko/cards/skill.png"
        val COST = -1
        val AMOUNT = 6
        val UPGRADE_PLUS_AMOUNT = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = AMOUNT
        this.magicNumber = AMOUNT
    }

    override fun makeCopy(): AbstractCard = FaramitasTemptation()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount
        }
        val turns = max(target!!.currentHealth / (this.energyOnUse + this.magicNumber), 1)
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, self,
                        FadePower(target, turns),
                        turns
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, self,
                        IntangiblePlayerPower(target, turns),
                        turns
                )
        )
        self!!.energy.use(EnergyPanel.totalCount)
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }

}