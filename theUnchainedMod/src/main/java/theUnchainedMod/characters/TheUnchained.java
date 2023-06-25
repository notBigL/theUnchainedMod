package theUnchainedMod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.*;
import theUnchainedMod.patches.CharacterSelectUIPatch;
import theUnchainedMod.relics.RustedChains;
import theUnchainedMod.vfx.ChainAcrossScreenEffect;
import theUnchainedMod.vfx.UnchainedVictoryVFX;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.*;
import static theUnchainedMod.characters.TheUnchained.Enums.COLOR_ORANGE;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class TheUnchained extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(TheUnchainedMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_UNCHAINED;
        @SpireEnum(name = "UNCHAINED_ORANGE_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_ORANGE;
        @SpireEnum(name = "UNCHAINED_ORANGE_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
        @SpireEnum(name = "UNCHAINED_BOOSTER_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_BOOSTER;
        @SpireEnum(name = "UNCHAINED_BOOSTER_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_BOOSTER_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("UnchainedCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /END EFFECT/ =================

    boolean endEffectStarted;


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer1.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer2.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer3.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer4.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer5.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer6.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer1d.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer2d.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer3d.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer4d.png",
            "theUnchainedModResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public TheUnchained(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theUnchainedModResources/images/char/defaultCharacter/orb/vfx.png", new float[]{-20.0F, 20.0F, -40.0F, 40.0F, 0.0F},
                new SpineAnimation(THE_UNCHAINED_SKELETON_ATLAS, THE_UNCHAINED_SKELETON_JSON, 1.0f));


        // =============== TEXTURES, ENERGY, LOADOUT =================  
        if(!UNCHAINED_SKIN_ACTIVATED) {
            initializeClass(THE_DEFAULT_CHARACTER, // required call to load textures and setup energy/loadout.
                    // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                    THE_DEFAULT_SHOULDER_2, // campfire pose
                    THE_DEFAULT_SHOULDER_1, // another campfire pose
                    THE_DEFAULT_CORPSE, // dead corpse
                    getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager
        }
        else {
            initializeClass(PRINCE_UNBOUND_CHARACTER, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                PRINCE_UNBOUND_SHOULDER_2, // campfire pose
                PRINCE_UNBOUND_SHOULDER_1, // another campfire pose
                PRINCE_UNBOUND_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        }

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

        if(UNCHAINED_SKIN_ACTIVATED) loadAnimation(THE_UNCHAINED_PRINCE_UNBOUND_SKELETON_ATLAS, THE_UNCHAINED_PRINCE_UNBOUND_JSON, 1.0f);
        else                         loadAnimation(THE_UNCHAINED_SKELETON_ATLAS, THE_UNCHAINED_SKELETON_JSON, 1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTimeScale(1.0f);

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(Whiplash.ID);
        retVal.add(DefensiveLink.ID);
        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(RustedChains.ID);
        UnlockTracker.markRelicAsSeen(RustedChains.ID);

        return retVal;
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel(makeComicPath("Unchained_comic_1.png")));
        panels.add(new CutscenePanel(makeComicPath("Unchained_comic_2.png")));
        panels.add(new CutscenePanel(makeComicPath("Unchained_comic_3.png")));
        return panels;
    }

    @Override
    public Texture getCutsceneBg() {
        return loadImage(makeComicPath("Unchained_comic_bg.png"));
    }


    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("unchainedSelect", MathUtils.random(-0.2F, 0.2F)); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "unchainedSelect";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_ORANGE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return TheUnchainedMod.UNCHAINED_ORANGE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new DefensiveLink();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheUnchained(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return TheUnchainedMod.UNCHAINED_ORANGE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return TheUnchainedMod.UNCHAINED_ORANGE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
    if(!endEffectStarted)
    {
        effects.add(new UnchainedVictoryVFX());

        effects.add(new ChainAcrossScreenEffect(Color.GOLD, 0,Settings.HEIGHT * 0.72f, MathUtils.random(4, 6), MathUtils.random(55,75)));
        effects.add(new ChainAcrossScreenEffect(Color.GOLD, Settings.WIDTH * 0.21f, Settings.HEIGHT, MathUtils.random(4, 6), MathUtils.random(230,250)));
        effects.add(new ChainAcrossScreenEffect(Color.GOLD, Settings.WIDTH * 0.25f,0, MathUtils.random(4, 6), MathUtils.random(130,150)));
        effects.add(new ChainAcrossScreenEffect(Color.GOLD, Settings.WIDTH * 0.83f, Settings.HEIGHT, MathUtils.random(4, 6), MathUtils.random(290,310)));
        effects.add(new ChainAcrossScreenEffect(Color.GOLD, Settings.WIDTH * 0.86f,0, MathUtils.random(4, 6), MathUtils.random(45,65)));

        endEffectStarted = true;

        unchainedConfig.setBool(UNCHAINED_SKIN_UNLOCKED_PROPERTY, true);
        unchainedConfig.setBool(UNCHAINED_BOOSTER_PACK_UNLOCKED_PROPERTY, true);
        TheUnchainedMod.UNCHAINED_SKIN_UNLOCKED = true;
        TheUnchainedMod.UNCHAINED_BOOSTER_PACK_UNLOCKED = true;
        try
        {
            TheUnchainedMod.unchainedConfig.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
}
