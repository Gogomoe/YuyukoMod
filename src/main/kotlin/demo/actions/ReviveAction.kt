package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.cards.isButterfly
import demo.cards.isSakura
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.Sakura

class ReviveAction(val cardID: String) : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = CARD_MANIPULATION
    }

    override fun update() {
        val player = AbstractDungeon.player

        val group = AbstractDungeon.player.exhaustPile
        val list = group.group

        val condition = when (cardID) {
            Butterfly.ID -> AbstractCard::isButterfly
            Sakura.ID -> AbstractCard::isSakura
            else -> ({ card: AbstractCard -> card.cardID == cardID } as (AbstractCard) -> Boolean)
        }

        val card = list.find(condition)

        if (card != null) {
            card.unfadeOut()
            group.moveToHand(card, group)
        }

        this.isDone = true
    }

}