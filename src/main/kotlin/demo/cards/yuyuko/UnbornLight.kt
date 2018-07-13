package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower

class UnbornLight : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Unborn Light"
        val IMAGE_PATH = "images/yuyuko/cards/attack3.png"
        val COST = 2
        val ATTACK_DMG = 6
        val UPGRADE_PLUS_DMG = 4
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = ATTACK_DMG
    }

    override fun makeCopy(): AbstractCard = UnbornLight()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }

        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        self, this.multiDamage, NORMAL, AttackEffect.SLASH_DIAGONAL
                )
        )
        monsters.forEach {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            it, self,
                            DiaphaneityPower(it, this.damage),
                            this.damage
                    )
            )
        }

    }

    override fun upgrade() {
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

}