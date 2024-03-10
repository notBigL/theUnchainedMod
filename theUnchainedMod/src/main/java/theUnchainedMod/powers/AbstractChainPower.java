package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.actions.ChainAction;
import theUnchainedMod.patches.ChainsFinishedThisCombat;
import theUnchainedMod.relics.Carabiner;
import theUnchainedMod.relics.Churros;
import theUnchainedMod.relics.DancingRibbons;
import theUnchainedMod.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class AbstractChainPower extends AbstractPower {
    private static final Texture attackTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/AttackChain_power48.png");
    private static final Texture attackTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/AttackChain_power128.png");
    private static final Texture skillTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/SkillChain_power48.png");
    private static final Texture skillTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/SkillChain_power128.png");
    private static final Texture powerTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/PowerChain_power48.png");
    private static final Texture powerTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/PowerChain_power128.png");
    public static int chainIdOffset;
    public final AbstractGameAction finishedChainAction;
    public final AbstractCard.CardType cardType;
    private final String powerIdWithoutOffset;


    public AbstractChainPower(String powerID, AbstractCreature player, int chainLength, AbstractGameAction finishedChainAction, AbstractCard.CardType cardType) {
        this.ID = powerID + chainIdOffset;
        chainIdOffset++;
        this.owner = player;
        this.amount = chainLength;
        this.finishedChainAction = finishedChainAction;
        this.cardType = cardType;
        loadTextures(this.cardType);
        this.powerIdWithoutOffset = powerID;
        churroIncrement();
    }

    public void atEndOfTurn(boolean isPlayer) {
        triggerCarabiner();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }


    public void onUseCard(AbstractCard c, UseCardAction action) {
        switch (c.cardID) {
            case "theUnchainedMod:ChainSaw":
                triggerCarabiner();
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                break;
            case "theUnchainedMod:Liberation":
                AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, this.ID, "liberation"));
                this.flash();
                break;
            case "theUnchainedMod:DoubleDown":
                AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, this.ID, "link"));
                this.flash();
                break;
            case "theUnchainedMod:Swirl":
                if (player.hasRelic(DancingRibbons.ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, this.ID, "link"));
                    this.flash();
                    break;
                }
            default:
                AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, this.ID));
                if (this.cardType == c.type) {
                    this.flash();
                }
                break;
        }
    }

    private void loadTextures(AbstractCard.CardType type) {
        if (type == AbstractCard.CardType.ATTACK) {
            this.region128 = new TextureAtlas.AtlasRegion(attackTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(attackTexture48, 0, 0, 48, 48);
        } else if (type == AbstractCard.CardType.SKILL) {
            this.region128 = new TextureAtlas.AtlasRegion(skillTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(skillTexture48, 0, 0, 48, 48);
        } else {
            this.region128 = new TextureAtlas.AtlasRegion(powerTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(powerTexture48, 0, 0, 48, 48);
        }
    }

    public void finishMe() {
        AbstractDungeon.actionManager.addToBottom(finishedChainAction);
        checkForFinishers();
        ChainsFinishedThisCombat.IncreaseChainsFinishedThisCombat(1);
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }


    private void checkForFinishers() {
        if (player.hasPower(AccelerationPower.POWER_ID)) {
            player.getPower(AccelerationPower.POWER_ID).onSpecificTrigger();
        }
        if (player.hasPower(FluidMovementPower.POWER_ID)) {
            int momentumAmount = player.getPower(FluidMovementPower.POWER_ID).amount;
            if (momentumAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MomentumPower(player, momentumAmount)));
            }
        }
        if (player.hasPower(ChaseDestinyPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        if (player.hasPower(GuardedPosturePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player.getPower(GuardedPosturePower.POWER_ID).amount));
        }
    }

    private void churroIncrement() {
        if (player.hasRelic(Churros.ID)) {
            player.getRelic(Churros.ID).onTrigger();
        }
    }

    private void triggerCarabiner() {
        if (player.hasRelic(Carabiner.ID)) {
            player.getRelic(Carabiner.ID).onTrigger();
        }
    }
}
