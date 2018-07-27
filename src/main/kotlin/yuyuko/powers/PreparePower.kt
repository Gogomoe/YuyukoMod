package yuyuko.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class PreparePower(ID: String, val toRun: () -> Unit) : AbstractPower() {


    init {
        this.ID = "Prepare $ID"
        val strings = CardCrawlGame.languagePack.getPowerStrings(this.ID)
        this.name = strings.NAME
        this.description = strings.DESCRIPTIONS[0]
        this.amount = -1
        this.updateDescription()

        this.owner = AbstractDungeon.player
        this.isTurnBased = true
        this.img = Texture("images/powers/prepare.png")
    }

    override fun atEndOfRound() {
        toRun()
        AbstractDungeon.actionManager.addToBottom(
                RemoveSpecificPowerAction(owner, owner, this)
        )
    }

}
