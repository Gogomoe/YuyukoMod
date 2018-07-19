package demo.cards.yuyuko

import com.megacrit.cardcrawl.cards.AbstractCard

fun AbstractCard.isHide(): Boolean =
        when {
            this.cardID == ReverseTheScreen.ID && this.upgraded -> true
            this.cardID == DyingDream.ID && this.upgraded -> true
            this.cardID == InfiniteSin.ID && this.upgraded -> true
            else -> false
        }