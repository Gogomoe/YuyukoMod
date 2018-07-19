package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.powers.DiaphaneityPower

class DeathDancingAction(target: AbstractCreature, source: AbstractCreature) : AbstractGameAction() {

    init {
        this.target = target
        this.source = source
        this.actionType = DAMAGE
    }

    override fun update() {
        this.isDone = true
        val damage = target.getPower(DiaphaneityPower.POWER_ID)?.amount ?: return

        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(
                                source, damage, DamageType.NORMAL
                        ),
                        AttackEffect.BLUNT_LIGHT
                )
        )
    }

}