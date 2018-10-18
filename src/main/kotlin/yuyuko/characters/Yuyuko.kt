package yuyuko.characters

import basemod.abstracts.CustomPlayer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.EnergyManager
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.screens.CharSelectInfo
import yuyuko.CharacterColor
import yuyuko.cards.yuyuko.Sakura
import yuyuko.patches.CardColorEnum
import yuyuko.patches.PlayerClassEnum
import yuyuko.relics.Coronal
import yuyuko.relics.Yuyukosfan


class Yuyuko(name: String) : CustomPlayer(
        name,
        PlayerClassEnum.YUYOKO,
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

    }

    init {
        this.initializeClass(
                null,
                "images/yuyuko/shoulder2.png",
                "images/yuyuko/shoulder.png",
                "images/yuyuko/corpse.png",
                getLoadout(),
                20.0f, -10.0f, 220.0f, 290.0f,
                EnergyManager(ENERGY)
        )
        this.loadAnimation("images/yuyuko/animate/animate.atlas", "images/yuyuko/animate/animate.json", 0.9f)
        val e = this.state.setAnimation(0, "Sprite", true)
        e.time = e.endTime * MathUtils.random()
        e.timeScale = .25f
    }

    private val characterColor = CharacterColor(CardColorEnum.YUYUKO_COLOR!!, "yuyuko", 227f, 48f, 255f)

    override fun getTitle(p0: PlayerClass?): String = NAME

    override fun getCardColor(): Color = characterColor.color

    override fun getStartCardForEvent(): AbstractCard = Sakura()

    override fun getCardTrailColor(): Color = characterColor.color

    override fun getAscensionMaxHPLoss(): Int = 3

    override fun getEnergyNumFont(): BitmapFont = FontHelper.energyNumFontGreen

    override fun doCharSelectScreenSelectEffect() {}

    override fun getCustomModeCharacterButtonSoundKey(): String = "ATTACK_MAGIC_BEAM_SHORT";

    override fun getLocalizedCharacterName(): String = NAME

    override fun newInstance(): AbstractPlayer = Yuyuko(this.name)

    override fun getStartingDeck(): ArrayList<String> {
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

    override fun getStartingRelics(): ArrayList<String> {
        return arrayListOf(Coronal.ID, Yuyukosfan.ID)
    }

    override fun getLoadout(): CharSelectInfo {
        return CharSelectInfo(
                NAME, DESCRIPTION,
                40, 40,
                0, 99, 5,
                this,
                startingRelics,
                startingDeck,
                false)
    }


}
