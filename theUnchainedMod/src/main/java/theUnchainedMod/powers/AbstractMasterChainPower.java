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
import theUnchainedMod.actions.MasterChainAction;
import theUnchainedMod.relics.Carabiner;
import theUnchainedMod.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class AbstractMasterChainPower extends AbstractPower {
    private static final Texture Texture48_111 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/111_48.png");
    private static final Texture Texture128_111 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/111_128.png");
    private static final Texture Texture48_110 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/110_48.png");
    private static final Texture Texture128_110 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/110_128.png");
    private static final Texture Texture48_101 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/101_48.png");
    private static final Texture Texture128_101 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/101_128.png");
    private static final Texture Texture48_100 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/100_48.png");
    private static final Texture Texture128_100 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/100_128.png");
    private static final Texture Texture48_011 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/011_48.png");
    private static final Texture Texture128_011 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/011_128.png");
    private static final Texture Texture48_010 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/010_48.png");
    private static final Texture Texture128_010 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/010_128.png");
    private static final Texture Texture48_001 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/001_48.png");
    private static final Texture Texture128_001 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/001_128.png");
    private static final Texture Texture48_000 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/000_48.png");
    private static final Texture Texture128_000 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MasterChain/000_128.png");
    private static int chainIdOffset;
    public final AbstractGameAction finishedChainAction;
    public final AbstractCard.CardType cardType;
    private final String powerIdWithoutOffset;

    public int powersRequired;
    public int attacksRequired;
    public int skillsRequired;

    public AbstractMasterChainPower(String powerID, AbstractCreature player, int chainLength, AbstractGameAction finishedChainAction, AbstractCard.CardType cardType) {
        this.ID = powerID + chainIdOffset;
        chainIdOffset++;
        this.owner = player;
        this.powersRequired = this.skillsRequired = this.attacksRequired = this.amount = chainLength;
        this.finishedChainAction = finishedChainAction;
        this.cardType = cardType;
        loadTextures();
        this.powerIdWithoutOffset = powerID;
    }

    public void atEndOfTurn(boolean isPlayer) {
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        switch (c.cardID) {
            case "theUnchainedMod:ChainSaw":
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                break;
            default:
                AbstractDungeon.actionManager.addToBottom(new MasterChainAction(this.owner, c, this.ID, this));
                this.flash();
                break;
        }
    }

    public void loadTextures() {

        if (attacksRequired > 0)
        {
            if (skillsRequired > 0)
            {
                if (powersRequired > 0)
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_111, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_111, 0, 0, 128, 128);
                }
                else
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_110, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_110, 0, 0, 128, 128);
                }
            }
            else
            {
                if (powersRequired > 0)
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_101, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_101, 0, 0, 128, 128);
            }
                else
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_100, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_100, 0, 0, 128, 128);
                }
            }
        }
        else
        {
            if (skillsRequired > 0)
            {
                if (powersRequired > 0)
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_011, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_011, 0, 0, 128, 128);
                }
                else
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_010, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_010, 0, 0, 128, 128);
                }
            }
            else
            {
                if (powersRequired > 0)
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_001, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_001, 0, 0, 128, 128);
                }
                else
                {
                    this.region48 = new TextureAtlas.AtlasRegion(Texture48_000, 0, 0, 48, 48);
                    this.region128 = new TextureAtlas.AtlasRegion(Texture128_000, 0, 0, 128, 128);
                }
            }
        }
    }


    public void finishMe() {
        AbstractDungeon.actionManager.addToBottom(finishedChainAction);
        checkForFinishers();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new ChainsFinishedThisTurnPower(this.owner, 1)));
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

}
