package demo.powers


import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.ConstrictedPower
import demo.getRandom
import kotlin.math.max
import kotlin.math.min

class GhostPower(owner: AbstractCreature, amount: Int) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Ghost"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = owner
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = true
        this.img = Texture("images/powers/power.png")
    }


    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    RemoveSpecificPowerAction(this.owner, this.owner, NAME)
            )
        }
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            return
        }
        val player = AbstractDungeon.player

        if (player.hasPower(BecomeGhostPower.POWER_ID)) {
            return
        }

        onSpecificTrigger()
    }

    override fun onSpecificTrigger() {
        val player = AbstractDungeon.player
        val cardsRemain = player.hand.group.size

        if (cardsRemain < 1) {
            return
        }
        this.flash()

        repeat(amount) {
            val monster = AbstractDungeon.getCurrRoom().monsters.monsters
                    .filter { !it.isDeadOrEscaped }
                    .getRandom() ?: return
            AbstractDungeon.actionManager.addToBottom(
                    DamageAction(
                            monster,
                            DamageInfo(
                                    AbstractDungeon.player,
                                    cardsRemain,
                                    DamageType.NORMAL
                            ),
                            AttackEffect.SLASH_DIAGONAL
                    )
            )
        }

    }

    override fun atStartOfTurnPostDraw() {
        val player = AbstractDungeon.player
        val monster = AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .getRandom()
        if (monster != null) {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            monster, player,
                            ConstrictedPower(monster, player, 1),
                            1
                    )
            )
            this.flash()
        }
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(player, player, POWER_ID, 1)
        )
    }


    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]
    }

}