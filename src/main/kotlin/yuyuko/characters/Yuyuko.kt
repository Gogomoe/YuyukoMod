package yuyuko.characters

import basemod.abstracts.CustomPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.EnergyManager
import com.megacrit.cardcrawl.screens.CharSelectInfo
import yuyuko.patches.PlayerClassEnum
import yuyuko.relics.Yuyukosfan


class Yuyuko(name: String, setClass: PlayerClass) : CustomPlayer(
        name,
        setClass,
        orbTextures,
        "images/yuyuko/orb/vfx.png",
        null as String?,
        null as String?
) {
    companion object {
        val CHARACTER_STRINGS = CardCrawlGame.languagePack.getCharacterString("Yuyuko")
        val NAME = CHARACTER_STRINGS.NAMES[0]!!
        val DESCRIPTION = CHARACTER_STRINGS.TEXT[0]!!
        val ENERGY = 3
        val orbTextures = arrayOf(
                "images/yuyuko/orb/layer1.png",
                "images/yuyuko/orb/layer2.png",
                "images/yuyuko/orb/layer3.png",
                "images/yuyuko/orb/layer4.png",
                "images/yuyuko/orb/layer5.png",
                "images/yuyuko/orb/layer6.png",
                "images/yuyuko/orb/layer1d.png",
                "images/yuyuko/orb/layer2d.png",
                "images/yuyuko/orb/layer3d.png",
                "images/yuyuko/orb/layer4d.png",
                "images/yuyuko/orb/layer5d.png"
        )


        @JvmStatic
        fun getLoadout(): CharSelectInfo {
            return CharSelectInfo(
                    NAME, DESCRIPTION,
                    40, 40,
                    0, 99, 5,
                    PlayerClassEnum.YUYOKO,
                    getStartingRelics(),
                    getStartingDeck(),
                    false)
        }

        @JvmStatic
        fun getStartingRelics(): ArrayList<String> {
            return arrayListOf(Yuyukosfan.ID)
        }

        @JvmStatic
        fun getStartingDeck(): ArrayList<String> {
            val list = arrayListOf<String>()
            list.add("Fantasy Butterflies")
            list.add("Fantasy Butterflies")
            list.add("Fantasy Butterflies")
            list.add("Fantasy Butterflies")
            list.add("Fantasy Butterflies")
            list.add("Dreamy Sakura")
            list.add("Dreamy Sakura")
            list.add("Dreamy Sakura")
            list.add("Dreamy Sakura")
            list.add("Dreamy Sakura")
            list.add("Deathly Ground")
            list.add("Faramita's Temptation")
            return list
        }
    }

    init {
        this.initializeClass(
                "images/yuyuko/main.png",
                "images/yuyuko/shoulder2.png",
                "images/yuyuko/shoulder.png",
                "images/yuyuko/corpse.png",
                getLoadout(),
                20.0f, -10.0f, 220.0f, 290.0f,
                EnergyManager(ENERGY)
        )
    }


}
