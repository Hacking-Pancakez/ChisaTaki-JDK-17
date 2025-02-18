package dev.kurumidisciples.chisataki.secretsanta.time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.kurumidisciples.chisataki.internal.database.Database;
import dev.kurumidisciples.chisataki.internal.database.exceptions.InitializationException;
import dev.kurumidisciples.chisataki.secretsanta.MessageUsers;

public class SantaClock {

    private static final String GET_TIME = "SELECT time FROM santaTime";
    private static final String UPDATE_TIME = "UPDATE INTO santaTime (time) VALUES (?)";
    private static final String INSERT_TIME = "INSERT INTO santaTime (time) VALUES (?)";

    private static final Logger LOGGER = LoggerFactory.getLogger(SantaClock.class);

    static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

     static {
        createClock();
    }


    public static boolean isTimeSet(){
        try(PreparedStatement statement = Database.createStatement(GET_TIME)){
            try(ResultSet set = statement.executeQuery()){
                return set.next();
            }
        } catch (SQLException | InitializationException e) {
            LOGGER.error("An error occurred in SantaClock when retrieving table", e);
            return false;
        }
    }

    public static void setTime(long time){
        if (isTimeSet()){
            try(PreparedStatement statement = Database.createStatement(UPDATE_TIME)){
                statement.setLong(1, time);
                statement.executeUpdate();
            } catch (SQLException | InitializationException e) {
                LOGGER.error("An error occurred in SantaClock when inserting into the table", e);
            }
        } else {
            insertTime(time);
        }
    }

    public static void insertTime(long time){
        try(PreparedStatement statement = Database.createStatement(INSERT_TIME)){
            statement.setLong(1, time);
            statement.executeUpdate();
        } catch (SQLException | InitializationException e) {
            LOGGER.error("An error occurred in SantaClock when inserting into the table", e);
        }
    }

    public static long getTime(){
        try(PreparedStatement statement = Database.createStatement(GET_TIME)){
            try(ResultSet set = statement.executeQuery()){
                if(set.next()){
                    return set.getLong("time");
                } else {
                    return -1;
                }
            }
        } catch (SQLException | InitializationException e) {
            LOGGER.error("An error occurred in SantaClock when retrieving table", e);
            return -1;
        }
    }

   
   public static void createClock() {
            executor.scheduleAtFixedRate(() -> {
                long currentTime = System.currentTimeMillis() / 1000;
                long expirationTime = getTime();

                LOGGER.debug(String.valueOf(expirationTime));

                boolean isTimeSet = isTimeSet();

                LOGGER.debug("Running SantaClock");

                if (isTimeSet && currentTime >= expirationTime) {
                    LOGGER.info("SantaClock time is up. Sending Messages.");
                    MessageUsers.messageUsers();
                    executor.shutdown();
                } else {
                    LOGGER.debug("time is not up");
                }
            }, 0, 1, TimeUnit.SECONDS);

    }

    public static boolean isWithinDay(long time){
        long currentTime = System.currentTimeMillis() / 1000;
        return (time - currentTime) <= 86400;
    }

    public static void start(){
        return;
    }
}
