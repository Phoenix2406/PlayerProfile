/**
 * LICENSE
 * PlayerProfile for RPG System
 * Copyright (C) 2022 Phoenix
 * ----------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 * END
 */

package org.phoenix.playerprofile.manager.settings;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.utils.Logger;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SettingManager extends ListenerAdapter {

    private static Map<SelectMenu, List<Modal>> modalPage = new HashMap<>();
    private static final Map<Long, List<PaginatedSettings>> paginatedEmbedsByMember = new HashMap<>();
    private static final Map<Long, Instant> lastInteractionByMember = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void createPaginated(TextChannel channel, long memberId, Map<List<MessageEmbed>, List<SelectMenu>> pages, Map<SelectMenu, List<Modal>> modalPages) {
        SettingManager.modalPage = modalPages;
        List<PaginatedSettings> paginatedEmbeds = paginatedEmbedsByMember.computeIfAbsent(memberId, k -> new ArrayList<>());
        PaginatedSettings paginatedEmbed = new PaginatedSettings(channel, pages);
        paginatedEmbeds.add(paginatedEmbed);
        paginatedEmbed.sendInitialPage();
    }

    public static void updateInteraction(long memberId) {
        lastInteractionByMember.put(memberId, Instant.now());
    }

    private void disableButton(long memberId) {
        List<PaginatedSettings> paginatedEmbeds = paginatedEmbedsByMember.get(memberId);
        if (paginatedEmbeds != null) {
            paginatedEmbeds.forEach(PaginatedSettings::disableButtons);
        }
    }

    public void startInteractionChecker() {
        scheduler.scheduleAtFixedRate(() -> {
            Instant currentTime = Instant.now();
            for (Long memberId : lastInteractionByMember.keySet()) {
                Instant lastInteraction = lastInteractionByMember.get(memberId);
                long minuteSinceInteraction = ChronoUnit.MINUTES.between(lastInteraction, currentTime);
                if (minuteSinceInteraction >= 3) {
                    disableButton(memberId);
                }
            }
        }, 3, 3, TimeUnit.MINUTES);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) return;

        long memberId = member.getIdLong();
        List<PaginatedSettings> paginatedEmbeds = paginatedEmbedsByMember.get(memberId);
        if (paginatedEmbeds == null) return;

        for (PaginatedSettings paginatedEmbed : paginatedEmbeds) {
            if (event.getComponentId().equals(paginatedEmbed.getPrevButtonId())) {
                event.deferEdit().queue();
                paginatedEmbed.showPreviousPage();
                updateInteraction(memberId);
                paginatedEmbed.updateEmbed().exceptionally(exception -> {
                    Logger.log(Logger.LogLevel.ERROR, "An error occurred while updating the embed.");
                    Log4JLogger.printStackTrace(exception);
                    return null;
                });
            } else if (event.getComponentId().equals(paginatedEmbed.getNextButtonId())) {
                event.deferEdit().queue();
                paginatedEmbed.showNextPage();
                updateInteraction(memberId);
                paginatedEmbed.updateEmbed().exceptionally(exception -> {
                    Logger.log(Logger.LogLevel.ERROR, "An error occurred while updating the embed.");
                    Log4JLogger.printStackTrace(exception);
                    return null;
                });
            } else if (event.getComponentId().equals(paginatedEmbed.getReloadButtonId())) {
                event.deferEdit().queue();
                event.editButton(Button.success(paginatedEmbed.getReloadButtonId(), ProfileCore.getTranslatorAPI().translate("Success"))).queue(success ->
                        event.editButton(Button.primary(paginatedEmbed.getReloadButtonId(), ProfileCore.getTranslatorAPI().translate("Reload"))).queueAfter(10, TimeUnit.SECONDS));
                try {
                    ProfileCore.getConfigData().getInstance().update();
                    ProfileCore.getDiscordConfig().getInstance().update();
                    ProfileCore.getSoundConfig().getInstance().update();
                    ProfileCore.getEnLangConfig().getInstance().update();
                    ProfileCore.getIdLangConfig().getInstance().update();
                    ProfileCore.getConfigData().getInstance().reload();
                    ProfileCore.getDiscordConfig().getInstance().reload();
                    ProfileCore.getSoundConfig().getInstance().reload();
                    ProfileCore.getEnLangConfig().getInstance().reload();
                    ProfileCore.getIdLangConfig().getInstance().reload();
                } catch (IOException exception) {
                    Log4JLogger.printStackTrace(exception);
                }
            }
        }
    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        for (Map.Entry<SelectMenu, List<Modal>> entry : modalPage.entrySet()) {
            SelectMenu selectMenus = entry.getKey();
            List<Modal> modals = entry.getValue();
            SelectOption selectedOption = event.getSelectedOptions().get(0); // Assuming one option is selected

            // Get the index of the selected item in the SelectMenu dropdown
            int selectedIndex = -1; // Default to -1 if not found

            int currentIndex = 0;

            for (SelectOption option : event.getSelectMenu().getOptions()) {
                if (option.equals(selectedOption)) {
                    selectedIndex = currentIndex;
                    break;
                }
                currentIndex++;
            }

            if (event.getComponentId().equals(selectMenus.getId())) {
                event.replyModal(modals.get(selectedIndex)).queue();
            }
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        for (Map.Entry<SelectMenu, List<Modal>> entry : modalPage.entrySet()) {
            List<Modal> modals = entry.getValue();

            for (Modal modal : modals) {
                if (event.getModalId().equals(modal.getId())) {
                    YamlDocument fileConfig = getConfigurationType(modal.getId());
                    for (ModalMapping modalValue : event.getValues()) {
                        try {
                            fileConfig.set(modalValue.getId(), Integer.parseInt(modalValue.getAsString()));
                        } catch (NumberFormatException exception) {
                            if (modalValue.getAsString().equals("true") || modalValue.getAsString().equals("false")) {
                                fileConfig.set(modalValue.getId(), Boolean.parseBoolean(modalValue.getAsString()));
                            } else {
                                fileConfig.set(modalValue.getId(), modalValue.getAsString());
                            }
                        } finally {
                            try {
                                fileConfig.save();
                            } catch (IOException exception) {
                                Log4JLogger.printStackTrace(exception);
                            }
                        }

                    }
                }
            }
        }
    }

    private YamlDocument getConfigurationType(String type) {
        switch (type) {
            case "config.yml" -> {
                return ProfileCore.getConfigData().getInstance();
            }
            case "DiscordConfig.yml" -> {
                return ProfileCore.getDiscordConfig().getInstance();
            }
            case "SoundConfig.yml" -> {
                return ProfileCore.getSoundConfig().getInstance();
            }
            case "DiscordEmote.yml" -> {
                return ProfileCore.getEmoteConfig().getInstance();
            }
            case "en_US.yml" -> {
                return ProfileCore.getEnLangConfig().getInstance();
            }
            case "ind_ID.yml" -> {
                return ProfileCore.getIdLangConfig().getInstance();
            }
            default -> throw new IllegalArgumentException("Couldn't parse configuration type.");
        }
    }
}

class PaginatedSettings {
    private final TextChannel channel;
    private final Map<List<MessageEmbed>, List<SelectMenu>> pages;
    private int currentPage;
    private Message message;
    private final String nextButtonId;
    private final String saveButtonId;
    private final String prevButtonId;
    private final AtomicInteger customIdCounter = new AtomicInteger(0);

    public PaginatedSettings(TextChannel channel, Map<List<MessageEmbed>, List<SelectMenu>> pages) {
        this.channel = channel;
        this.pages = pages;
        this.currentPage = 0;

        this.nextButtonId = "next_" + generateCustomId();
        this.saveButtonId = "reload_" + generateCustomId();
        this.prevButtonId = "prev_" + generateCustomId();
    }

    public void sendInitialPage() {
        if (!pages.isEmpty()) {
            for (Map.Entry<List<MessageEmbed>, List<SelectMenu>> entry : pages.entrySet()) {
                MessageEmbed initialPage = entry.getKey().get(0);
                SelectMenu initialMenu = entry.getValue().get(0);
                channel.sendMessageEmbeds(initialPage)
                        .setActionRows(ActionRow.of(initialMenu), getActionRow())
                        .queue(msg -> message = msg);
            }
        }
    }

    public void showNextPage() {
        for (Map.Entry<List<MessageEmbed>, List<SelectMenu>> entry : pages.entrySet()) {
            if (currentPage < entry.getKey().size() - 1) {
                currentPage++;
                updateEmbed();
            }
        }
    }

    public void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateEmbed();
        }
    }

    public CompletableFuture<Void> updateEmbed() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (message != null) {
            for (Map.Entry<List<MessageEmbed>, List<SelectMenu>> entry : pages.entrySet()) {
                message.editMessageEmbeds(entry.getKey().get(currentPage))
                        .setActionRows(ActionRow.of(entry.getValue().get(currentPage)), getActionRow())
                        .queue(success -> future.complete(null),
                                error -> {
                                    Logger.log(Logger.LogLevel.SUCCESS, "Error updating embed!");
                                    Log4JLogger.printStackTrace(error);
                                    future.completeExceptionally(error);
                                }
                        );
            }
        } else {
            future.completeExceptionally(new IllegalStateException("Message is null."));
        }
        return future;
    }

    public void disableButtons() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (message != null) {
            for (Map.Entry<List<MessageEmbed>, List<SelectMenu>> entry : pages.entrySet()) {
                message.editMessageEmbeds(entry.getKey().get(currentPage))
                        .setActionRows(
                                ActionRow.of(
                                        entry.getValue().get(currentPage).asDisabled()),
                                ActionRow.of(
                                        Button.danger(getPrevButtonId(), ProfileCore.getTranslatorAPI().translate("Previous")).withDisabled(true),
                                        Button.primary(getReloadButtonId(), ProfileCore.getTranslatorAPI().translate("Reload")).withDisabled(true),
                                        Button.success(getNextButtonId(), ProfileCore.getTranslatorAPI().translate("Next")).withDisabled(true)))
                        .queue(success -> future.complete(null),
                                error -> {
                                    Logger.log(Logger.LogLevel.SUCCESS, "Error disabling embed!");
                                    Log4JLogger.printStackTrace(error);
                                    future.completeExceptionally(error);
                                }
                        );
            }
        } else {
            future.completeExceptionally(new IllegalStateException("Message is null."));
        }
    }

    public String getNextButtonId() {
        return nextButtonId;
    }

    public String getReloadButtonId() {
        return saveButtonId;
    }

    public String getPrevButtonId() {
        return prevButtonId;
    }

    private ActionRow getActionRow() {
        for (Map.Entry<List<MessageEmbed>, List<SelectMenu>> entry : pages.entrySet()) {
            Button prevButton = Button.danger(getPrevButtonId(), ProfileCore.getTranslatorAPI().translate("Previous")).withStyle(ButtonStyle.DANGER).withDisabled(currentPage == 0);
            Button reloadButton = Button.primary(getReloadButtonId(), ProfileCore.getTranslatorAPI().translate("Reload")).withStyle(ButtonStyle.PRIMARY);
            Button nextButton = Button.success(getNextButtonId(), ProfileCore.getTranslatorAPI().translate("Next")).withStyle(ButtonStyle.SUCCESS).withDisabled(currentPage == entry.getKey().size() - 1);
            return ActionRow.of(prevButton, reloadButton, nextButton);
        }
        return null;
    }

    private long generateCustomId() {
        return customIdCounter.getAndIncrement();
    }
}