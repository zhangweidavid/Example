/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.recieve;

import demo.mbassdor.core.event.message.MessageReceive;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月14日 下午5:44:01
 * @version  v 0.1
 */
public class ReceiveTopic 
extends MessageReceive 
{ 
public static final String COMMAND = "TOPIC"; 

private final String channel; 

private final String sender; 

private final String topic; 

public ReceiveTopic( OutputQueue replyQueue, IrcMessage message ) 
{ 
    super( replyQueue, message ); 

    sender = message.getPrefix().substring( 0, message.getPrefix().indexOf( '!' ) ); 
    channel = message.getParams()[0]; 
    topic = message.getParams()[1].substring( 1 ); 
} 

public String getChannel() 
{ 
    return channel; 
} 

public String getSender() 
{ 
    return sender; 
} 

public String getTopic() 
{ 
    return topic; 
} 
}
