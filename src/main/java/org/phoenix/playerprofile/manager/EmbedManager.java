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

package org.phoenix.playerprofile.manager;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.utils.Logger;

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

public class EmbedManager extends ListenerAdapter implements Listener {

    private static final Map<Long, List<PaginatedEmbed>> paginatedEmbedsByMember = new HashMap<>();
    private static final Map<Long, Instant> lastInteractionByMember = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void createPaginated(TextChannel channel, long memberId, List<MessageEmbed> pages) {
        List<PaginatedEmbed> paginatedEmbeds = paginatedEmbedsByMember.computeIfAbsent(memberId, k -> new ArrayList<>());
        PaginatedEmbed paginatedEmbed = new PaginatedEmbed(channel, pages);
        paginatedEmbeds.add(paginatedEmbed);
        paginatedEmbed.sendInitialPage();
    }

    public static void updateInteraction(long memberId) {
        lastInteractionByMember.put(memberId, Instant.now());
    }

    private void disableButton(long memberId) {
        List<PaginatedEmbed> paginatedEmbeds = paginatedEmbedsByMember.get(memberId);
        if (paginatedEmbeds != null) {
            paginatedEmbeds.forEach(PaginatedEmbed::disableButtons);
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
        List<PaginatedEmbed> paginatedEmbeds = paginatedEmbedsByMember.get(memberId);
        if (paginatedEmbeds == null) return;

        for (PaginatedEmbed paginatedEmbed : paginatedEmbeds) {
            if (event.getComponentId().equals(paginatedEmbed.getPrevButtonId())) {
                event.deferEdit().queue();
                paginatedEmbed.showPreviousPage();
                updateInteraction(memberId);
            } else if (event.getComponentId().equals(paginatedEmbed.getNextButtonId())) {
                event.deferEdit().queue();
                paginatedEmbed.showNextPage();
                updateInteraction(memberId);
            }
        }
    }
}

class PaginatedEmbed {
    private final TextChannel channel;
    private final List<MessageEmbed> pages;
    private int currentPage;
    private Message message;
    private final String nextButtonId;
    private final String prevButtonId;
    private final AtomicInteger customIdCounter = new AtomicInteger(0);

    public PaginatedEmbed(TextChannel channel, List<MessageEmbed> pages) {
        this.channel = channel;
        this.pages = pages;
        this.currentPage = 0;

        this.nextButtonId = "next_" + generateCustomId();
        this.prevButtonId = "prev_" + generateCustomId();
    }

    public void sendInitialPage() {
        if (!pages.isEmpty()) {
            MessageEmbed initialPage = pages.get(0);
            channel.sendMessageEmbeds(initialPage)
                    .setActionRows(getActionRow())
                    .queue(msg -> message = msg);
        }
    }

    public void showNextPage() {
        if (currentPage < pages.size() - 1) {
            currentPage++;
            updateEmbed().exceptionally(exception -> {
                Logger.log(Logger.LogLevel.ERROR, "An error occurred while updating the embed.");
                Log4JLogger.printStackTrace(exception);
                return null;
            });
        }
    }

    public void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateEmbed().exceptionally(exception -> {
                Logger.log(Logger.LogLevel.ERROR, "An error occurred while updating the embed.");
                Log4JLogger.printStackTrace(exception);
                return null;
            });
        }
    }

    public CompletableFuture<Void> updateEmbed() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (message != null) {
            message.editMessageEmbeds(pages.get(currentPage))
                    .setActionRows(getActionRow())
                    .queue(success -> future.complete(null),
                            error -> {
                                Logger.log(Logger.LogLevel.SUCCESS, "Error updating embed!");
                                Log4JLogger.printStackTrace(error);
                                future.completeExceptionally(error);
                            }
                    );
        } else {
            future.completeExceptionally(new IllegalStateException("Message is null."));
        }
        return future;
    }

    public void disableButtons() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (message != null) {
            message.editMessageEmbeds(pages.get(currentPage))
                    .setActionRows(ActionRow.of(
                            Button.danger(getPrevButtonId(), "Previous").withDisabled(true),
                            Button.success(getNextButtonId(), "Next").withDisabled(true)))
                    .queue(success -> future.complete(null),
                            error -> {
                                Logger.log(Logger.LogLevel.SUCCESS, "Error disabling embed!");
                                Log4JLogger.printStackTrace(error);
                                future.completeExceptionally(error);
                            }
                    );
        } else {
            future.completeExceptionally(new IllegalStateException("Message is null."));
        }
    }

    public String getNextButtonId() {
        return nextButtonId;
    }

    public String getPrevButtonId() {
        return prevButtonId;
    }

    private ActionRow getActionRow() {
        Button prevButton = Button.danger(getPrevButtonId(), "Previous").withStyle(ButtonStyle.DANGER).withDisabled(currentPage == 0);
        Button nextButton = Button.success(getNextButtonId(), "Next").withStyle(ButtonStyle.SUCCESS).withDisabled(currentPage == pages.size() - 1);
        return ActionRow.of(prevButton, nextButton);
    }

    private long generateCustomId() {
        return customIdCounter.getAndIncrement();
    }
}