package demo.mbassdor;

import java.io.File;

import net.engio.mbassy.bus.common.DeadMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Created by wei.zw on 2017/5/13.
 */
@Listener(references = References.Strong)
public class SimpleFileListener {
	
	private static final Logger logger=LogManager.getLogger();

    @Handler
    public void handle(File file){
    	logger.info(file.getName());
    }
    
    @Handler(delivery = Invoke.Asynchronously)
    public void expensiveOperation(File file){
    	logger.info("async..."+file.getName());
    }
    
//    @Handler(condition = "msg.size >= 10000")
//    @Enveloped(messages = {HashMap.class, LinkedList.class})
//    public void handleLarge(MessageEnvelope envelope) {
//       // handle objects without common super type
//    }

    @Handler
    public void handleDeadMessage(DeadMessage deadMessage){
        logger.info(deadMessage.getMessage());
    }
// 
   
}
