package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import theUnchainedMod.util.TextureLoader;

@SpirePatch(clz = AbstractCreature.class, method = "renderHealth")
public class RelayedDamageIconRender {

    private static final Texture nextTurnRelayedDamageIcon = TextureLoader.getTexture("theUnchainedModResources/images/ui/NextTurnRelayedDamage_UIIcon.png");
    private static final Texture thisTurnRelayedDamageIcon = TextureLoader.getTexture("theUnchainedModResources/images/ui/ThisTurnRelayedDamage_UIIcon.png");


    @SpirePostfixPatch
    public static void renderRelayedDamageIconAndValue(AbstractCreature creature, SpriteBatch sb, float ___BLOCK_ICON_X, float ___BLOCK_ICON_Y, float ___blockOffset, Hitbox ___hb, float ___hbYOffset, Color ___blockTextColor, float ___blockScale) {
        if (!Settings.hideCombatElements && RelayHelpers.nextTurnRelayedDamage.get(creature) > 0) {
            int nextTurnRelayedDamageAmount = RelayHelpers.nextTurnRelayedDamage.get(creature);
            int actualNextTurnDamageAmount = nextTurnRelayedDamageAmount / 2;
            float x = ___hb.cX - ___hb.width / 2.0F;
            float y = ___hb.cY - ___hb.height / 2.0F + ___hbYOffset;

            sb.setColor(RelayHelpers.nextTurnRelayedDamageColor.get(creature));
            sb.draw(nextTurnRelayedDamageIcon, x + RelayHelpers.RELAY_ICON_X - 32.0F, y + RelayHelpers.NEXT_TURN_RELAYED_DAMAGE_ICON_Y - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale,
                    0.0F, 0, 0, 64, 64, false, false);

            FontHelper.blockInfoFont.getData().setScale(RelayHelpers.nextTurnRelayedDamageScale.get(creature));
            FontHelper.renderFontRightAligned(sb, FontHelper.blockInfoFont, Integer.toString(nextTurnRelayedDamageAmount), x + ___BLOCK_ICON_X, y + 175.0F * Settings.scale, RelayHelpers.nextTurnRelayedDamageTextColor.get(creature));
            FontHelper.renderFontRightAligned(sb, FontHelper.blockInfoFont, Integer.toString(actualNextTurnDamageAmount), x + ___BLOCK_ICON_X, y + 145.0F * Settings.scale, RelayHelpers.nextTurnRelayedActualDamageTextColor.get(creature));
            FontHelper.blockInfoFont.getData().setScale(1.0F);
        }

        if (!Settings.hideCombatElements && RelayHelpers.thisTurnRelayedDamage.get(creature) > 0) {
            int thisTurnRelayedDamageAmount = RelayHelpers.thisTurnRelayedDamage.get(creature);
            int actualThisTurnDamageAmount = thisTurnRelayedDamageAmount / 2;
            float x = ___hb.cX - ___hb.width / 2.0F;
            float y = ___hb.cY - ___hb.height / 2.0F + ___hbYOffset;

            sb.setColor(RelayHelpers.thisTurnRelayedDamageColor.get(creature));
            sb.draw(thisTurnRelayedDamageIcon, x + RelayHelpers.RELAY_ICON_X - 32.0F, y + RelayHelpers.THIS_TURN_RELAYED_DAMAGE_ICON_Y - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale,
                    0.0F, 0, 0, 64, 64, false, false);

            FontHelper.blockInfoFont.getData().setScale(RelayHelpers.thisTurnRelayedDamageScale.get(creature));
            FontHelper.renderFontRightAligned(sb, FontHelper.blockInfoFont, Integer.toString(thisTurnRelayedDamageAmount), x + ___BLOCK_ICON_X, y + 111.0F * Settings.scale, RelayHelpers.thisTurnRelayedDamageTextColor.get(creature));
            FontHelper.renderFontRightAligned(sb, FontHelper.blockInfoFont, Integer.toString(actualThisTurnDamageAmount), x + ___BLOCK_ICON_X, y + 81.0F * Settings.scale, RelayHelpers.thisTurnRelayedActualDamageTextColor.get(creature));
            FontHelper.blockInfoFont.getData().setScale(1.0F);

        } else if (!Settings.hideCombatElements && RelayHelpers.nextTurnRelayedDamage.get(creature) > 0) {
            float x = ___hb.cX - ___hb.width / 2.0F;
            float y = ___hb.cY - ___hb.height / 2.0F + ___hbYOffset;
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
            sb.draw(thisTurnRelayedDamageIcon, x + RelayHelpers.RELAY_ICON_X - 32.0F, y + RelayHelpers.THIS_TURN_RELAYED_DAMAGE_ICON_Y - 32.0F,
                    32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale,
                    0.0F, 0, 0, 64, 64, false, false);
        }
    }
}
