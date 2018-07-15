package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.ConstrictedPower
import demo.actions.HideAction
import demo.patches.CardColorEnum
import demo.powers.GhostPower

class DyingDream : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Dying Dream"
        val IMAGE_PATH = "images/yuyuko/cards/attack5.png"
        val COST = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = DyingDream()

    override fun calculateCardDamage(mo: AbstractMonster?) {
        val amount = AbstractDungeon.player.getPower(GhostPower.POWER_ID)?.amount ?: 0
        this.baseDamage = amount * 10
        super.calculateCardDamage(mo)
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        self, this.multiDamage, NORMAL, SLASH_VERTICAL
                )
        )
        val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
        val amount = AbstractDungeon.player.getPower(GhostPower.POWER_ID)?.amount ?: 0

        monsters.forEach {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            it, self,
                            ConstrictedPower(it, self, amount),
                            amount * 10
                    )
            )
        }
        AbstractDungeon.actionManager.addToBottom(
                RemoveSpecificPowerAction(self, self, GhostPower.POWER_ID)
        )

        AbstractDungeon.player.getPower(GhostPower.POWER_ID)?.flash()
    }

    override fun triggerWhenDrawn() {
        if (!upgraded) {
            return
        }
        val player = AbstractDungeon.player
        val drawPile = AbstractDungeon.player.drawPile.group
        val remain = drawPile.filter { it.isHide() }
        if (drawPile.size != remain.size) {
            AbstractDungeon.actionManager.addToBottom(
                    HideAction(this)
            )
            AbstractDungeon.actionManager.addToBottom(
                    DrawCardAction(player, 1)
            )
        }
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }

}