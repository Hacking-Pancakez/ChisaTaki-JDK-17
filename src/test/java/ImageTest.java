import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import dev.kurumidisciples.chisataki.quotes.QuoteSettings;
import dev.kurumidisciples.chisataki.quotes.image.QuoteImageGenerator;
import net.dv8tion.jda.api.entities.Message;

public class ImageTest {
    
    public static void main(String[] args) {
        QuoteSettings settings = new QuoteSettings("123", true, true, 630, 1200);
        Message message = null;

        BufferedImage image = QuoteImageGenerator.generateQuoteImage(settings, message);

        try {
            ImageIO.write(image, "png", new File("quote.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
