package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.actions.ChainAction;
import theUnchainedMod.util.TextureLoader;

public class AbstractChainPower extends AbstractPower {
    private static final Texture attackTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/AttackChain_power48.png");
    private static final Texture attackTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/AttackChain_power128.png");
    private static final Texture skillTexture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/SkillChain_power48.png");
    private static final Texture skillTexture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/SkillChain_power128.png");
    private static int chainIdOffset;
    public final AbstractGameAction finishedChainAction;
    public final AbstractCard.CardType cardType;


    public AbstractChainPower(String powerID, AbstractCreature player, int chainLength, AbstractGameAction finishedChainAction, AbstractCard.CardType cardType) {
        this.ID = powerID + chainIdOffset;
        chainIdOffset++;
        this.owner = player;
        this.amount = chainLength;
        this.finishedChainAction = finishedChainAction;
        this.cardType = cardType;
        loadTextures(this.cardType);
        instantFinishCheck();
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }


    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.cardID.equals("theUnchainedMod:Liberation")) {
            AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID, "liberation"));
        } else if (c.cardID.equals("theUnchainedMod:RoutinePunch") || c.cardID.equals("theUnchainedMod:RoutineDodge") || c.cardID.equals("theUnchainedMod:Swirl")) {
            AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID, "link"));
            this.flash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new ChainAction(this.owner, c, this.cardType, finishedChainAction, this.ID));
            this.flash();
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
            this.region128 = new TextureAtlas.AtlasRegion(skillTexture128, 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(skillTexture48, 0, 0, 48, 48);
        }
    }

    private void instantFinishCheck() {
        AbstractPlayer player = (AbstractPlayer) owner;
        String instantFinishID = "theUnchainedMod:KnotPower";
        if (player.hasPower(instantFinishID)) {
            player.getPower(instantFinishID).reducePower(1);
            AbstractDungeon.actionManager.addToBottom(this.finishedChainAction);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new MomentumPower(player)));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
}
