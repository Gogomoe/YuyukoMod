package demo.cards.yuyuko

import com.megacrit.cardcrawl.cards.AbstractCard

fun AbstractCard.isHide(): Boolean =
        when {
            this.cardID == ReverseTheScreen.ID && this.upgraded -> true
            else -> false
        }