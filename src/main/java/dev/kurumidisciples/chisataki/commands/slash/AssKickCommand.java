package dev.kurumidisciples.chisataki.commands.slash;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import dev.kurumidisciples.chisataki.asskick.AutoDetectCircle;
import dev.kurumidisciples.chisataki.utils.ColorUtils;
import dev.kurumidisciples.chisataki.utils.ImageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;


public class AssKickCommand extends SlashCommand {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssKickCommand.class);

    public AssKickCommand() {
        super("buttkick", "give someone a playful kick in the butt!");
        super.options = List.of(new OptionData(OptionType.USER, "user", "The user to kick in the butt", true));
    }

    @Override
    @SuppressWarnings("null")
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Member executor = event.getMember();
        Member target = event.getOption("user").getAsMember();

        FileUpload file = FileUpload.fromData(createGif(executor, target), "asskick.gif");

        event.getHook().editOriginalEmbeds(buildEmbed(executor, target)).setFiles(file).queue();
    }


    private MessageEmbed buildEmbed(Member executor, Member target){
        return new EmbedBuilder()
            .setColor(ColorUtils.PURPLE)
            .setTitle(String.format("%s kicked %s in the butt!", executor.getEffectiveName(), target.getEffectiveName()))
            .setImage("attachment://asskick.gif")
            .setFooter("that looked like it hurt")
            .build();
    }

    private InputStream createGif(Member executor, Member target){
        try {
            BufferedImage executorAvatar = ImageUtils.retrieveImageFromUrl(executor.getEffectiveAvatarUrl());
            BufferedImage targetAvatar = ImageUtils.retrieveImageFromUrl(target.getEffectiveAvatarUrl());

            CompletableFuture<List<BufferedImage>> future = CompletableFuture.supplyAsync(() -> {
                List<BufferedImage> circleImages = new ArrayList<>();
                circleImages.add(ImageUtils.shrinkImage(ImageUtils.makeCircleImage(executorAvatar), 98, 98));
                circleImages.add(ImageUtils.shrinkImage(ImageUtils.makeCircleImage(targetAvatar), 98, 98));
                return circleImages;
            });

            List<BufferedImage> gifFrames = ImageUtils.gifToBufferedImages("data/images/kick.png", 55, 27390, 498);

            ArrayList<BufferedImage> newFrames = new ArrayList<>();

            for (int i = 0; i < gifFrames.size(); i++) {
                    try {
                        BufferedImage frame = gifFrames.get(i);
                        frame = ImageUtils.overlayImages(frame, future.get().get(0), AutoDetectCircle.detect(frame).get(0).getX(), AutoDetectCircle.detect(frame).get(0).getY());
                        frame = ImageUtils.overlayImages(frame, future.get().get(1), AutoDetectCircle.detect(frame).get(1).getX(), AutoDetectCircle.detect(frame).get(1).getY());
                        newFrames.add(frame);
                    } catch (Exception e) {
                       logger.error("Error: on frame " + i, e);
                    }
            }

            return ImageUtils.createGifEncoder(newFrames, 50);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
