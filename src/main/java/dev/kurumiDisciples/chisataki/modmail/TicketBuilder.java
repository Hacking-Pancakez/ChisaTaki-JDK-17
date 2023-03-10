package dev.kurumiDisciples.chisataki.modmail;

import javax.json.Json;
import javax.json.JsonObject;

import dev.kurumiDisciples.chisataki.utils.FileUtils;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

public class TicketBuilder {


  public static Ticket buildTicketFile(int ticketNumber, ModalInteraction interaction, long channelId){
   
    JsonObject ticketContents = Json.createObjectBuilder()
      .add("ticket", ticketNumber)
      .add("ticket-id", channelId)
      .add("member", String.valueOf(interaction.getMember().getIdLong()))
      .addNull("staff")
      .add("subject", interaction.getValue("subject").getAsString())
      .add("body", interaction.getValue("body").getAsString())
      .add("status", "unanswered")
      .addNull("reason")
      .build();

    FileUtils.updateFileContent("data/tickets/ticket-" + String.valueOf(ticketNumber) + ".json", ticketContents);

    return new Ticket(ticketContents);
  }

  public static void updateTicketFile(Ticket ticket){
    FileUtils.updateFileContent("data/tickets/ticket-" + String.valueOf(ticket.getTicketNumber()) + ".json", ticket.getJsonObject());
  }
}