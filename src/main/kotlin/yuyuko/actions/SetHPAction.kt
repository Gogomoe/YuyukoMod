package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.AbstractCreature

class SetHPAction(target: AbstractCreature, source: AbstractCreature, health: Int) : AbstractGameAction() {

    init {
        setValues(target, source, health)
        this.actionType = ActionType.SPECIAL
    }

    override fun update() {
        target.currentHealth = amount
        target.healthBarUpdatedEvent()
        isDone = true
    }

}