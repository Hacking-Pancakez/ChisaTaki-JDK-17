package dev.kurumidisciples.chisataki.listeners;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SupportInteraction extends ListenerAdapter {
    private static final String SUPPORTER_CHANNEL = "1015626668360077453";
    private static final String BOOSTER_CHANNEL = "1028022086888869888";
    private static final ExecutorService boostExecutor = Executors.newSingleThreadExecutor();

    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        boostExecutor.submit(() -> {
            if (event.getOldTimeBoosted() == null) {
                String messageFormat = "Thank you for the boost %s!!!!!❤️ Please go to %s to claim your customizable role and color if you haven't already!";
                String message = String.format(messageFormat, event.getMember().getAsMention(), event.getGuild().getTextChannelById(BOOSTER_CHANNEL).getAsMention());
                event.getGuild().getTextChannelById(SUPPORTER_CHANNEL).sendMessage(message)
                        .setStickers(StickerSnowflake.fromId(event.getGuild().getStickersByName("ChisaTaki Cuddle", false).get(0).getId()))
                        .queue();
            }
        });
    }
}
