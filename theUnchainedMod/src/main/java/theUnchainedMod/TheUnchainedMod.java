package theUnchainedMod;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theUnchainedMod.booster_pack_cards.ArcaneArtillery;
import theUnchainedMod.cards.*;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.events.TheDemonSpeaksEvent;
import theUnchainedMod.patches.CharacterSelectUIPatch;
import theUnchainedMod.potions.DancePotion;
import theUnchainedMod.potions.ChainGrease;
import theUnchainedMod.potions.CrushingElixir;
import theUnchainedMod.relics.*;
import theUnchainedMod.util.IDCheckDontTouchPls;
import theUnchainedMod.util.TextureLoader;
import theUnchainedMod.variables.DefaultCustomVariable;
import theUnchainedMod.variables.DefaultSecondMagicNumber;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 4 places.
// I comment those places below, under the place where you set your ID.

// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class TheUnchainedMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        AddAudioSubscriber,
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(TheUnchainedMod.class.getName());
    private static String modID;
    private static final String MODNAME = "The Unchained Mod";
    private static final String AUTHOR = "Mezix & BigL"; // And pretty soon - You!
    private static final String DESCRIPTION = "A new Character with 75+ new cards, 10+ new Relics, 3 new Potions and 4 distinct archetypes you can play around with and have a lot of fun!";


    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
            Settings.GameLanguage.SPA,
            //TODO: Activate german language
            // Settings.GameLanguage.DEU
    };


    // CONFIG
    public static SpireConfig unchainedConfig;
    static {
        try {
            unchainedConfig = new SpireConfig("The Unchained", "config");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties theUnchainedDefaultSettings = new Properties();

        //  Unlock all Optional Content
        public static final String UNCHAINED_OPTIONAL_CONTENT_UNLOCKED_PROPERTY = "PrinceUnboundSkinUnlocked";
        public static boolean UNCHAINED_OPTIONAL_CONTENT_UNLOCKED = false;
        //  Prince Unbound Skin
        public static final String UNCHAINED_SKIN_UNLOCKED_PROPERTY = "PrinceUnboundSkinUnlocked";
        public static boolean UNCHAINED_SKIN_UNLOCKED = false;
        public static final String UNCHAINED_SKIN_ACTIVATED_PROPERTY = "PrinceUnboundSkinActivated";
        public static boolean PRINCE_UNBOUND_SKIN_ACTIVATED = false;

        //  Booster Pack
        public static final String UNCHAINED_BOOSTER_PACK_UNLOCKED_PROPERTY = "BoosterPackUnlocked";
        public static boolean UNCHAINED_BOOSTER_PACK_UNLOCKED = false;
        public static final String UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY = "BoosterPackActivated";
        public static boolean UNCHAINED_BOOSTER_PACK_ACTIVATED = false;

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color UNCHAINED_ORANGE = CardHelper.getColor(255.0f, 170.0f, 17.0f);
    public static final Color UNCHAINED_BOOSTER_COLOR = CardHelper.getColor(255.0f, 170.0f, 17.0f);

    // Potion Colors in RGB
    public static final Color LINK_POTION_LIQUID = CardHelper.getColor(220.0F, 170.0f, 71.0f);
    public static final Color LINK_POTION_HYBRID = CardHelper.getColor(155.0F, 87.0f, 24.0f);
    public static final Color LINK_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f);
    public static final Color DANCE_POTION_LIQUID = CardHelper.getColor(255.0f, 173.0f, 48.0f);
    public static final Color DANCE_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f);
    public static final Color DANCE_POTION_SPOTS = CardHelper.getColor(255.0f, 173.0f, 48.0f);
    public static final Color CRUSH_POTION_LIQUID = CardHelper.getColor(115.0f, 180.0f, 223.0f);
    public static final Color CRUSH_POTION_HYBRID = CardHelper.getColor(68.0f, 108.0f, 134.0f);
    public static final Color CRUSH_POTION_SPOTS = CardHelper.getColor(255.0f, 173.0f, 48.0f);
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theUnchainedModResources/images/512/bg_attack_unchained_small.png";
    private static final String SKILL_DEFAULT_GRAY = "theUnchainedModResources/images/512/bg_skill_unchained_small.png";
    private static final String POWER_DEFAULT_GRAY = "theUnchainedModResources/images/512/bg_power_unchained_small.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "theUnchainedModResources/images/512/card_unchained_orb.png";
    private static final String CARD_ENERGY_ORB = "theUnchainedModResources/images/512/card_small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theUnchainedModResources/images/1024/bg_attack_unchained_big.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theUnchainedModResources/images/1024/bg_skill_unchained_big.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theUnchainedModResources/images/1024/bg_power_unchained_big.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theUnchainedModResources/images/1024/card_unchained_orb_big.png";

    // Character assets
    private static final String THE_DEFAULT_BUTTON = "theUnchainedModResources/images/charSelect/UnchainedCharacterButton.png";

    //  UNCHAINED DEFAULT
    public static final String THE_DEFAULT_PORTRAIT = "theUnchainedModResources/images/charSelect/UnchainedSplashArt.png";
    public static final String THE_DEFAULT_CHARACTER = "theUnchainedModResources/images/char/defaultCharacter/Side_View_tmp_Unchained.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theUnchainedModResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theUnchainedModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theUnchainedModResources/images/char/defaultCharacter/corpse.png";

    //  UNCHAINED DEFAULT
    public static final String PRINCE_UNBOUND_PORTRAIT = "theUnchainedModResources/images/char/defaultCharacter/princeUnbound/PUSplashArt.png";
    public static final String PRINCE_UNBOUND_CHARACTER = "theUnchainedModResources/images/char/defaultCharacter/Side_View_tmp_Unchained.png";
    public static final String PRINCE_UNBOUND_SHOULDER_1 = "theUnchainedModResources/images/char/defaultCharacter/princeUnbound/PU_Shoulder1.png";
    public static final String PRINCE_UNBOUND_SHOULDER_2 = "theUnchainedModResources/images/char/defaultCharacter/princeUnbound/PU_Shoulder2.png";
    public static final String PRINCE_UNBOUND_CORPSE = "theUnchainedModResources/images/char/defaultCharacter/princeUnbound/PU_corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theUnchainedModResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String THE_UNCHAINED_SKELETON_ATLAS = "theUnchainedModResources/images/char/defaultCharacter/idle/skeleton.atlas";
    public static final String THE_UNCHAINED_SKELETON_JSON = "theUnchainedModResources/images/char/defaultCharacter/idle/skeleton_Skeleton.json";
    public static final String THE_UNCHAINED_PRINCE_UNBOUND_SKELETON_ATLAS = "theUnchainedModResources/images/char/defaultCharacter/princeUnbound/princeUnboundSkeleton.atlas";
    public static final String THE_UNCHAINED_PRINCE_UNBOUND_JSON = "theUnchainedModResources/images/char/defaultCharacter/princeUnbound/princeUnboundSkeleton_Skeleton.json";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    public static String makePotionPath(String resourcePath) {
        return getModID() + "Resources/images/potions/" + resourcePath;
    }

    public static String makeComicPath(String resourcePath) {
        return getModID() + "Resources/images/scenes/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeVFXPath(String resourcePath) {
        return getModID() + "Resources/images/vfx/" + resourcePath;
    }

    public static String makeEnergyOrbLayerPath(String resourcePath) {
        return getModID() + "Resources/char/defaultCharacter/orb/" + resourcePath;
    }

    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public TheUnchainedMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */

        setModID("theUnchainedMod");

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheUnchained.Enums.COLOR_ORANGE.toString());


        // THE UNCHAINED DEFAULT CARDS
        BaseMod.addColor(TheUnchained.Enums.COLOR_ORANGE, UNCHAINED_ORANGE, UNCHAINED_ORANGE, UNCHAINED_ORANGE,
                UNCHAINED_ORANGE, UNCHAINED_ORANGE, UNCHAINED_ORANGE, UNCHAINED_ORANGE,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        // THE UNCHAINED BOOSTER PACK CARDS
        BaseMod.addColor(TheUnchained.Enums.COLOR_BOOSTER, UNCHAINED_BOOSTER_COLOR, UNCHAINED_BOOSTER_COLOR, UNCHAINED_BOOSTER_COLOR,
                UNCHAINED_BOOSTER_COLOR, UNCHAINED_BOOSTER_COLOR, UNCHAINED_BOOSTER_COLOR, UNCHAINED_BOOSTER_COLOR,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");

        logger.info("Adding mod settings");

/*
        AbstractPlayer unchainedChar = null;

        for (AbstractPlayer p : CardCrawlGame.characterManager.getAllCharacters())
        {
            if (p.getCardColor() == TheUnchained.Enums.COLOR_ORANGE)  unchainedChar = p;
        }
        if(unchainedChar == null) return;
        Prefs playerPrefs = unchainedChar.getPrefs();

        String heartKillBoolString = "FALSE";
        if(playerPrefs.getBoolean("basemod:HEART_KILL", false)) heartKillBoolString = "TRUE";

*/
        //String heartKillBoolString = "TRUE";

        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        //theUnchainedDefaultSettings.setProperty(UNCHAINED_OPTIONAL_CONTENT_UNLOCKED_PROPERTY, heartKillBoolString);
        //theUnchainedDefaultSettings.setProperty(UNCHAINED_SKIN_UNLOCKED_PROPERTY, "FALSE");
        //theUnchainedDefaultSettings.setProperty(UNCHAINED_BOOSTER_PACK_UNLOCKED_PROPERTY, "FALSE");
        //theUnchainedDefaultSettings.setProperty(UNCHAINED_SKIN_UNLOCKED_PROPERTY, heartKillBoolString);
        //theUnchainedDefaultSettings.setProperty(UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY, heartKillBoolString);
        //theUnchainedDefaultSettings.setProperty(UNCHAINED_BOOSTER_PACK_UNLOCKED_PROPERTY, heartKillBoolString);
        try {
            unchainedConfig.load(); // Load the setting and set the boolean to equal it

            CharacterSelectUIPatch.boosterPackUnlocked = UNCHAINED_OPTIONAL_CONTENT_UNLOCKED = unchainedConfig.getBool(UNCHAINED_OPTIONAL_CONTENT_UNLOCKED_PROPERTY);
            CharacterSelectUIPatch.princeUnboundUnlocked = PRINCE_UNBOUND_SKIN_ACTIVATED = unchainedConfig.getBool(UNCHAINED_SKIN_ACTIVATED_PROPERTY);
            UNCHAINED_BOOSTER_PACK_ACTIVATED = unchainedConfig.getBool(UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TheUnchainedMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TheUnchainedMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TheUnchainedMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======


    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        TheUnchainedMod defaultmod = new TheUnchainedMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheUnchained.Enums.THE_UNCHAINED.toString());
        if(!PRINCE_UNBOUND_SKIN_ACTIVATED) {
            BaseMod.addCharacter(new TheUnchained("the Default", TheUnchained.Enums.THE_UNCHAINED),
                    THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheUnchained.Enums.THE_UNCHAINED);
        }
        else
        {
            BaseMod.addCharacter(new TheUnchained("the Default", TheUnchained.Enums.THE_UNCHAINED),
                    THE_DEFAULT_BUTTON, PRINCE_UNBOUND_PORTRAIT, TheUnchained.Enums.THE_UNCHAINED);
        }

        receiveEditPotions();
        logger.info("Added " + TheUnchained.Enums.THE_UNCHAINED.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Unlock all optional Content

        ModLabeledToggleButton unlockAllOptionalContentButton = new ModLabeledToggleButton("Unlocks all optional content so it can be used whenever you want to.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                UNCHAINED_OPTIONAL_CONTENT_UNLOCKED, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {
                }, // thing??????? idk
                (button) -> { // The actual button:
                    UNCHAINED_OPTIONAL_CONTENT_UNLOCKED = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        //SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theUnchainedDefaultSettings);
                        unchainedConfig.setBool(UNCHAINED_OPTIONAL_CONTENT_UNLOCKED_PROPERTY, UNCHAINED_OPTIONAL_CONTENT_UNLOCKED);
                        unchainedConfig.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


        settingsPanel.addUIElement(unlockAllOptionalContentButton); // Add the button to the settings panel. Button is a go.

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        AddEventParams eventParams = new AddEventParams.Builder(TheDemonSpeaksEvent.ID, TheDemonSpeaksEvent.class) // for this specific event
                .dungeonID(Exordium.ID) // The dungeon (act) this event will appear in
                .playerClass(TheUnchained.Enums.THE_UNCHAINED) // Character specific event
                .create();

        // Add the event
        BaseMod.addEvent(eventParams);

        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD POTIONS ===================

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(ChainGrease.class, LINK_POTION_LIQUID, LINK_POTION_HYBRID, LINK_POTION_SPOTS, ChainGrease.POTION_ID, TheUnchained.Enums.THE_UNCHAINED);
        BaseMod.addPotion(DancePotion.class, DANCE_POTION_LIQUID, DANCE_POTION_HYBRID, DANCE_POTION_SPOTS, DancePotion.POTION_ID, TheUnchained.Enums.THE_UNCHAINED);
        BaseMod.addPotion(CrushingElixir.class, CRUSH_POTION_LIQUID, CRUSH_POTION_HYBRID, CRUSH_POTION_SPOTS, CrushingElixir.POTION_ID, TheUnchained.Enums.THE_UNCHAINED);


        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new RustedChains(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new BalletShoes(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new Memento(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new Carabiner(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new PolishedChains(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new BrokenCharm(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new Churros(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new HeartOfTheUnderdog(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new TotemOfPain(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new DancingRibbons(), TheUnchained.Enums.COLOR_ORANGE);
        BaseMod.addRelicToCustomPool(new Wrench(), TheUnchained.Enums.COLOR_ORANGE);

        // Booster Pack Relics
        BaseMod.addRelicToCustomPool(new CrushingGauntlets(), TheUnchained.Enums.COLOR_BOOSTER);
        BaseMod.addRelicToCustomPool(new ArcaneAmplifier(), TheUnchained.Enums.COLOR_BOOSTER);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        UnlockTracker.markRelicAsSeen(BalletShoes.ID);
        UnlockTracker.markRelicAsSeen(Memento.ID);
        UnlockTracker.markRelicAsSeen(Carabiner.ID);
        UnlockTracker.markRelicAsSeen(PolishedChains.ID);
        UnlockTracker.markRelicAsSeen(BrokenCharm.ID);
        UnlockTracker.markRelicAsSeen(Churros.ID);
        UnlockTracker.markRelicAsSeen(HeartOfTheUnderdog.ID);
        UnlockTracker.markRelicAsSeen(TotemOfPain.ID);
        UnlockTracker.markRelicAsSeen(DancingRibbons.ID);
        UnlockTracker.markRelicAsSeen(Wrench.ID);
        UnlockTracker.markRelicAsSeen(CrushingGauntlets.ID);
        UnlockTracker.markRelicAsSeen(ArcaneAmplifier.ID);
        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        new AutoAdd("TheUnchainedMod") // ${project.artifactId}
                .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();
        new AutoAdd("TheUnchainedMod") // ${project.artifactId}
                .packageFilter(ArcaneArtillery.class) // search in booster pack folder by finding the arcane artillery class
                .setDefaultSeen(true)
                .cards();
        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================
    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Character-Strings.json");

        // UI Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-UI-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Event-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + getLangString() + "/TheUnchainedMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    @Override
    public void receiveAddAudio() {
        logger.info("Adding audios");
        BaseMod.addAudio("swingAttack", makeAudioPath("swingAttacks.ogg"));
        BaseMod.addAudio("chainExtension", makeAudioPath("chainExtension.ogg"));
        BaseMod.addAudio("crushedArmorHit", makeAudioPath("crushedArmorHit.ogg"));
        BaseMod.addAudio("relayApply", makeAudioPath("relayApply.ogg"));
        BaseMod.addAudio("relayBreak", makeAudioPath("relayBreak.ogg"));
        BaseMod.addAudio("unchainedSelect", makeAudioPath("unchainedSelect.ogg"));
        BaseMod.addAudio("normalChainAttack", makeAudioPath("normalChainAttack.ogg"));
        BaseMod.addAudio("arcaneCharmApplication", makeAudioPath("arcaneCharmApplication.ogg"));
        BaseMod.addAudio("glyphBeam", makeAudioPath("glyphBeam.ogg"));


        logger.info("Done adding audios");
    }


    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
