package demo

import basemod.BaseMod
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.helpers.CardHelper

class CharacterColor internal constructor(
        private val cardColor: AbstractCard.CardColor,
        private val name: String,
        private val r: Float,
        private val g: Float,
        private val b: Float) {

    val color = CardHelper.getColor(r, g, b)!!

    fun register() {
        BaseMod.addColor(cardColor.toString(),
                color, color, color, color, color, color, color,
                "images/512/cardui/bg_attack_$name.png",
                "images/512/cardui/bg_skill_$name.png",
                "images/512/cardui/bg_power_$name.png",
                "images/512/cardui/card_${name}_orb.png",
                "images/cardui/1024/bg_attack_$name.png",
                "images/cardui/1024/bg_skill_$name.png",
                "images/cardui/1024/bg_power_$name.png",
                "images/cardui/1024/card_${name}_orb.png")
    }

}
