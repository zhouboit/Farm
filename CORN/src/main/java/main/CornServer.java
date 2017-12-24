package main;

import com.jonbore.frame.JettyServer;
import org.apache.log4j.Logger;


/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class CornServer {

    private static Logger logger = Logger.getLogger(CornServer.class);

    public static void main(String[] args) {
        JettyServer.startServer(8081, "/farm/corn", "corn");
    }
}
