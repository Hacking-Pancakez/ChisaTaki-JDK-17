package dev.kurumidisciples.chisataki.tictactoe;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@SuppressWarnings("all")
public class TTTUtils {
    
     public static TTTGameSetup rebuildGameSetupFromMenu(Interaction event, String id){
        String[] ids = id.split("-");
        return new TTTGameSetup(event.getGuild().getMemberById(ids[1]), event.getGuild().getMemberById(ids[2]));
    }

    public static TTTGameSetup rebuildGameSetupFromButton(Interaction event, String buttonId){
        String[] ids = buttonId.split("-");
        TTTGameSetup setup = new TTTGameSetup(event.getGuild().getMemberById(ids[3]), event.getGuild().getMemberById(ids[5]));
        setup.setPlayer1Choice(TTTChoice.getChoice(ids[4]));
        return setup;
    }

    public static TTTGameSetup rebuildGameSetupFromRequest(Interaction event, String id){
        String[] ids = id.split("-");
        TTTGameSetup setup = new TTTGameSetup(event.getGuild().getMemberById(ids[1]), event.getGuild().getMemberById(ids[3]));
        setup.setPlayer1Choice(TTTChoice.getChoice(ids[2]));
        return setup;
    }


    public static char[][] discordButtonsToCharBoard(List<ActionRow> actionRows){
        // ActionRow to Buttons to char[][]
        List<List<Button>> buttons = new ArrayList<>();
        for (int o = 0; o < actionRows.size(); o++){
            buttons.add(actionRows.get(o).getButtons());
        }
        char[][] board = new char[3][3];
        for (int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.get(i).size(); j++) {
                if (buttons.get(i).get(j).getEmoji() == null) {
                    board[i][j] = ' ';
                } else if (buttons.get(i).get(j).getEmoji().getAsReactionCode().equals("<:Chinanago:1120915801680134185>")) {
                    board[i][j] = 'o';
                } else if (buttons.get(i).get(j).getEmoji().getAsReactionCode().equals("<:Sakana:1016650006662496326>")) {
                    board[i][j] = 'x';
                }
            }
        }
        return board;
    }

    public static char[][] discordButtonsToCharBoardFromButton(List<List<Button>> buttons){
        // ActionRow to Buttons to char[][]
        char[][] board = new char[3][3];
        for (int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.get(i).size(); j++) {
                if (buttons.get(i).get(j).getEmoji() == null) {
                    board[i][j] = ' ';
                } else if (buttons.get(i).get(j).getEmoji().getFormatted().equals("<:Chinanago:1120915801680134185>")) {
                    board[i][j] = 'o';
                } else if (buttons.get(i).get(j).getEmoji().getFormatted().equals("<:Sakana:1016650006662496326>")) {
                    board[i][j] = 'x';
                }
            }
        }
        return board;
    }

    public static Member getCurrentPlayerFromTTTBoard(Interaction event, Button button){
        String[] ids = button.getId().split("-");
        return event.getGuild().getMemberById(ids[7]);
    }
}
