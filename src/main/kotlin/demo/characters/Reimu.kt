package demo.characters

import basemod.abstracts.CustomPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.EnergyManager
import com.megacrit.cardcrawl.screens.CharSelectInfo
import demo.CharacterColor
import demo.cards.reimu.Strike_Reimu
import demo.cards.reimu.Strike_Reimu.Companion
import demo.patches.CardColorEnum
import demo.patches.PlayerClassEnum

class Reimu(name: String, setClass: PlayerClass) : CustomPlayer(
        name,
        setClass,
        orbTextures,
        "images/reimu/orb/vfx.png",
        null as String?,
        null as String?
) {
    companion object {
        val CHARACTER_STRINGS = CardCrawlGame.languagePack.getCharacterString("Reimu")
        val NAME = CHARACTER_STRINGS.NAMES[0]!!
        val DESCRIPTION = CHARACTER_STRINGS.TEXT[0]!!
        val ENERGY = 3
        val orbTextures = arrayOf(
                "images/reimu/orb/layer1.png",
                "images/reimu/orb/layer2.png",
                "images/reimu/orb/layer3.png",
                "images/reimu/orb/layer4.png",
                "images/reimu/orb/layer5.png",
                "images/reimu/orb/layer6.png",
                "images/reimu/orb/layer1d.png",
                "images/reimu/orb/layer2d.png",
                "images/reimu/orb/layer3d.png",
                "images/reimu/orb/layer4d.png",
                "images/reimu/orb/layer5d.png"
        )


        @JvmStatic
        fun getLoadout(): CharSelectInfo {
            return CharSelectInfo(
                    NAME, DESCRIPTION,
                    80, 80,
                    0, 80, 5,
                    PlayerClassEnum.REIMU,
                    getStartingRelics(),
                    getStartingDeck(),
                    false)
        }

        @JvmStatic
        fun getStartingRelics(): ArrayList<String> {
            return arrayListOf()
        }

        @JvmStatic
        fun getStartingDeck(): ArrayList<String> {
            val list = arrayListOf<String>()
            list.add("Strike_Reimu")
            list.add("Strike_Reimu")
            list.add("Strike_Reimu")
            list.add("Strike_Reimu")
            list.add("Strike_Reimu")
            return list
        }
    }

    init {
        this.initializeClass(
                "images/reimu/main.png",
                "images/reimu/shoulder2.png",
                "images/reimu/shoulder.png",
                "images/reimu/corpse.png",
                getLoadout(),
                20.0f, -10.0f, 220.0f, 290.0f,
                EnergyManager(ENERGY)
        )
    }


}
