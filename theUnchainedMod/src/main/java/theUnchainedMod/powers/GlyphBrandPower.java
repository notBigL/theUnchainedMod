package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.util.TextureLoader;
import theUnchainedMod.vfx.SmallLaserDifferentColorEffect;

public class GlyphBrandPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("GlyphBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/GlyphBrandPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/GlyphBrandPower_power128.png");

    public GlyphBrandPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onSpecificTrigger() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserDifferentColorEffect(owner.hb.cX, owner.hb.cY, source.hb.cX, source.hb.cY), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }


    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

}
