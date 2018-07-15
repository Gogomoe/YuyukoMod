package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class AllWanderAction: AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
    }

    override fun update() {
        AbstractDungeon.getCurrRoom().endTurn()
        this.isDone = true
    }

}