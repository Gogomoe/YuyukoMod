package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.ConstrictedPower
import yuyuko.patches.CardColorEnum
import yuyuko.powers.GhostPower

class PhantomVillage : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Phantom Village"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = 4
        this.magicNumber = 4
    }

    override fun makeCopy(): AbstractCard = PhantomVillage()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        var count = 0
        AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .forEach {
                    count += it.getPower(ConstrictedPower.POWER_ID)?.amount ?: 0
                    AbstractDungeon.actionManager.addToBottom(
                            RemoveSpecificPowerAction(it, self, ConstrictedPower.POWER_ID)
                    )
                }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, count),
                        count
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, count / magicNumber)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(-1)
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}