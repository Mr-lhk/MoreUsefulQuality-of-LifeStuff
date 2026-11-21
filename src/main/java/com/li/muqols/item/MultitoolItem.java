package com.li.muqols.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

/**
 * 多功能工具：集镐、斧、锄、锹、剑的能力于一体。
 * <p>
 * 数值约定（与原版对齐）：
 * <ul>
 *     <li>耐久、挖掘速度、附魔能力、修复材料：直接使用 {@link Tier} 的原版数值；</li>
 *     <li>攻击伤害：取同材质原版斧的数值，攻速固定为原版剑的 1.6（{@code -2.4} 修饰符）；</li>
 *     <li>攻击时消耗 2 点耐久（同原版斧），挖掘方块每块消耗 1 点；</li>
 *     <li>拥有剑的横扫（{@code SWORD_SWEEP}）与斧的破盾能力。</li>
 * </ul>
 * 右键优先级：斧（去皮/刮铜/除蜡）→ 锄地 → 铲平/灭火；潜行右键时铲优先、锄次之。
 */
public class MultitoolItem extends TieredItem {
    private static final float ATTACK_SPEED = -2.4F;

    public MultitoolItem(Tier tier, float attackDamage, Item.Properties properties) {
        super(tier, properties
                .component(DataComponents.TOOL, createToolProperties(tier))
                .attributes(DiggerItem.createAttributes(tier, attackDamage, ATTACK_SPEED)));
    }

    private static Tool createToolProperties(Tier tier) {
        float speed = tier.getSpeed();
        return new Tool(List.of(
                // 达不到该材质挖掘等级的方块不掉落
                Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, speed),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, speed),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_HOE, speed),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, speed),
                // 剑的效果：蜘蛛网快速破坏且掉落，剑类方块加速
                Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
                Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)
        ), 1.0F, 1);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
        return ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_AXE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ability);
    }

    /**
     * 斧的破盾效果：原版默认只对 {@code AxeItem} 生效，这里显式放开。
     */
    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    /**
     * 与斧一致：每次命中消耗 2 点耐久。
     */
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        // 与原版斧一致：副手举盾且非潜行时，右键的意图是格挡，此时跳过斧类行为
        // （去树皮/刮铜/除蜡）。锄、铲本身在原版中并不检查副手盾牌，因此仍照常生效。
        boolean shieldBlockIntent = player != null
                && context.getHand() == InteractionHand.MAIN_HAND
                && player.getOffhandItem().is(Items.SHIELD)
                && !player.isSecondaryUseActive();

        if (!shieldBlockIntent) {
            InteractionResult axeResult = this.useAsAxe(context, level, pos, state, player);
            if (axeResult != InteractionResult.PASS) {
                return axeResult;
            }
        }

        // 潜行右键优先铲平/灭火，普通右键优先锄地
        if (player != null && player.isSecondaryUseActive()) {
            InteractionResult shovelResult = this.useAsShovel(context, level, pos, state, player);
            if (shovelResult != InteractionResult.PASS) {
                return shovelResult;
            }
            return this.useAsHoe(context, level, pos, state, player);
        }

        InteractionResult hoeResult = this.useAsHoe(context, level, pos, state, player);
        if (hoeResult != InteractionResult.PASS) {
            return hoeResult;
        }
        return this.useAsShovel(context, level, pos, state, player);
    }

    private InteractionResult useAsAxe(UseOnContext context, Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        BlockState modified = state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false);
        if (modified != null) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return this.applyModification(context, level, pos, modified, player);
        }

        modified = state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false);
        if (modified != null) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, pos, 0);
            return this.applyModification(context, level, pos, modified, player);
        }

        modified = state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false);
        if (modified != null) {
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, pos, 0);
            return this.applyModification(context, level, pos, modified, player);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult useAsHoe(UseOnContext context, Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        BlockState tilled = state.getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
        if (tilled == null) {
            return InteractionResult.PASS;
        }
        level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return this.applyModification(context, level, pos, tilled, player);
    }

    private InteractionResult useAsShovel(UseOnContext context, Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        }

        BlockState flattened = state.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
        if (flattened != null && level.getBlockState(pos.above()).isAir()) {
            level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            return this.applyModification(context, level, pos, flattened, player);
        }

        BlockState doused = state.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false);
        if (doused != null) {
            if (!level.isClientSide) {
                level.levelEvent(null, 1009, pos, 0);
            }
            return this.applyModification(context, level, pos, doused, player);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult applyModification(UseOnContext context, Level level, BlockPos pos, BlockState newState, @Nullable Player player) {
        if (!level.isClientSide) {
            level.setBlock(pos, newState, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.muqols.multitool.usage").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.muqols.multitool.sneak").withStyle(ChatFormatting.DARK_GRAY));
    }
}
