package theUnchainedMod.events;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.relics.ArcaneAmplifier;
import theUnchainedMod.relics.CrushingGauntlets;

import static theUnchainedMod.TheUnchainedMod.makeEventPath;

public class TheDemonSpeaksEvent extends AbstractImageEvent {

    public static final String ID = TheUnchainedMod.makeID("TheDemonSpeaks");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("TheDemonSpeaksEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private final int HEALTH_LOSS_LOW_ASCENSION = 7;
    private final int HEALTH_LOSS = 10;

    private int healthdamage; //The actual number of how much Max HP we're going to lose.

    public TheDemonSpeaksEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15) { // If the player is ascension 15 or above, lose 5% max hp. Else, lose just 3%.
            healthdamage = HEALTH_LOSS;
        } else {
            healthdamage = HEALTH_LOSS_LOW_ASCENSION;
        }

        // The first dialogue options available to us.
        this.imageEventText.updateBodyText(DESCRIPTIONS[0]); // Update the text of the event
        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0: // running through Exordium

                imageEventText.loadImage("theUnchainedModResources/images/events/TheDemonSpeaksEvent2.png"); // Change the shown image to image of mask
                CardCrawlGame.sound.play("unchainedSelect"); // Play a hit sound

                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);

                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[1] + healthdamage + OPTIONS[2], new CrushingGauntlets()); // Crushing Gauntlets - take damage
                imageEventText.setDialogOption(OPTIONS[0] , new Writhe(), new ArcaneAmplifier()); // Arcane Amplifier, gain a writhe.
                imageEventText.setDialogOption(OPTIONS[3]); // Tell him to shove it
                screenNum = 1;
                break;
            case 1:
                switch (i) {
                    case 0: // Crushing gauntlets
                        imageEventText.loadImage("theUnchainedModResources/images/events/TheDemonSpeaksEvent3.png"); // Change the shown image to image of mask
                        CardCrawlGame.sound.play("ATTACK_HEAVY");
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, healthdamage));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new CrushingGauntlets());

                        screenNum = 2;
                        break;
                    case 1: // Arcane Amplifier
                        imageEventText.loadImage("theUnchainedModResources/images/events/TheDemonSpeaksEvent3.png"); // Change the shown image to image of mask
                        CardCrawlGame.sound.play("arcaneCharmApplication");
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new ArcaneAmplifier());

                        screenNum = 2;
                        AbstractCard c = new Writhe().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        break;

                    case 2: // Leave without doing anything
                        imageEventText.loadImage("theUnchainedModResources/images/events/TheDemonSpeaksEvent4.png"); // Change the shown image to image of mask
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;
                }
                break;
            case 2:
                openMap(); // You'll open the map and end the event
                break;
        }
    }
}