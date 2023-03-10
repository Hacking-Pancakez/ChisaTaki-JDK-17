package dev.kurumiDisciples.chisataki;
import dev.kurumiDisciples.chisataki.utils.CooldownUtils;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.interactions.components.selections.*;
import net.dv8tion.jda.api.interactions.components.buttons.*;
import net.dv8tion.jda.api.interactions.components.*;
import net.dv8tion.jda.api.interactions.commands.*;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import net.dv8tion.jda.api.exceptions.*;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.emoji.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

import java.awt.Color;

import java.io.FileReader;

import java.time.LocalDateTime;

/* To Do: Add event loggers */

public class RoleMenuInteraction extends ListenerAdapter {

  StringSelectInteractionEvent EVENT;

  final static List<String> shrineIDs = List.of(/* chisato */"1013558607213756518", /* takina */ "1013567857075953706");
  final static List<String> groupIDs = List.of(/* OniMai */ "1056969921201975367",
      /* Spy Classroom */"1056970881185873990", /* NieR:Automata */ "1056970891575164978",
      /* Kubo */ "1056970896251818035", /* Magical Revolution */ "1056969596139216967",
      /* 80,000 Gold */ "1056969931633205290", /* Tomo-Chan */"1056969945495380040", /* Dub */ "1033697196899577866",
      /* vinland saga */ "1042588408083660852", /* bofuri */ "1042589219333361758");
  final static String chisataki = "1010080294692458496";
  final static List<String> serverIDs = List.of(/* server announcement */ "1013809351108079636",
      /* event */ "1013809301342662726", /* Chisataki */ "1013809402547011616", /* groupwatch */ "1025081700570636318");

  final static Logger logger = LoggerFactory.getLogger(RoleMenuInteraction.class);

  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    Thread role = new Thread() {
      public void run() {
        if (event.getName().equals("test-send")) {
          if (!event.getMember().getId().equals("360241951804620800"))
            return;
          event.getGuild().getTextChannelById("1024037775743406111").sendMessage(" ").setEmbeds(getTutorialEmbed())
              .queue();
          event.getGuild().getTextChannelById("1024037775743406111").sendMessage(" ").setEmbeds(getShrineEmbed())
              .addActionRow(getShrineMenu()).queue();
          event.getGuild().getTextChannelById("1024037775743406111").sendMessage(" ").setEmbeds(getChisatakiEmbed())
              .addActionRow(getChistakiButton()).queue();
          event.getGuild().getTextChannelById("1024037775743406111").sendMessage(" ").setEmbeds(getServerEmbed())
              .addActionRow(getServerMenu()).queue();
          event.getGuild().getTextChannelById("1024037775743406111").sendMessage(" ").setEmbeds(getGroupwatchEmbed())
              .addActionRow(getGroupMenu()).queue();
        }
      }
    };
    role.start();
  }

  public void onStringSelectInteraction(StringSelectInteractionEvent event) {
    Thread roleThread = new Thread() {
      public void run() {
        EVENT = event;
        if (event.getComponentId().equals("menu:role:groupwatch")) {
          String format = "I've added: %s to you!";
          Guild guild = event.getGuild();
          event.deferReply(true).queue();
          removeGroupRoles(event.getMember());
          String roleName = "";
          for (String s : event.getValues()) {

            switch (s) {
              case "princessSelect":
                roleHandle(event.getMember(), guild.getRoleById("1056969596139216967"));
                roleName += "`MagiRevo`, ";
                break;
              case "bofuriSelect":
                roleHandle(event.getMember(), guild.getRoleById("1042589219333361758"));
                roleName += "`Bofuri`, ";
                break;
              case "onimaiSelect":
                roleHandle(event.getMember(), guild.getRoleById("1056969921201975367"));
                roleName += "`OniMai`, ";
                break;
              case "goldSelect":
                roleHandle(event.getMember(), guild.getRoleById("1056969931633205290"));
                roleName += "`80,000 Gold`, ";
                break;
              case "vinlandSelect":
                roleHandle(event.getMember(), guild.getRoleById("1042588408083660852"));
                roleName += "`Vinland Saga`, ";
                break;
              case "tomoSelect":
                roleHandle(event.getMember(), guild.getRoleById("1056969945495380040"));
                roleName += "`Tomo-Chan`, ";
                break;
              case "spySelect":
                roleHandle(event.getMember(), guild.getRoleById("1056970881185873990"));
                roleName += "`Spy Classroom`, ";
                break;
              case "nierSelect":
                roleHandle(event.getMember(), guild.getRoleById("1056970891575164978"));
                roleName += "`NieR:Automata`, ";
                break;
              case "kuboSelect":
                roleHandle(event.getMember(), guild.getRoleById("1056970896251818035"));
                roleName += "`Kubo`, ";
                break;
              case "dubSelect":
                roleHandle(event.getMember(), guild.getRoleById("1033697196899577866"));
                roleName += "`Dub`, ";
                break;
            }
          }
          try{
          roleName = roleName.substring(0, roleName.length() - 2);
          format = String.format(format, roleName);
          }
          catch(StringIndexOutOfBoundsException e){
            format = "I've removed all roles from you!";
          }
          event.getHook().editOriginal(format).queue();
        } else if (event.getComponentId().equals("menu:role:server")) {
          String format = "I've added: %s to you!";
          Guild guild = event.getGuild();
          event.deferReply(true).queue();
          removeServerRoles(event.getMember());
          String roleName = "";
          for (String s : event.getValues()) {

            switch (s) {
              case "announceSelect":
                roleHandle(event.getMember(), guild.getRoleById("1013809351108079636"));
                roleName += "`Server Announcement`, ";
                break;
              case "eventSelect":
                roleHandle(event.getMember(), guild.getRoleById("1013809301342662726"));
                roleName += "`Event Announcement`, ";
                break;
              case "chisaSelect":
                roleHandle(event.getMember(), guild.getRoleById("1013809402547011616"));
                roleName += "`ChisaTaki Announcement`, ";
                break;
              case "groupSelect":
                roleHandle(event.getMember(), guild.getRoleById("1025081700570636318"));
                roleName += "`Groupwatch`, ";
                break;
            }
          }
          try{
            roleName = roleName.substring(0, roleName.length() - 2);
            format = String.format(format, roleName);
          }
          catch(StringIndexOutOfBoundsException e){
            format = "I've removed all roles from you!";
          }
          event.getHook().editOriginal(format).queue();
        } else if (event.getComponentId().equals("menu:role:shrine")) {
          event.deferReply(true).queue();

          if (canUpdateShrineRoles(event.getUser())) {
            updateShrineRoles(event);
          } else {
            String message = "Please wait an hour before changing your allegiances";
            event.getHook().editOriginal(message).queue();
          }
        }
      }
    };
    roleThread.setName("Role-Thread");
    roleThread.start();
  }

  private static boolean canUpdateShrineRoles(User user) {
    LocalDateTime previousDateTime = CooldownUtils.getPreviousDateTime("data/shrineRoleCooldown.json", user);
    return CooldownUtils.hasCooldownElapsed(previousDateTime);
  }

  private static void updateShrineRoles(StringSelectInteractionEvent event) {
    Guild guild = event.getGuild();
    removeShrineRoles(event.getMember());
    removeLostRole(event.getMember());
    for (String s : event.getValues()) {
      switch (s) {
        case "chisatoSelect":
          roleHandle(event.getMember(), guild.getRoleById("1013558607213756518"));
          break;
        case "takinaSelect":
          roleHandle(event.getMember(), guild.getRoleById("1013567857075953706"));
          break;
      }
    }

    CooldownUtils.updateUsersCooldown("data/shrineRoleCooldown.json", event.getUser());
    event.getHook().editOriginal("Role Added").queue();
  }

  public void onButtonInteraction(ButtonInteractionEvent event) {
    Thread buttonThread = new Thread() {
      public void run() {
        if (event.getComponentId().equals("chisaButton")) {
          Guild guild = event.getGuild();
          event.deferReply(true).queue();
          if (hasRole(event.getMember(), guild.getRoleById("1010080294692458496"))) {
            guild.removeRoleFromMember(event.getMember(), guild.getRoleById("1010080294692458496")).queue();
          } else {
            guild.addRoleToMember(event.getMember(), guild.getRoleById("1010080294692458496")).queue();
          }
          event.getHook().editOriginal("Role Removed/Added").queue();
        }
      }
    };
    buttonThread.setName("Button-Thread");
    buttonThread.start();
  }

  private static MessageEmbed getChisatakiEmbed() {
    MessageEmbed chisataki = new EmbedBuilder().addField("**ChisaTaki**",
        "Click the button below to join the church of ChisaTaki and get access to the exclusive ChisaTaki Chats and channels!",
        false).addField(" ", "<:ChisaTakiKiss:1013059473167888486> - <@&1010080294692458496>", false)
        .setColor(new Color(216, 109, 127)).build();
    return chisataki;
  }

  private static MessageEmbed getTutorialEmbed() {
    MessageEmbed tutorial = new EmbedBuilder().setTitle("Role Tutorial").setDescription(
        "● To get a role, just open the dropdown menu and select the roles you wish to have!.\n\n● For example, select "
            + Emoji.fromUnicode("U+1F4E2").getAsReactionCode()
            + " Server Announcement to get the <@&1013809351108079636> role.")
        .setColor(new Color(216, 109, 127)).build();

    return tutorial;
  }

  private static MessageEmbed getGroupwatchEmbed() {
    MessageEmbed groupwatch = new EmbedBuilder().setTitle("Groupwatch Roles").setDescription(
        "Select the each option that represents a show. Select the show you want to be pinged on when the respective groupwatch starts. Unselect the option to remove it.")
        .setColor(new Color(216, 109, 127)).build();
    return groupwatch;
  }

  private static MessageEmbed getShrineEmbed() {
    MessageEmbed shrine = new EmbedBuilder().setColor(new Color(216, 109, 127))
        .addField("Faction Roles", "**__Chisato's Soldier!__**", false)
        .addField(" ", "<:ChisatoTrain:1013976121253040160> - <@&1013558607213756518>", false)
        .addField(" ", "This Role provides you access to <#1013939451979911289> and other Chisato related channels.",
            false)
        .addField(" ", "**__Takina's Sakana!__**", false)
        .addField(" ", "<:TakinaTrain:1013976244884344872> - <@&1013567857075953706>", false)
        .addField(" ", "This role provides you access to <#1013939540420997262> and other Takina related channels.",
            false)
        .addField(" ", "**Note: You can only pick one of these roles so choose wisely on whom to follow!**", false)
        .build();
    return shrine;
  }

  private static MessageEmbed getServerEmbed() {
    MessageEmbed server = new EmbedBuilder().setColor(new Color(216, 109, 127))
        .addField("**Server Roles**", "📢 - <@&1013809351108079636> : Get pinged for server updates/announcements.",
            false)
        .addField(" ", "🎁 - <@&1013809301342662726>: Get pinged for event news/announcements.", false)
        .addField(" ",
            "<a:EDFlower:1014474116692181082> - <@&1013809402547011616>: Get pinged for special ChisaTaki announcements/updates.",
            false)
        .addField(" ", "<:WasabiNoriko:1016648327208648704> - <@&1025081700570636318> : Get pinged for groupwatches.",
            false)
        .build();
    return server;
  }

  private static StringSelectMenu getShrineMenu() {
    StringSelectMenu shrine = StringSelectMenu.create("menu:role:shrine")
        .addOption("Chisato's Soldier", "chisatoSelect", null,
            Emoji.fromCustom("ChisaTakiHeart2", 1023727380038176849L, false))
        .addOption("Takina's Sakana", "takinaSelect", null, Emoji.fromCustom("Sakana", 1016650006662496326L, false))
        .setPlaceholder("Select Your Faction").setMinValues(0).build();
    return shrine;
  }

  private static StringSelectMenu getServerMenu() {
    StringSelectMenu server = StringSelectMenu.create("menu:role:server").setPlaceholder("Select Server Role(s)")
        .addOption("Server Announcement", "announceSelect", null, Emoji.fromUnicode("U+1F4E2"))
        .addOption("Event Announcement", "eventSelect", null, Emoji.fromUnicode("U+1F381"))
        .addOption("ChisaTaki Announcement", "chisaSelect", null,
            Emoji.fromCustom("EDFlower", 1014474116692181082L, true))
        .addOption("Groupwatch", "groupSelect", null, Emoji.fromCustom("WasabiNoriko", 1016648327208648704L, false))
        .setMaxValues(4).setMinValues(0).build();
    return server;
  }

  private static Button getChistakiButton() {
    return Button.of(ButtonStyle.SECONDARY, "chisaButton", "ChisaTaki Worshipper",
        Emoji.fromCustom("ChisaTakiKiss", 1014257843974721606L, false));
  }

  private static StringSelectMenu getGroupMenu() {
    StringSelectMenu group = StringSelectMenu.create("menu:role:groupwatch").setPlaceholder("Select Groupwatch Role(s)")
        .addOption("Magical Revolution of the Reincarnated Princess", "princessSelect", null,
            Emoji.fromCustom("AniSmug", 1002932728016150670L, false))
        .addOption("Bofuri", "bofuriSelect", null, Emoji.fromCustom("a_MapleNom", 692821511379222588L, true))
        .addOption("OniMai", "onimaiSelect", null, Emoji.fromCustom("mahiroAraAra", 1003445149898117150L, false))
        .addOption("Saving 80,000 Gold in Another World for my Retirement", "goldSelect", null,
            Emoji.fromCustom("MikuGold", 393591496403582976L, true))
        .addOption("Vinland Saga", "vinlandSelect", null, Emoji.fromUnicode("U+2693"))
        .addOption("Tomo-chan is a Girl!", "tomoSelect", null, Emoji.fromUnicode("U+1F467"))
        .addOption("Spy Classroom", "spySelect", null, Emoji.fromUnicode("U+1F575"))
        .addOption("NieR: Automata", "nierSelect", null, Emoji.fromUnicode("U+1F916"))
        .addOption("Kubo Won't Let Me Be Invisible", "kuboSelect", null, Emoji.fromUnicode("U+1FAE5"))
        .addOption("Dub", "dubSelect", null, Emoji.fromUnicode("U+1F5E3")).setMaxValues(10).setMinValues(0).build();

    return group;
  }

  private static void roleHandle(Member member, Role role) {
    Guild guild = member.getGuild();
    try {
      guild.addRoleToMember(member, role).complete();
    } catch (Exception e) {
      // nothing
    }
  }

  private static boolean hasRole(Member member, Role role) {
    List<Role> memberRoles = member.getRoles();

    for (Role r : memberRoles) {
      if (r.getId().equals(role.getId()))
        return true;
    }

    return false;
  }

  private static void removeShrineRoles(Member member) {
    Guild guild = member.getGuild();

    for (String id : shrineIDs) {
      Role role = guild.getRoleById(id);
      if (hasRole(member, role)) {
        guild.removeRoleFromMember(member, role).queue();
      }
    }

  }

  private static void removeServerRoles(Member member) {
    Guild guild = member.getGuild();

    for (String id : serverIDs) {
      Role role = guild.getRoleById(id);
      if (hasRole(member, role)) {
        guild.removeRoleFromMember(member, role).queue();
      }
    }
  }

  private static void removeGroupRoles(Member member) {
    Guild guild = member.getGuild();

    for (String id : groupIDs) {
      Role role = guild.getRoleById(id);
      if (hasRole(member, role)) {
        try {
          guild.removeRoleFromMember(member, role).complete();
        } catch (Exception e) {

        }
      }
    }
  }

  private static void removeLostRole(Member member) {
    Guild guild = member.getGuild();
    Role lostRole = guild.getRoleById("1013807894531166309");

    try {
      guild.removeRoleFromMember(member, lostRole).reason("Role Bot Function").queue();
    } catch (InsufficientPermissionException e) {
      logger.error("Insufficieent Permission Error Caught. Reason: {}", e.getMessage());
    } catch (HierarchyException e) {
      logger.error("Hierarchy Error Caught. Reason: {}", e.getMessage());
    }
  }
}