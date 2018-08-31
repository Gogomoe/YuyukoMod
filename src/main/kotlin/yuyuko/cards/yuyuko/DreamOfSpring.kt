package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DreamOfSpringPower
import yuyuko.powers.PreparePower

class DreamOfSpring : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.ALL
) {
    companion object {
        @JvmStatic
        val ID = "Dream of Spring"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.magicNumber = 1
        this.baseMagicNumber = 1
    }

    override fun makeCopy(): AbstractCard = DreamOfSpring()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val toRun = {
            val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
                    .filter { !it.isDeadOrEscaped }
            listOf(self!!, *monsters.toTypedArray()).forEach {
                AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                                it, self,
                                DreamOfSpringPower(it, this.magicNumber),
                                this.magicNumber
                        )
                )
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        PreparePower(ID, toRun)
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(1)
        }
    }

}