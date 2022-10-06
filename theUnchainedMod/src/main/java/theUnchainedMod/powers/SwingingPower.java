package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.util.TextureLoader;

import java.util.Iterator;

public class SwingingPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("SwingingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/KeepSwingingPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/KeepSwingingPower_power128.png");

    public SwingingPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
        updateExistingSwings(amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    private void updateExistingSwings(int additionalDamage) {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();
        iterateThroughCardGroup(var1, additionalDamage);

        var1 = AbstractDungeon.player.drawPile.group.iterator();
        iterateThroughCardGroup(var1, additionalDamage);

        var1 = AbstractDungeon.player.discardPile.group.iterator();
        iterateThroughCardGroup(var1, additionalDamage);

        var1 = AbstractDungeon.player.exhaustPile.group.iterator();
        iterateThroughCardGroup(var1, additionalDamage);

    }

    private void iterateThroughCardGroup(Iterator cardGroup, int additionalDamage) {
        AbstractCard c;
        while(cardGroup.hasNext()) {
            c = (AbstractCard)cardGroup.next();
            if (c.tags.contains(CustomTags.SWING)) {
                    c.baseDamage += additionalDamage;
            }
        }
    }

}
