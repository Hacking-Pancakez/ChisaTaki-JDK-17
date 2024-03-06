package dev.kurumidisciples.chisataki.internal.database;

import java.util.List;

import dev.kurumidisciples.chisataki.booster.BoosterDatabase;
import dev.kurumidisciples.chisataki.commands.slash.IgnoreCommand;
import dev.kurumidisciples.chisataki.internal.database.middlemen.GenericDatabaseTable;
import dev.kurumidisciples.chisataki.modmail.Ticket;
import dev.kurumidisciples.chisataki.shrine.ShrineDatabaseConst;
import dev.kurumidisciples.chisataki.utils.CooldownUtils;

public class TableCollection {
    
    public static List<GenericDatabaseTable> getTables(){
        return List.of(
            new IgnoreCommand(),
            new Ticket(),
            new ShrineDatabaseConst(),
            new CooldownUtils(),
            new BoosterDatabase()
        );
    }
}
