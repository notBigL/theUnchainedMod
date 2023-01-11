package theUnchainedMod.patches;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theUnchainedMod.util.TextureLoader;

import java.util.ArrayList;

@SpirePatch(clz = AbstractPlayer.class, method = "renderPowerTips")
public class RelayedDamageToolTips {

    private static final PowerStrings relayStrings = CardCrawlGame.languagePack.getPowerStrings("theUnchainedMod:RelayPower");
    private static final String[] RELAY_DESCRIPTIONS = relayStrings.DESCRIPTIONS;
    private static final Texture relayTexture = TextureLoader.getTexture("theUnchainedModResources/images/ui/RelayTooltipIcon.png");
    private static final TextureAtlas.AtlasRegion relayTexture48 = new TextureAtlas.AtlasRegion(relayTexture, 0, 0, 48, 48);
    private static final PowerStrings nextTurnRelayedDamageStrings = CardCrawlGame.languagePack.getPowerStrings("theUnchainedMod:NextTurnRelayedDamage");
    private static final String[] NEXT_TURN_DESCRIPTIONS = nextTurnRelayedDamageStrings.DESCRIPTIONS;
    private static final Texture nextTurnTexture = TextureLoader.getTexture("theUnchainedModResources/images/ui/NextTurnRelayedDamageUIIcon_Centered.png");
    private static final TextureAtlas.AtlasRegion nextTurnTexture48 = new TextureAtlas.AtlasRegion(nextTurnTexture, 0, 0, 48, 48);
    private static final PowerStrings thisTurnRelayedDamageStrings = CardCrawlGame.languagePack.getPowerStrings("theUnchainedMod:ThisTurnRelayedDamage");
    private static final String[] THIS_TURN_DESCRIPTIONS = thisTurnRelayedDamageStrings.DESCRIPTIONS;
    private static final Texture thisTurnTexture = TextureLoader.getTexture("theUnchainedModResources/images/ui/ThisTurnRelayedDamageUIIcon_Centered.png");
    private static final TextureAtlas.AtlasRegion thisTurnTexture48 = new TextureAtlas.AtlasRegion(thisTurnTexture, 0, 0, 48, 48);


    @SpireInsertPatch(rloc = 1, localvars = {"tips"})
    public static void insertRelayedDamageToolTips(AbstractPlayer player, SpriteBatch sb, @ByRef ArrayList<PowerTip>[] tips) {
        if(RelayHelpers.currentRelay.get(player) > 0) {
            String description = RELAY_DESCRIPTIONS[0] + RelayHelpers.currentRelay.get(player) + RELAY_DESCRIPTIONS[1];
            tips[0].add(new PowerTip(relayStrings.NAME, description, relayTexture48));
        }
        if (RelayHelpers.nextTurnRelayedDamage.get(player) > 0) {
            String description = NEXT_TURN_DESCRIPTIONS[0] + RelayHelpers.nextTurnRelayedDamage.get(player) + NEXT_TURN_DESCRIPTIONS[1] + RelayHelpers.nextTurnRelayedDamage.get(player) / 2 + NEXT_TURN_DESCRIPTIONS[2];
            tips[0].add(new PowerTip(nextTurnRelayedDamageStrings.NAME, description, nextTurnTexture48));
        }
        if (RelayHelpers.thisTurnRelayedDamage.get(player) > 0) {
            String description = THIS_TURN_DESCRIPTIONS[0] + RelayHelpers.thisTurnRelayedDamage.get(player) + THIS_TURN_DESCRIPTIONS[1] + RelayHelpers.thisTurnRelayedDamage.get(player) / 2 + THIS_TURN_DESCRIPTIONS[2];
            tips[0].add(new PowerTip(thisTurnRelayedDamageStrings.NAME, description, thisTurnTexture48));
        }

    }
}
