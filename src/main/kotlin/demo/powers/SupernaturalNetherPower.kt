package demo.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import demo.event.EventDispenser
import demo.event.Observer
import demo.event.PostDiaphaneityReduceEvent
import kotlin.math.max
import kotlin.math.min

class SupernaturalNetherPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Supernatural Nether"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = true
        this.img = Texture("images/powers/power.png")
    }

    private var observer: Observer<PostDiaphaneityReduceEvent>? = null

    override fun onInitialApplication() {
        observer = EventDispenser.subscribe(PostDiaphaneityReduceEvent.ID) {
            it.diaphaneityPower.amount = 20
        }
        onSpecificTrigger()
    }

    override fun onRemove() {
        EventDispenser.unsubscribe(PostDiaphaneityReduceEvent.ID, observer!!)
    }


    override fun reducePower(reduceAmount: Int) {
        super.reducePower(reduceAmount)
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(
                    RemoveSpecificPowerAction(this.owner, this.owner, this)
            )
        }
    }

    override fun stackPower(stackAmount: Int) {
        super.stackPower(stackAmount)
        onSpecificTrigger()
    }


    override fun onSpecificTrigger() {
        val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .toTypedArray()
        val all = listOf(AbstractDungeon.player, *monsters)
        all.forEach {
            val amount = it.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
            val toAdd = 20 - amount
            if (amount < 20) {
                AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                                it, owner,
                                DiaphaneityPower(it, toAdd),
                                toAdd
                        )
                )
            }
        }
    }

    override fun atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(owner, owner, this, 1)
        )
        if (this.amount - 1 != 0) {
            onSpecificTrigger()
        }
    }


    override fun updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

}
