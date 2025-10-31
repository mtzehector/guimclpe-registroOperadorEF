/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class MessageResolver {
    private static final Properties PROPERTIES = new Properties();

  static {
    InputStream stream = null;
    try {
      stream = MessageResolver.class.getResourceAsStream("/plantillaEnvioToken.properties");
      PROPERTIES.load(stream);
    } catch (IOException ex) {
      Logger.getLogger(MessageResolver.class.getName()).log(Level.SEVERE, null,
              ex);
    } finally {
      try {
        if (stream != null) {
          stream.close();
        }
      } catch (IOException ex) {
        Logger.getLogger(MessageResolver.class.getName()).
                log(Level.SEVERE, null, ex);
      }
    }
  }

  public static String getMessage(String key, Object[] data) {
	    String message;
	    message = (new MessageFormat(PROPERTIES.getProperty(key, ""))).format(data,
	            new StringBuffer(), null).toString();
	    return message;
	  }

  public static String getMessage(String key) {
	           String message;
	           message = PROPERTIES.getProperty(key, "").toString();
	           return message;
	         }


}
