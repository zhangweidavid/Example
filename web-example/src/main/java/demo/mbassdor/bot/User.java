/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:55:35
 * @version v 0.1
 */
public class User {
	private final String nick;

	private PrivLevel privLevel;

	public User(String nick) {
		this(nick, PrivLevel.Regular);
	}

	public User(String nick, PrivLevel privLevel) {
		this.nick = nick;
		this.privLevel = privLevel;
	}

	public PrivLevel getPrivLevel() {
		return privLevel;
	}

	public void setPrivLevel(PrivLevel privLevel) {
		this.privLevel = privLevel;
	}

	public String getNick() {
		return nick;
	}

	@Override
	public String toString() {
		return "User [nick=" + nick + ", privLevel=" + privLevel + "]";
	}

	/**
	 * IRC Privilege level relative to the parent Channel
	 */
	public enum PrivLevel {
		/**
		 * Just a regular unprivileged user
		 */
		Regular,

		/**
		 * A user with op permissions
		 */
		Op
	}
}
