package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.util.TextureLoader;

import java.util.Iterator;

public class BarbedWireChainsPower extends AbstractPower {

    public AbstractCreature source;
    public static final String POWER_ID = TheUnchainedMod.makeID("BarbedWireChainsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/BarbedWireChains_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/BarbedWireChains_power128.png");

    public BarbedWireChainsPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        int unfinishedChains = 0;
        Iterator var1 = AbstractDungeon.player.powers.iterator();
        AbstractPower currentPower;
        while (var1.hasNext()) {
            currentPower = (AbstractPower) var1.next();
            if (currentPower instanceof AbstractChainPower || currentPower instanceof AbstractMasterChainPower) unfinishedChains++;
        }
        if (unfinishedChains > 0) {
            this.flash();
            for (int i = 0; i < unfinishedChains; i++) {
                this.addToBot(new DamageAllEnemiesAction((AbstractCreature) null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}
