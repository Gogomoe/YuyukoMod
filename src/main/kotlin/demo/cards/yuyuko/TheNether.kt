package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower

class TheNether : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "The Nether"
        val IMAGE_PATH = "images/yuyuko/cards/attack3.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.isEthereal = true
    }


    override fun makeCopy(): AbstractCard = TheNether()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .forEach {
                    val amount = it.getPower(DiaphaneityPower.POWER_ID)?.amount ?: return@forEach
                    AbstractDungeon.actionManager.addToBottom(
                            DamageAction(
                                    it,
                                    DamageInfo(self, amount, NORMAL),
                                    SLASH_HEAVY
                            )
                    )
                }
        val amount = self!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: return
        AbstractDungeon.actionManager.addToBottom(
                HealAction(self, self, amount)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.isEthereal = false
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}