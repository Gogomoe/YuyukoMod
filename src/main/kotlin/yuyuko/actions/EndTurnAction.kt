package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class EndTurnAction : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.WAIT
    }

    override fun update() {
        AbstractDungeon.getCurrRoom().endTurn()
        this.isDone = true
    }

}